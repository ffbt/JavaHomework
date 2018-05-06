import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMAppointment implements PIMEntity, Dateable
{
    private String priority;
    private String date;
    private String description;

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

    public PIMAppointment()
    {

    }

    public PIMAppointment(String priority)
    {
        setPriority(priority);
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    @Override
    public void fromString(String string)
    {

    }

    @Override
    public String toString()
    {
        return "APPOINTMENT" + " " + this.getPriority() + " " + date + " " + description;
    }
}
