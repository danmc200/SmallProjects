#!/usr/bin/python
#Author Daniel Boydelatour
import math, sys

def formula(x2, y2, d):
    return x2 - (d * y2)

def isqrt(n):
    sqr = long(math.sqrt(n))
    return (sqr ** 2 == n)

def remSqrts(col):
    to_delete = []
    for x in range(len(col)):
        if(isqrt(col[x])):
            to_delete.append(col[x])
    for item in to_delete:
        col.remove(item)
    return col

def get_match(x, col):
    matches = []
    for n in col:
        x2 = x**2
        y2 = ((x2-1) / n)
        if(isqrt(y2)):
            result = formula(x2, y2, n)
            if(result == 1):
                matches.append(n)
                print col
                print n, x, y2
                print "count: ", len(col)
    return matches

if __name__ == "__main__":
    to_match = [x for x in range(2, 1000)]
    to_match = remSqrts(to_match)
    print to_match
    x = 2
    while(len(to_match) > 1):
        tmp = get_match(x, to_match)
        to_match = [item for item in to_match if item not in tmp]
        x += 1
    print to_match
