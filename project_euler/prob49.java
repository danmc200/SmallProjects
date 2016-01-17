import java.util.*;
class prob49
{
    public static boolean isPrime(int p)
    {
        List<String> endsWith = Arrays.asList(new String[]{
            "1",
            "3",
            "7",
            "9"
        });
        String str = new Integer(p).toString();
        if(p > 9)
        {
            if(!endsWith.contains(str.substring(str.length()-1,str.length())))
            {
                return false;
            }
        }
        for(int i = p/2; i > 1; i--)
        {
            if(p % i == 0)
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isIntegersSameDigits(int a, int b)
    {
        char[] charA = new Integer(a).toString().toCharArray();
        Arrays.sort(charA);
        char[] charB = new Integer(b).toString().toCharArray();
        Arrays.sort(charB);
        for(int i = 0; i < charA.length; i++)
        {
            if(charA[i] != charB[i])
            {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> findMatchingDigits(int d, List<Integer> primes, int minSize)
    {
        List<Integer> matches = new ArrayList<Integer>();
        matches.add(d);
        for(int p : primes)
        {
            if(d == p)
            {
                continue;
            }
            if(isIntegersSameDigits(d, p))
            {
                matches.add(p);
            }
        }
        if(matches.size() < minSize)
        {
            return new ArrayList<Integer>();
        }
        return matches;
    }

    public static String getKey(int i)
    {
        char [] key = new Integer(i).toString().toCharArray();
        Arrays.sort(key);
        return new String(key);
    }

    public static boolean passesRule(List<Integer> matches)
    {
        List<List<Integer>> differences = new ArrayList<List<Integer>>();
        for(int i = 0; i < matches.size(); i++)
        {
            int first = matches.get(i);
            List<Integer> diffs = new ArrayList<Integer>();
            for(int j = i+1; j < matches.size(); j++)
            {
                int second = matches.get(j);
                int diff = Math.abs(first - second);
                diffs.add(diff);
            }
            differences.add(diffs);
        }
        outer:
        for(int i = 0; i < differences.size(); i++)
        {
            List<Integer> diff1 = new ArrayList<Integer>();
            diff1 = differences.get(i);
            for(int j = 0; j < diff1.size(); j++)
            {
                int diffJ = diff1.get(j);
                List<Integer> diff2s = differences.get(i+j+1);
                if(diff2s.contains(diffJ))
                {
                    System.out.println(diffJ);
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String [] args)
    {
        List<Integer> primes = new ArrayList<Integer>();
        for(int i = 1000; i <= 9999; i++)
        {
            if(isPrime(i))
            {
                primes.add(i);
            }
        }
        Map<String, List<Integer>> keyAndMatches = new HashMap<String, List<Integer>>();
        for(int p : primes)
        {
            String key = getKey(p);
            if(!keyAndMatches.containsKey(key))
            {
                List<Integer> matches = findMatchingDigits(p, primes, 3);
                if(!matches.isEmpty())
                {
                    keyAndMatches.put(key, matches);
                }
            }
        }
        for(String key : keyAndMatches.keySet())
        {
            List<Integer> matches = keyAndMatches.get(key);
            if(passesRule(matches))
            {
                System.out.println(matches);
            }
        }
    }
}
