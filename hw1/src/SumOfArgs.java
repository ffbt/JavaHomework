/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class SumOfArgs
{
    public static void main(String[] args)
    {
        int sum = 0;
        for (String arg : args)
        {
            int n;
            try
            {
                n = Integer.parseInt(arg);
            }
            catch (NumberFormatException e)
            {
                continue;
            }
            sum += n;
        }
        System.out.println(sum);
    }
}
