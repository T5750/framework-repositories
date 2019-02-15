#!/bin/bash
i=0
while [ $i -le 100 ]
do
	i=`expr $i + 1`
	if [ $i -eq 5 -o $i -eq 10 ]
		then continue;
	else
		echo "This number is $i"
	fi
	if [ $i -eq 15 ]
		then break;
	fi
done
