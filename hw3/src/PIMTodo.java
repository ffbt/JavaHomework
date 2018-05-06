import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMTodo implements PIMEntity, Dateable
{
    private String string;
    private String date;
    private String priority;

    @Override
    public String getDate()
    {
        return date;
    }

    @Override
    public String getPriority()
    {
        return priority;
    }

    @Override
    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public PIMTodo()
    {

    }

    public PIMTodo(String priority)
    {
        setPriority(priority);
    }

    public void setString(String string)
    {
        this.string = string;
    }

    @Override
    public void fromString(String string)
    {

    }

    @Override
    public String toString()
    {
        return "TODO " + this.getPriority() + " " + date + " " + string;
    }

    @Override
    public boolean setDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            sdf.parse(date);
        }
        catch (ParseException e)
        {
            return false;
        }
        this.date = date;
        return true;
    }
}
