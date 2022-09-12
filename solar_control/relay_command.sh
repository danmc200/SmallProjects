#!/bin/bash

echo -n $1 > /dev/ttyACM0
echo "ran command for" $1
