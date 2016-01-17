#!/usr/bin/python
import os, sys
args = ""
if(len(sys.argv) > 2):
    for arg in sys.argv[2:]:
        args += " " + arg
out = sys.argv[1].replace('.c', '.out ') 
os.system("gcc -o " + out + sys.argv[1] + "; " + out + args)
