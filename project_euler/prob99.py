#!/usr/bin/python

def read_proj():
    fle = open('p099_base_exp.txt')
    f = fle.readlines()
    p = []
    for i in f:
        j = i.split(',')
        j[1] = j[1].strip()
        p.append(int(j[0])**int(j[1]))
    m = max(p)
    print p.index(m)

if __name__ == "__main__":
    read_proj()
