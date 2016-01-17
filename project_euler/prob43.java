import java.util.*;
import java.math.*;

class prob43
{
    public static final String pandigital = "0123456789";

    public static List<Long> getCombos(String s)
    {
        List<Long> combos = new ArrayList<Long>();

        long tot = 1;
        for(long i = 1; i <= s.length(); i++)
        {
            tot*=i;
            combos.add(tot);
        }
        return combos;
    }

    public static Map<Integer, Integer> getNumber(long occ, List<Long> combos)
    {
        Map<Integer, Integer> rowAndFlips = new HashMap<Integer, Integer>();
        for(int i = combos.size()-1; i >= 0; i--)
        {
            long sub = combos.get(i);
            int flips = 0;
            while(occ - sub > 0)
            {
                occ -= sub;
                flips++;
                rowAndFlips.put(i + 1, flips);
            }
        }
        return rowAndFlips;
    }

    public static String flip(String str, int index, Map<Integer, Integer> combos)
    {
        if(index > str.length() - 1)
        {
            return str;
        }
        String tmp = "";
        tmp = str.substring(index, str.length());
        int combosIndex = str.length() - 1 - index;
        long fromLow = 0;
        if(combos.containsKey(combosIndex))
        {
            fromLow = combos.get(combosIndex);
        }
        char [] chars = tmp.toCharArray();
        Arrays.sort(chars);
        char c = chars[(int)fromLow];
        int indexSwap = index;
        for(int i = index; i < str.length(); i++)
        {
            if(str.toCharArray()[i] == c)
            {
                indexSwap = i;               
            }
        }
        char[] strArray = str.toCharArray();
        char s1 = strArray[index];
        char s2 = strArray[indexSwap];
        strArray[index] = s2;
        strArray[indexSwap] = s1;
        str = flip(new String(strArray), index+1, combos);
        return str;
    }

    public static boolean isFunkay(String str)
    {
        char [] chars = str.toCharArray();
        Map<Integer, Integer> valueAndDivisor = new HashMap<Integer, Integer>();
        valueAndDivisor.put(Integer.valueOf("" + chars[1] + "" + chars[2] + "" + chars[3]), 2);
        valueAndDivisor.put(Integer.valueOf("" + chars[2] + "" + chars[3] + "" + chars[4]), 3);
        valueAndDivisor.put(Integer.valueOf("" + chars[3] + "" + chars[4] + "" + chars[5]), 5);
        valueAndDivisor.put(Integer.valueOf("" + chars[4] + "" + chars[5] + "" + chars[6]), 7);
        valueAndDivisor.put(Integer.valueOf("" + chars[5] + "" + chars[6] + "" + chars[7]), 11);
        valueAndDivisor.put(Integer.valueOf("" + chars[6] + "" + chars[7] + "" + chars[8]), 13);
        valueAndDivisor.put(Integer.valueOf("" + chars[7] + "" + chars[8] + "" + chars[9]), 17);

        for(Integer key : valueAndDivisor.keySet())
        {
            if((double)key % valueAndDivisor.get(key) != 0)
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String [] args)
    {
        List<Long> combos = getCombos(pandigital);
        long prime = 0;
        BigInteger sum = new BigInteger("0");
        System.out.println(combos);
        System.out.println(isFunkay("1406357289"));
        for(long i = combos.get(combos.size() -1); i >= 1; i--)
        {
            Map<Integer, Integer> rowAndFlips = getNumber(i, combos);
            String s = new String(pandigital);
            String strNumber = flip(s, 0, rowAndFlips);
            if(strNumber.startsWith("0"))
            {
                System.out.println(strNumber + " " + i + " " + rowAndFlips);
                break;
            }
            if(isFunkay(strNumber))
            {
                sum = sum.add(new BigInteger(strNumber));
                System.out.println(strNumber + " " + i + " " + rowAndFlips);
                System.out.println("sum: " + sum);
            }
        }
        System.out.println("sum: " + sum);
    }
}
