#!/bin/bash

#if 11 correct spot and correct character
#if 01 correct character and not correct spot
function checkKey(){
    echo "$1"
    wget http://simple-snow-3171.herokuapp.com/?key="$1" --output-document index.html 2>/dev/null
    a=`cat index.html | sed 's|<[/]*body>||g' | sed 's|<[/]*html>||g'`
    echo $a
    if [[ $a != *"KEY DENIED"* ]]
    then
        exit 1;
    fi;
    if [[ $a == *"11" ]]
    then
        result=1;
    elif [[ $a == *"01" ]]
    then
        result=0;
    else
        result=-1;
    fi
}
function findNext(){
    for x in $chars; do
        checkKey $key$x;
        resultCheckKeyOnly $result $x
    done;
}

function getChars(){
    for x in {a..z}; do
        checkKey $x;
        resultCheck $result $x
    done;
    for x in {A..Z}; do
        checkKey $x;
        resultCheck $result $x
    done;
    for x in {0..9}; do
        checkKey $x;
        resultCheck $result $x
    done;
}
function resultCheck(){
    if [[ "$1" == "1" ]]
    then
        echo "match";
        key="$key"$2"";
        chars="$chars "$2"";
    fi;
    if [[ "$1" == "0" ]]
    then
        echo "almost match";
        chars="$chars "$2"";
    fi;
}
function resultCheckKeyOnly(){
    if [[ "$1" == "1" ]]
    then
        echo "match";
        key="$key"$2"";
    fi;
}

key=""
chars=""
getChars
while [ true ]; do
    findNext
done;
echo $key
echo $chars

