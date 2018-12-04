import java.util.*;
import java.math.*;

class prob32
{
    public static final String digits = "123456789";

    public boolean longArraysEqualContents(Long[] a, Long[] b)
    {
        long [] a2 = new long[a.length];
        long [] b2 = new long[b.length];

        for(int i = 0; i < a.length; i++)
        {
            long t = a[i];
            a2[i] = t;
        }
        for(int i = 0; i < b.length; i++)
        {
            long t = b[i];
            b2[i] = t;
        }
        Arrays.sort(a2);
        Arrays.sort(b2);
        
        for(int i = 0; i < a.length; i++)
        {
            if(a2[i] != b2[i])
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String [] args)
    {
        prob32 prob = new prob32();
        List<Long[]> pandigital = new ArrayList<Long[]>();
        long sum = 0;
        for(long i = 1; i < 1000; i++)
        {
            for(long j = 1; j < 10000; j++)
            {
                String s1 = new Long(i).toString();
                String s2 = new Long(j).toString();
                long prod = i * j;
                String prodStr = new Long(prod).toString();
                String allStr = prodStr + s1 + s2;

                char [] tmp = allStr.toCharArray();
                List<Character> chars = new ArrayList<Character>();
                for(char c : tmp)
                {
                    chars.add(c);
                }
                Collections.sort(chars);
                allStr = "";
                for(char c : chars)
                {
                    allStr  += "" + c;  
                }
                if(allStr.equals(digits))
                {
                    Long [] toAdd = new Long[]{i, j, prod};
                    boolean isAdd = true;
                    for(Long [] pans : pandigital)
                    {
                        if(prob.longArraysEqualContents(pans, toAdd) ||
                            prod == pans[2]
                            )
                        {
                            isAdd = false;
                        }
                    }
                    if(isAdd)
                    {
                        sum += prod;
                        pandigital.add(toAdd);
                        System.out.println(i + " " + j + " " + prod);
                        System.out.println("sum: " + sum);
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
