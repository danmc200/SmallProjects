import java.util.*;
import java.io.*;
import java.math.*;

class prob22
{
    public static final List<String> names = new ArrayList<String>();
    public prob22()
    {
        readFile();
    }
    public void readFile()
    {
        try {
           BufferedReader br = new BufferedReader(new FileReader("names.txt"));
           String line = br.readLine();
           while (line != null) 
           {
                String [] text = line.split(",");
                for(String s : text)
                {
                    s = s.substring(1, s.length()-1);
                    names.add(s);
                }
                Collections.sort(names);
                for(String n : names)
                {
                    System.out.println(n);
                }
               line = br.readLine();
           }
           br.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String [] args)
    {
        prob22 prob = new prob22();
        char toIntRep = 'A' - 1;
        System.out.println('A' - toIntRep);
        BigDecimal sum = new BigDecimal("0");
        for(int i = 0; i < names.size(); i++)
        {
            String n = names.get(i);
            long score = 0L;
            for(char c : n.toCharArray())
            {
                score += c - toIntRep;   
            }
            score *= (i + 1);
            sum = sum.add(new BigDecimal(score));
        }
        System.out.println(sum);
    }
}
