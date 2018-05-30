package exam;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

class Value
{
    int i;
}

public class Equivalence
{
    public static void main(String[] args)
    {
        Integer n1 = new Integer(1);
        Integer n2 = new Integer(1);
        System.out.println(n1 == n2);
        Value v1 = new Value();
        Value v2 = new Value();
        v1.i = v2.i = 100;
        System.out.println(v1.equals(v2));

        int[][] a;
        a = new int[2][];
        a[0] = new int[2];
        a[1] = new int[3];
    }
}
