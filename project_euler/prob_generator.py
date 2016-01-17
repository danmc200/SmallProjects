#!/usr/bin/python
import os, sys
prob_num = sys.argv[1]
cont = "class prob" + str(prob_num) +"""
{
    public static void main(String [] args)
    {

    }
}"""
file_name = "prob" + prob_num + ".java"
fle = open(file_name, 'w')
fle.write(cont)
fle.close()
