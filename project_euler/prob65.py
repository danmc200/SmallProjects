#!/usr/bin/python
#Author Daniel Boydelatour

def convergents(val):
    a = 1
    b = 2
    for x in range(2, 101):
        temp = a
        if(x % 3 == 0):
            c = 2 * (x / 3)
        else:
            c = 1
        a = b
        b = c * a + temp
    return b

def sum_digits(n):
   r = 0
   while n:
       r, n = r + n % 10, n / 10
   return r
            
if __name__ == "__main__":
    print sum_digits(convergents(100))
