#!/usr/bin/python
#Author Daniel Boydelatour
#Summary:
#print the sum of 5 numbers which are all prime
#and all combinations of concatenated pairs between
#them are primes as well
import prime

def col_pairs_is_prime(col):
	for x in range(0, len(col)):
		i = x
		for a in range(1, len(col)):
			i += 1
			if(i >= len(col)):
				i -= len(col)
			combined = str(col[x]) + str(col[i])
			is_prime = prime.is_prime(int(combined))
			if(not is_prime):
				return False
	return True

def get5pairs(col, x, num_of_pairs):
	while(x < 10000):#set limit prime search to 10,000 (can be adjusted)
		if(prime.is_prime(x) and x not in col):
			new_col = list(col)
			new_col.append(x)
			if col_pairs_is_prime(new_col):
				print (new_col)
				if len(new_col) == num_of_pairs: #found the pairs we need. Done
					return new_col
				else:
					a = x
					col = get5pairs(new_col, x, num_of_pairs)
					x = a
					if(len(col) > len(new_col)):
						return col
		x += 1
	del col[-1]
	return col

if __name__ == '__main__':
	print sum(get5pairs([], 3, 5))
