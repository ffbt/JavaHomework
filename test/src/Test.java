import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Test implements Serializable
{
    public static void main(String[] args)
    {
        Calendar calendar = Calendar.getInstance();
        String string = "a\\b\\c";
        String string1 = "192.168.1.1";
        String[] strings = string.split("\\\\");
        String[] strings1 = string1.split("\\.");
        for (int i = 0; i < strings1.length; i++)
        {
            String s = strings1[i];
            System.out.println(s);
        }
        for (int i = 0; i < strings.length; i++)
        {
            String s = strings[i];
            System.out.println(s);
        }
    }
}
