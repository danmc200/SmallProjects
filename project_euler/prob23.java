import java.util.*;
class prob23
{
    public long getSumOfDivisors(long num)
    {
        long sum = 0; 
        for(long lo = num /2; lo > 0; lo--)
        {
            if((num / (double)lo) % 1 ==0)
            {
                sum+=lo;
            }
        }
        return sum;
    }

    public static void main(String [] args)
    {
        prob23 prob = new prob23();
        System.out.println(prob.getSumOfDivisors(28));
        List<Long> abd = new ArrayList<Long>();
        List<Long> ints = new ArrayList<Long>();
        for(long i = 28123; i > 0; i--)
        {
            long sum = prob.getSumOfDivisors(i);
            if(sum > i)
            {
                abd.add(i);
            }
        }
        System.out.println("calculate abd pairs");
        for(int i = 0; i < abd.size(); i++)
        {
            long num1 = abd.get(i);
            for(int k = abd.size() -1; k > 0; k--)
            {
                long num2 = abd.get(k);
                if(num1 + num2 <= 28123)
                {
                    if(!ints.contains(num1 + num2))
                        ints.add(num1 + num2);
                }
                else
                {
                    break;
                }
            }
        }
        Collections.sort(ints);
        System.out.println(ints);
        System.out.println("calculate answer");
        long sumAll = 0l;
        List<Long> nonMatch = new ArrayList<Long>();
        for(long i = 28123; i > 0; i--)
        {
            if(!ints.contains(i))
            {
                sumAll += i;
                nonMatch.add(i);
            }
        }
        System.out.println(nonMatch);
        System.out.println(sumAll);
    }
}
