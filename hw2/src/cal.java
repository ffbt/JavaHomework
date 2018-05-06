import java.util.Calendar;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class cal
{
    private final static String[] DAYS = {"Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"};

    private static final String[] MONTHS = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    public static void main(String[] args)
    {
        int month, year;
        Calendar calendar = Calendar.getInstance();
        try
        {
            month = Integer.parseInt(args[0]);
            year = Integer.parseInt(args[1]);
            if (month < 1 || month > 12 || year < 0)
                throw new Exception();
            month--;
        }
        catch (Exception e)
        {
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        System.out.println(MONTHS[month] + " " + year);
        for (int i = 0; i < 7; i++)
            System.out.format("%4s", DAYS[i].substring(0, 2));
        System.out.println();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < week; i++)
            System.out.print("\t");
        while (calendar.get(Calendar.MONTH) == month)
        {
            System.out.format("%4d", calendar.get(Calendar.DATE));
            calendar.add(Calendar.DATE, 1);
            week++;
            if (week % 7 == 0)
                System.out.println();
        }
    }
}
