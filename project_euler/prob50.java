import java.util.*;
class prob50
{
    public static boolean isPrime(long p)
    {
        List<String> endsWith = Arrays.asList(new String[]{
            "1",
            "3",
            "7",
            "9"
        });
        String str = new Long(p).toString();
        if(p > 9)
        {
            if(!endsWith.contains(str.substring(str.length()-1,str.length())))
            {
                return false;
            }
        }
        for(long i = p/2; i > 1; i--)
        {
            if(p % i == 0)
            {
                return false;
            }
        }
        return true;
    }


    public static int getNumberOfConsec(long p, int currentHighest)
    {
        List<Long> primes = new ArrayList<Long>();
        
        long sum = 0L;
        for(long i = 2; i < p; i++)
        {
            if(isPrime(i))
            {
                primes.add(i);
                sum += i;
                if(sum >= p)
                {
                    break;
                }
            }
        }
        if(primes.size() < currentHighest)
        {
            return -1;
        }
        while(sum > p)
        {
            sum -= primes.remove(0);
            if(sum < p)
            {
                return 0;
            }
            if(sum == p)
            {
                return primes.size();
            }
            
        }
        return primes.size();
    }

    public static void main(String [] args)
    {
        System.out.println("loaded");
        int longestConsec = 0;
        for(long i = 1000000-1; i >=41; i--)
        {
            if(isPrime(i))
            {
                int tmp = getNumberOfConsec(i, longestConsec);
                if(tmp == -1)
                {
                    break;
                }
                if(tmp > longestConsec)
                {
                    longestConsec = tmp;
                    System.out.println(longestConsec + " prime: " + i);
                }
            }
        }
    }
}
