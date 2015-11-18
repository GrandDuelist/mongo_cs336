#!/usr/bin/env python

import re
import nltk

import pandas as pd
import numpy as np

from bs4 import BeautifulSoup
from nltk.corpus import stopwords
from pprint import pprint


class KaggleWord2VecUtility(object):
    """KaggleWord2VecUtility is a utility class for processing raw HTML text into segments for further learning"""

    @staticmethod
    def review_to_wordlist( review, remove_stopwords=False ):
        # Function to convert a document to a sequence of words,
        # optionally removing stop words.  Returns a list of words.
        #
        # 1. Remove HTML
        review_text = BeautifulSoup(review, "html.parser").get_text()
        #
        # 2. Remove non-letters
        review_text = re.sub("[^a-zA-Z]"," ", review_text)
        #
        # 3. Convert words to lower case and split them
        words = review_text.lower().split()
        #
        # 4. Optionally remove stop words (false by default)
        if remove_stopwords:
            stops = set(stopwords.words("english"))
            words = [w for w in words if not w in stops]
        #
        # 5. Return a list of words
        return(words)

    @staticmethod
    def review_to_worddict( review, remove_stopwords=False ):
    #def review_to_wordlist( review, remove_stopwords=False ):
        # Function to convert a document to a sequence of words,
        # optionally removing stop words.  Returns a list of words.
        #
        # 1. Remove HTML
        review_text = BeautifulSoup(review, "html.parser").get_text()
        #
        # 2. Remove non-letters
       # review_text = re.sub("[^a-zA-Z]"," ", review_text)
        #
        # 3. Convert words to lower case and split them
        #words = review_text.lower().split()
        #
        # # 4. Optionally remove stop words (false by default)
        # if remove_stopwords:
        #     stops = set(stopwords.words("english"))
        #     words = [w for w in words if not w in stops]
        # #
        # # 5. change the list of words into a dictionary
        # words_dict = KaggleWord2VecUtility.convert_qhu(words)
        # #pprint(words_dict)
        #
        # 6. return the dictionary of words
        return review_text

    # Define a function to split a review into parsed sentences
    @staticmethod
    def review_to_sentences( review, tokenizer, remove_stopwords=False ):
        # Function to split a review into parsed sentences. Returns a
        # list of sentences, where each sentence is a list of words
        #
        # 1. Use the NLTK tokenizer to split the paragraph into sentences
        raw_sentences = tokenizer.tokenize(review.decode('utf8').strip())
        #
        # 2. Loop over each sentence
        sentences = []
        for raw_sentence in raw_sentences:
            # If a sentence is empty, skip it
            if len(raw_sentence) > 0:
                # Otherwise, call review_to_wordlist to get a list of words
                sentences.append( KaggleWord2VecUtility.review_to_wordlist( raw_sentence, \
                  remove_stopwords ))
        #
        # Return the list of sentences (each sentence is a list of words,
        # so this returns a list of lists
        return sentences

    # Define a function to count the frequency of spitted words
    @staticmethod
    def convert_clxia(words_in_list):
        words_in_dict = {}
        count = 0

        for word in words_in_list:
            word = word.lower() # make all letter to lowercase
            r = words_in_dict.get(word, [])
            r.append(count)
            words_in_dict[word] = r
            count += 1
        #    
        return words_in_dict

    # Define a function to count the frequency of spitted words
    @staticmethod
    def convert_qhu(words_in_list):
        words_in_dict = {}

        for word in words_in_list:
            word = word.lower() # make all letter to lowercase
            r = words_in_dict.get(word, 0)
            words_in_dict[word] = r+1
        return words_in_dict