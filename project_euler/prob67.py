#!/usr/bin/python

class Binary:
    value = 0
    total = 0

def build_binary_list(triangle_nums):
    binaries = []
    for i in xrange(len(triangle_nums)):    
        binary_row = []
        for x in xrange(len(triangle_nums[i])):
            b = Binary()
            b.value = int(triangle_nums[i][x])
            b.total = int(triangle_nums[i][x])
            binary_row.append(b)
        binaries.append(binary_row)
    return binaries

def build_happy_path(binaries):
    for i in range(len(binaries)-1, -1, -1):
        for x in range(len(binaries[i])):
            cur_val = binaries[i][x]

            if(len(binaries) == i+1):
                continue
            else:
                next_val_right = binaries[i+1][x+1]
                next_val_left = binaries[i+1][x]

            if(next_val_left.total > next_val_right.total):
                cur_val.total = next_val_left.total + cur_val.total
            else:
                cur_val.total = next_val_right.total + cur_val.total

    print (binaries[0][0].total)


if  __name__ == '__main__': 
    fopen = open('triangle.txt')
    triangle_nums = []
    for x in fopen.readlines():
        s = x.split(' ')
        triangle_nums.append(s)
    print("last in list")
    binaries = build_binary_list(triangle_nums)
    binaries = build_happy_path(binaries)
