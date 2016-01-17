import java.util.*;
class prob27
{
    public static boolean isPrime(int p)
    {
        Integer [] primesEnd = {
            1, 3, 7, 9
        };
        List<Integer> primesEndList = Arrays.asList(primesEnd);
        String str = new Integer(p).toString();
        if(str.length() > 2)
        {
            if(!primesEndList.contains(new Integer("" + str.charAt(str.length()-1))))
            {
                return false;
            }
        }
        if(p < 2)
        {
            return false;
        }
        for(int lo = p/2; lo > 1; lo--)
        {
            if((double)p / lo % 1 == 0)
            {
                return false;
            }
        }

        return true;
    }

    public static int getP(int n, int a, int b)
    {
        int p = (int)Math.pow(n, 2) + (a*n) + b;
        return p;
    }

    public static void main(String [] args)
    {
        int numOfPrimes = 0;
        for(int a = -1000; Math.abs(a) <= 1000; a++)
        {
            for(int b = -1000; Math.abs(b) <= 1000; b++)
            {
                int n = 0;
                while(true)
                {
                    int p = getP(n, a, b);
                    boolean isPrime = isPrime(p);
                    if(!isPrime)
                    {
                        break;
                    }
                    n++;
                    if(n >= numOfPrimes)
                    {
                        numOfPrimes = n;
                        System.out.println("n: " + n + "a*b: " + a*b);
                    }
                }
            }
        }
    }
}
