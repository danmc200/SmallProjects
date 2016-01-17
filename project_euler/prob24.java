class prob24
{
    public static final String DIGITS = "0123456789";

    public long findAllCombos(int cursor, int [] nums, long num)
    {
        for(int i = cursor; i < nums.length-1; i++)
        {
        }
        //move cursor num through backwords
        for(int i = cursor; i < nums.length; i++)
        {
            //move previous num through
            num += findAllCombos(i, nums, num);
        }
        //move cursor num through forwords till before 1st
        for(int i = cursor; i > 0; i--)
        {
            num++;
        }
        cursor++;
        num += findAllCombos(cursor, nums, num);
        return num;
    }

    public static void main(String [] args)
    {
        prob24 prob = new prob24();
        char[] digits = DIGITS.toCharArray();
        int [] nums = new int[digits.length];
        long tot = 0l;
        for(int i = 0; i < 1; i++)//nums.length; i++)
        {
            char c = ' ';
            c = digits[i]; 
            for(int j = 0; j < nums.length; j++)
            {
                if(j != i)
                {
                    c = digits[j];
                }
                nums[j] = Integer.valueOf("" + c);
            }
            tot += prob.findAllCombos(nums.length-1, nums, tot);
        }
        System.out.println(tot);
    }
}
