package exam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test implements Serializable
{
    static int test(int testval, int target)
    {
        int result = 0;
        if (testval > target)
            result = +1;
        else if (testval < target)
            result = -1;
        else
            result = 0;
        return result;
    }

    String x = "1";
    int y;

    public static void main(String[] args)
    {
        /*Calendar calendar = Calendar.getInstance();
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
        }*/

//        System.out.println(test(10, 5));

//        int z = 2;
//        System.out.println(x + y + z);  // compiler error

        try
        {
            String s = "ABCDE";
            byte b[] = s.getBytes();
            FileOutputStream file = new FileOutputStream("test.txt", true);
            file.write(b);
            file.close();
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
}
