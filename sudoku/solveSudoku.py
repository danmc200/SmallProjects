#!/usr/bin/python
#https://projecteuler.net/problem=96

def getCellToFill(grid, row, col):
    for r in range(row,9):
        for c in range(col,9):
            if grid[r][c] == 0:
                    return r,c
    for r in range(0,9):
        for c in range(0,9):
            if grid[r][c] == 0:
                return r,c
    return -1,-1

def isNumValid(grid, row, col, num):
    num_valid = all([num != grid[row][x] for x in range(9)]) and all([num != grid[x][col] for x in range(9)])
    if num_valid:
        secTopX, secTopY = 3*(row/3), 3*(col/3)
        for x in range(secTopX, secTopX+3):
            for y in range(secTopY, secTopY+3):
                if grid[x][y] == num:
                    return False
        return True
    return False

def solveSudoku(grid, row=0, col=0):
    row,col = getCellToFill(grid, row, col)
    if row == -1:
        return True
    for e in range(1,10):
        if isNumValid(grid,row,col,e):
            grid[row][col] = e
            if solveSudoku(grid, row, col):
                return True
            #Undo the current cell and pick another
            grid[row][col] = 0
    return False

def readSudokus(filename):
    fle = open(filename, 'r')
    grid_collection = []
    grid = []
    for x in fle.readlines():
        x = x.replace('\r\n', '')
        if('Grid' in x or len(x) == 0):
            if(len(grid) > 0):
                grid_collection.append(grid)
                grid = []
        else:
            row = []
            for c in x:
                row.append(int(c))
            grid.append(row)
    return grid_collection

def solve(filename):
    grid_collection = readSudokus(filename)
    sum_all = 0
    for gr in grid_collection:
        solveSudoku(gr)
        #sum of first 3 digits in top left corner 
        sum_all += int(str(gr[0][0]) + str(gr[0][1]) + str(gr[0][2]))
    print sum_all
    return grid_collection

if __name__ == "__main__":
    solve('sudoku.txt')
