/**
 * 范博涛 15130110029 565267339@qq.com
 */
public class PIMNote implements PIMEntity
{
    private String priority;
    private String string;

    public PIMNote()
    {

    }

    public PIMNote(String priority)
    {
        setPriority(priority);
    }

    public void setString(String string)
    {
        this.string = string;
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
    public void fromString(String string)
    {

    }

    @Override
    public String toString()
    {
        return "NOTE " + this.getPriority() + " " + string;
    }
}
