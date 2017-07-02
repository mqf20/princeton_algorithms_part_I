#!/bin/bash

SUBMISSION_FILE_NAME=assignment5.zip

# Submit required files in a zip file called SUBMISSION_FILE_NAME

mkdir temp/
cp src/searchtrees/kdtrees/KdTree.java temp/
cp src/searchtrees/kdtrees/PointSET.java temp/

# Remove lines that start with "package" in-place
sed -i '/^[ ]*package/d' temp/KdTree.java
sed -i '/^[ ]*package/d' temp/PointSET.java

zip -j $SUBMISSION_FILE_NAME temp/*  # ignore directories

rm -rf temp/

echo "prepared $(pwd)/$SUBMISSION_FILE_NAME"

