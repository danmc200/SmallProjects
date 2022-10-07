#!/bin/bash

dms=`ps -ef | grep "dms read" | grep -v grep`
if [[ "$dms" == "" ]]
then
	echo "not found"
	screen -dms read /dev/ttyACM0 9600&
fi

#ask for read from device
cat /dev/ttyACM0 > read.txt
echo -n giv0 > /dev/ttyACM0
