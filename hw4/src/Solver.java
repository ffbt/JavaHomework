import java.util.Collections;
import java.util.Stack;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class Solver
{
    private Stack<String> postfixStack = new Stack<>();
    private Stack<Character> opStack = new Stack<>();
    //                               (  )  *  +   ,  -  .  /
    private int[] operatePriority = {0, 3, 2, 1, -1, 1, 0, 2}; // 运用运算符ASCII码-40做索引的运算符优先级

    public static String conversion(String expression)
    {
        Solver solver = new Solver();
        String result;
        try
        {
            expression = transform(expression);
            result = solver.calculator(expression);
            return result;
        }
        catch (Exception e)
        {
            return String.valueOf(0.0 / 0.0);
        }
    }

    private static String transform(String expression)
    {
        char[] arr = expression.toCharArray();
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == '-')
            {
                if (i == 0)
                    arr[i] = '~';
                else
                {
                    char c = arr[i - 1];
                    if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == 'E' || c == 'e')
                        arr[i] = '~';
                }
            }
        }
        if (arr[0] == '~' && arr[1] == '(')
        {
            arr[0] = '-';
            return "0" + String.valueOf(arr);
        }
        return String.valueOf(arr);
    }

    private String calculator(String expression)
    {
        Stack<String> resultStack = new Stack<>();
        prepare(expression);
        Collections.reverse(postfixStack);
        String firstValue, secondValue, currentValue;
        while (!postfixStack.isEmpty())
        {
            currentValue = postfixStack.pop();
            if (!isOperator(currentValue.charAt(0)))
            {
                currentValue = currentValue.replace("~", "-");
                resultStack.push(currentValue);
            }
            else
            {
                secondValue = resultStack.pop();
                firstValue = resultStack.pop();
                firstValue = firstValue.replace("~", "-");
                secondValue = secondValue.replace("~", "-");
                String tmpResult = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tmpResult);
            }
        }
        return resultStack.pop();
    }

    private void prepare(String expression)
    {
        opStack.push(',');
        char[] arr = expression.toCharArray();
        int currentIndex = 0;
        int count = 0;  // 上次算术运算符到本次算数运算符的字符的长度
        char currentOp, peekOp; // 当前操作符和栈顶操作符
        for (int i = 0; i < arr.length; i++)
        {
            currentOp = arr[i];
            if (isOperator(currentOp))
            {
                if (count > 0)
                    postfixStack.push(new String(arr, currentIndex, count));
                peekOp = opStack.peek();
                if (currentOp == ')')
                {
                    while (opStack.peek() != '(')
                        postfixStack.push(String.valueOf(opStack.pop()));
                    opStack.pop();
                }
                else
                {
                    while (currentOp != '(' && peekOp != ',' && compare(currentOp, peekOp))
                    {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i + 1;
            }
            else
                count++;
        }
        if (count > 1 || (count == 1 && !isOperator(arr[currentIndex])))
            postfixStack.push(new String(arr, currentIndex, count));
        while (opStack.peek() != ',')
            postfixStack.push(String.valueOf(opStack.pop()));
    }

    private boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    private boolean compare(char cur, char peek)
    {
        return operatePriority[(peek) - 40] >= operatePriority[(cur) - 40];
    }

    private String calculate(String firstValue, String secondValue, char currentOp)
    {
        String result = "";
        switch (currentOp)
        {
            case '+':
                result = String.valueOf(ArithHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(ArithHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(ArithHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(ArithHelper.div(firstValue, secondValue));
                break;
        }
        return result;
    }
}
