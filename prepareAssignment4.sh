#!/bin/bash

SUBMISSION_FILE_NAME=assignment4.zip

# Submit Percolation.java and PercolationStats.java in a zip file called SUBMISSION_FILE_NAME

mkdir temp/
cp src/priorityqueues/kpuzzle/Board.java temp/
cp src/priorityqueues/kpuzzle/Solver.java temp/

# Remove lines that start with "package" in-place
sed -i '/^[ ]*package/d' temp/Board.java
sed -i '/^[ ]*package/d' temp/Solver.java

zip -j $SUBMISSION_FILE_NAME temp/*  # ignore directories

rm -rf temp/

echo "prepared $(pwd)/$SUBMISSION_FILE_NAME"

