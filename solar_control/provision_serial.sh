#!/bin/bash

sudo chown daniel /dev/ttyACM0
chmod 777 /dev/ttyACM0

dms=`ps -ef | grep "dms read" | grep -v grep`
if [[ "$dms" == "" ]]
then
	echo "not found"
	screen -dms read /dev/ttyACM0 9600&
fi
