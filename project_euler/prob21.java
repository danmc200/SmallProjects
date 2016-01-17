import java.util.*;
class prob21
{
    public static long findSumOfDivisors(long num)
    {
        long sum = 0;
        for(double lo = (num / 2.0); lo > 0; lo--)
        {
            if((num / lo) % 1 == 0)
            {
                sum += lo;
            }
        }
        return sum;
    }

    public static void main(String [] args)
    {
        long n1 = findSumOfDivisors(220);
        long n2 = findSumOfDivisors(284);
        System.out.println(n1 + " " + n2);

        Map<Long, Long> numAndD = new HashMap<Long, Long>();
        for(long i = 9999; i > 0; i--)
        {
            numAndD.put(i, findSumOfDivisors(i));
        }
        int match = 0;
        int sum = 0;
        for(Long key : numAndD.keySet())
        {
            Long val = numAndD.get(key);
            if(numAndD.containsKey(val) && !val.equals(key))
            {
                if(numAndD.get(val).equals(key))
                {
                    sum += key;
                    System.out.println("val 1: " + key + " " + val);
                    System.out.println("val 2: " + val + " " + val);
                    match++;
                }
            }
        }
        System.out.println(match + " " + match / 2);
        System.out.println(sum);
    }
}
