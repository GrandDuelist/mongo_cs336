#!venv/bin/python

import json
import random
from pymongo import MongoClient
from bson import json_util
import math

client = MongoClient()
db = client.cs336

# All reviews
all_reviews = [x["review"] for x in db.unlabel_review_after_splitting.find()]

# 6 random records from the unlabel_review collection
records = [db.unlabel_review_after_splitting.find().limit(-1).skip(x).next()
           for x in random.sample([x for x in range(1, 500)], 6)]

# Calculate N, number of words across all reviews
N = 0
df = {}
for review in all_reviews:
    for term in review:
        N = N + term["count"]
        word = term["word"]
        if word in df:
            df[word] += 1
        else:
            df[word] = 1


# Turn all documents to read into a vector of size len(df)
docs = []
cosines = []
magnitudes = []

for record in records:
    # Turn record into a docvector and add it to docvectors.
    vector = {}
    review = record["review"] # Current review
    words = [x["word"] for x in review] # All words in current review
    counts = [x["count"] for x in review] # All counts in current review
    for key, value in df.iteritems():
        w = 0
        if key in words:
            w = 1 + math.log10(counts[words.index(key)])
        vector[key] = w/math.log10(N/value)
    docs.append(vector)

# Calculate magnitudes of each vector
for vector in docs:
    magnitudes.append(math.sqrt(sum(map(lambda x: x**2, list(vector.itervalues())))))

# Pick one review
r = random.choice(records)
inex = records.index(r)

for record in records:
    if record is r:
        continue
    index = records.index(record)
    dot = sum(docs[index][key] * docs[inex][key] for key in docs[index].iterkeys())
    cosine = dot/(magnitudes[index] * magnitudes[inex])
    cosines.append(cosine)
    records[index]["cosine"] = cosine

# cosines = list(reversed(sorted(cosines)))
# print cosines

reviews = []
cosines.insert(records.index(r), 1.0)

for record in records:
    item = db.unlabel_review.find({"id": record['id']}).next()
    item["cosine"] = cosines[records.index(record)]
    reviews.append(item)

reviews.sort(key=lambda x: x["cosine"], reverse=True)
#for x in range(len(reviews)):
    #rev = reviews[x]
    #if x == 0:
        #print "Original.\n"
    #else:
        #print "Rank " + str(x) + ". Cosine " + str(rev["cosine"]) + "\n"
    #print json.dumps(rev, sort_keys=True, indent=4, default=json_util.default)
    #print "\n\n"

# ---------------------------------------------------------------------------- #

# Random two-word query. Rank all 6 reviews by cosine similarity of q to records
a = review[random.randint(0, len(review))]["word"]
b = review[random.randint(0, len(review))]["word"]
#print a, b

vector = {}
for key, value in df.iteritems():
    w = 0
    if key in [a, b]:
        w = 1
    vector[key] = w/math.log10(N/value)

    docs.append(vector)

inex = len(records)
magnitudes.append(math.sqrt(2))

cosines = []

for record in records:
    index = records.index(record)
    dot = sum(docs[index][key] * docs[inex][key] for key in docs[index].iterkeys())
    cosine = dot/(magnitudes[index] * magnitudes[inex])
    cosines.append(cosine)

reviews = []

for record in records:
    item = db.unlabel_review.find({"id": record['id']}).next()
    item["cosine"] = cosines[records.index(record)]
    reviews.append(item)

reviews.sort(key=lambda x: x["cosine"], reverse=True)

print "Query: ", a, b

for x in range(len(reviews)):
    rev = reviews[x]
    print "Rank " + str(x+1) + ". Cosine " + str(rev["cosine"]) + "\n"
    print json.dumps(rev, sort_keys=True, indent=4, default=json_util.default)
    print "\n\n"

# ---------------------------------------------------------------------------- #

print cosines
