/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class Substring
{
    public static void main(String[] args)
    {
        try
        {
            String string = args[0];
            int start = Integer.parseInt(args[1]);
            int num = Integer.parseInt(args[2]);
            System.out.println(string.substring(start, start + num));
        }
        catch (Exception e)
        {
            System.exit(1);
        }
    }
}
