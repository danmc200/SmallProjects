import java.util.*;
class prob19
{
    public static final Map<Integer, Integer> monthAndDays = new HashMap<Integer, Integer>();
    static
    {
        monthAndDays.put(1, 31);
        monthAndDays.put(2, 28);
        monthAndDays.put(3, 31);
        monthAndDays.put(4, 30);
        monthAndDays.put(5, 31);
        monthAndDays.put(6, 30);
        monthAndDays.put(7, 31);
        monthAndDays.put(8, 31);
        monthAndDays.put(9, 30);
        monthAndDays.put(10, 31);
        monthAndDays.put(11, 30);
        monthAndDays.put(12, 31);
    }

    public enum week
    {
        monday,
        tuesday,
        wednesday,
        thursday,
        friday,
        saturday,
        sunday;

        public week getNext()
        {
            int nextVal = this.ordinal() + 1;
            if(nextVal >= 7)
            {
                nextVal = 0;
            }
            return week.values()[nextVal];
        }
    }

    public static boolean isLeapYear(int year)
    {
        boolean leapYear = false;
        if(year % 4 != 0)
        {
            leapYear = false;
        }
        else if(year % 400 != 0)
        {
            leapYear = false;
        }
        else
        {
            leapYear = true;
        }
        return leapYear;
    }

    public week getDayOfWeekOnFirstOfNextMonth(week dayOfWeek, int month, int year)
    {
        week tmp = dayOfWeek;
        int numDays = 0;
        if(month == 2)
        {
            numDays = isLeapYear(year)?29:28;
        }
        else
        {
            numDays = monthAndDays.get(month);
        }
        for(int i = 0; i < numDays; i++)
        {
            tmp = tmp.getNext();
        }
        return tmp;
    }

    public static void main(String [] args)
    {
        prob19 prob = new prob19();
        int numSundays = 0;
        week dayOfWeek = week.monday;
        week firstDay = null;
        for(int i = 1900; i <= 2000; i++)
        {
            for(int month = 1; month <= 12; month++)
            {
                dayOfWeek = prob.getDayOfWeekOnFirstOfNextMonth(dayOfWeek, month, i);
                firstDay = dayOfWeek.getNext();
                if(i >= 1901)
                {
                    if(firstDay == week.sunday)
                    {
                        numSundays++;
                    }
                }
            }
        }
        System.out.println(firstDay);
        System.out.println(numSundays);
    }
}
