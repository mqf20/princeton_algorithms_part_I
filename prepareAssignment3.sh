#!/bin/bash

SUBMISSION_FILE_NAME=assignment3.zip

# Submit Percolation.java and PercolationStats.java in a zip file called SUBMISSION_FILE_NAME

mkdir temp/
cp src/sort/collinear/BruteCollinearPoints.java temp/
cp src/sort/collinear/FastCollinearPoints.java temp/
cp src/sort/collinear/Point.java temp/

# Remove lines that start with "package" in-place
sed -i '/^[ ]*package/d' temp/BruteCollinearPoints.java
sed -i '/^[ ]*package/d' temp/FastCollinearPoints.java
sed -i '/^[ ]*package/d' temp/Point.java

zip -j $SUBMISSION_FILE_NAME temp/*  # ignore directories

rm -rf temp/

echo "prepared $(pwd)/$SUBMISSION_FILE_NAME"

