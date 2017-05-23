#!/bin/bash

SUBMISSION_FILE_NAME=assignment2.zip

# Submit Percolation.java and PercolationStats.java in a zip file called SUBMISSION_FILE_NAME

mkdir temp/
cp src/stacksqueues/Deque.java temp/
cp src/stacksqueues/RandomizedQueue.java temp/
cp src/stacksqueues/Permutation.java temp/

# Remove lines that start with "package" in-place
sed -i '/^[ ]*package/d' temp/Deque.java
sed -i '/^[ ]*package/d' temp/RandomizedQueue.java
sed -i '/^[ ]*package/d' temp/Permutation.java

zip -j $SUBMISSION_FILE_NAME temp/*  # ignore directories

rm -rf temp/

echo "prepared $(pwd)/$SUBMISSION_FILE_NAME"

