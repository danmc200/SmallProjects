#!/usr/bin/python
#Author Daniel Boydelatour
import random

def findNextEmptyIndex(col):
    randIndexes = []
    for x in range(0, len(col)):
        if(col[x] == 0):
            randIndexes.append(x)
    #just randomize :/
    ran = random.randint(0, len(randIndexes)-1)
    return randIndexes[ran]

def filter0s(col):
    to_ret = []
    for x in range(0, len(col)):
        if(col[x] != 0):
            to_ret.append(col[x])
    return to_ret

def pop(col, order, num=1):
    for x in range(0, num):
        p = order.pop()
        col[p] = 0

#enter partial true so it won't check for
#closing the check back to beginning
#TODO could improve...
def allIn(nums, partial=False):
    col = filter0s(nums)
    if(len(col) == 1 and partial):
        return True
    if(not partial and len(nums) > len(col)):
        return False
    remove_or_shift = lambda val, col, col2=col: (
        col.remove(val), 
        col2.insert(0,val) if(col==col2) else col2.append(val))
    black_list = []
    x = 1
    while(x < len(col)):
        tmp1 = col[0]
        tmp2 = col[x]
        #last 2 equal first 2
        if(str(tmp1)[2:] == str(tmp2)[:2]):
            remove_or_shift(tmp1, col, black_list)#remove
            remove_or_shift(tmp2, col)#shift
            if len(col) == 1 and not partial:
                #last 2 equal first 2 (closing the cyclical dependency)
                return str(col[0])[2:] == str(black_list[0])[:2]
            elif len(col) == 1 and partial:
                return True
            x = 1
        else:
            x += 1
    return False

#Note: num (cyclical num) array index corresponds to function array index
#try to find a cyclical match with numbers currently in array
#if none found at index, pop last found from array
#this is method is inefficent, but it generally works fast enough (< 1min).
def findCyclicalNums(nums, fun):
    fun_num = 0
    lastIndex = []
    order = []
    init0s = lambda n, l: [n.append(0) for x in range(0, l)]
    init0s(lastIndex, len(nums))
    while(True):
        if(allIn(nums)):
            return nums
        elif(len(filter0s(nums)) == len(nums)):
            print str(nums) + "\n" + str(order)
            nums[fun_num] = lastIndex[fun_num] = 0
            pop(nums, order, 2)
        fun_num = findNextEmptyIndex(nums)
        x = lastIndex[fun_num] + 1
        while(True):#do-while loop
            nums[fun_num] = fun[fun_num](x)
            if(nums[fun_num] > 9999):
                nums[fun_num] = lastIndex[fun_num] = 0
                if(len(order) != 0):
                    pop(nums, order)
                break 
            elif(nums[fun_num] >= 1000 and allIn(nums, True)):
                break
            x += 1
        if(nums[fun_num] != 0):
            lastIndex[fun_num] = x
            order.append(fun_num)

if __name__ == "__main__":
    fun = [
        lambda n: n * (n + 1) / 2,      #Triangle
        lambda n: n ** 2,               #Square
        lambda n: n * (3 * n - 1) / 2,  #Pentagonal
        lambda n: n * (2 * n - 1),      #Hexagonal
        lambda n: n * (5 * n - 3) / 2,  #Heptagonal
        lambda n: n * (3 * n - 2)       #Octagonal
    ]
    nums = [0,0,0,0,0,0]
    nums2 = [0,0,0] #in problem description
    nums = findCyclicalNums(nums, fun)
    print "Answer: " + str(nums) + "\nnum sum: " + str(sum(nums))
