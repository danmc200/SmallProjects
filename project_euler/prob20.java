import java.util.*;
import java.math.*;
class prob20
{
    public static void main(String [] args)
    {
        long num = 100;
        BigDecimal bnum = new BigDecimal(num);
        for(long i = 100-1; i>0; i--)
        {
            BigDecimal bi = new BigDecimal(i);
            bnum = bnum.multiply(bi);
        }
        String str = bnum.toString();
        System.out.println(str);
        List<Integer> nums = new ArrayList<Integer>();
        for(char c : str.toCharArray())
        {
            nums.add(Integer.valueOf("" + c));                      
        }
        int sum = 0;
        for(Integer i : nums)
        {
            sum += i;
        }
        System.out.println(sum);
    }
}
