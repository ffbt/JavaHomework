import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMTodo implements PIMEntity, Dateable
{
    private String string;
    private String date;
    private String priority;
    private boolean flag;
    private String owner;

    @Override
    public String getOwner()
    {
        return owner;
    }

    @Override
    public void setPublic(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public boolean isPublic()
    {
        return flag;
    }

    @Override
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

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

    @Override
    public String getVisibility()
    {
        return isPublic() ? "public" : "private";
    }

    public PIMTodo()
    {

    }

    @Override
    public void setDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        this.date = sdf.format(date);
    }

    public PIMTodo(String priority, String owner, boolean flag)
    {
        setPriority(priority);
        this.owner = owner;
        this.flag = flag;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString()
    {
        return owner + ": TODO " + "\n" + getVisibility() + "\n" + this.getPriority() + "\n" + date + "\n" + string + "\n";
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
