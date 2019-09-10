#!/bin/bash
if [ -d $1 ]
then
	echo "This is a directory!"
elif [ -f $1 ]
then
	echo "This is a file!"
else
	echo "Error!"
fi
