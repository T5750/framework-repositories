#!/bin/bash
read op
case $op in
	a)
	echo "You selected a";;
	b)
        echo "You selected b";;
	c)
        echo "You selected c";;
	*)
        echo "Error"
esac
