
print "this will be printed"; 
import pandas as pd
import re
from bs4 import BeautifulSoup
import nltk
nltk.download();

from nltk.corpus
import stopwords


train = pd.read_csv("../labeledTrainData.tsv",header=0,delimiter="\t",quoting=3);
print train["review"][0];
example = BeautifulSoup(train["review"][0]);
print example.get_text()
letters_only = re.sub("[^a-zA-Z]","",example.get_text())
#print letters_only;
lower_case = letters_only.lower();
words = lower_case.split();

