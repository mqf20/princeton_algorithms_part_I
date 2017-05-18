#!/bin/bash

SUBMISSION_FILE_NAME=assignment1.zip

# Submit Percolation.java and PercolationStats.java in a zip file called SUBMISSION_FILE_NAME

mkdir temp/
cp src/dynamicconnectivity/percolation/Percolation.java temp/
cp src/dynamicconnectivity/percolation/PercolationStats.java temp/

# Remove lines that start with "package" in-place
sed -i '/^[ ]*package/d' temp/Percolation.java
sed -i '/^[ ]*package/d' temp/PercolationStats.java

zip -j $SUBMISSION_FILE_NAME temp/*  # ignore directories

rm -rf temp/

echo "prepared $(pwd)/$SUBMISSION_FILE_NAME"
