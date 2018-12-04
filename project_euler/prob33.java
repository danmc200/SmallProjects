import java.util.*;
class prob33
{
    public static List<Integer> getDivisors(int num)
    {
        List<Integer> divisors = new ArrayList<Integer>();
        for(int lo = num; lo > 1; lo--)
        {
            if((double)num / lo % 1 == 0)
            {
                divisors.add(lo);
            }
        }
        System.out.println(divisors);
        return divisors;
    }

    public static int getHighestCommon(List<Integer> a, List<Integer> b)
    {
        int highest = -1;
        for(int a1 : a)
        {
            if(b.contains(a1))
            {
                if(a1 > highest)
                {
                    highest = a1;
                }
            }
        }
        
        return highest;
    }

    public static void main(String [] args)
    {
//        int n = 49;
//        int d = 98;
//        int highest = 1;
//        while(highest != -1)
//        {
//            n /= highest;
//            d /= highest;
//            highest = getHighestCommon(getDivisors(n), getDivisors(d));
//        }
        List<Integer[]> odds = new ArrayList<Integer[]>();
        for(int i = 1; i < 100; i++)
        {
            for(int j = i + 1; j < 100; j++)
            {
                int n = i;
                int d = j;
                int highest = 1;
                while(highest != -1)
                {
                    n /= highest;
                    d /= highest;
                    highest = getHighestCommon(getDivisors(n), getDivisors(d));
                    if(highest >= i)
                    {
                        odds.add(new Integer[]{n, d});
                    }
                }
            }
        }
        for(Integer[] t : odds)
            System.out.println(t[0] + "/" + t[1]);
    }
}
