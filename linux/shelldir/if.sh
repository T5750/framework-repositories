#!/bin/bash
if [ -d $1 ]
then
	echo "This is a directory!"
else
        echo "This is not a directory!"
fi
