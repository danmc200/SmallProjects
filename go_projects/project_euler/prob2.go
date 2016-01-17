package main

import "fmt"

func main() {
	var sum int = 0
	var fib_num int = 0
	var prev1 int = 1
	var prev2 int = 1

	for i := 1; fib_num < 4000000; i++ {
		if fib_num%2 == 0 {
			sum += fib_num
		}
		fib_num = prev1 + prev2

		prev2 = prev1
		prev1 = fib_num
	}
	fmt.Printf("%d\n", sum)
}
