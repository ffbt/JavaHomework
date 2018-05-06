/**
 * 范博涛 15130110029 565267339@qq.com
 */
public class PIMNote implements PIMEntity
{
    private String priority;
    private String string;
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
    public String getVisibility()
    {
        return isPublic() ? "public" : "private";
    }

    public PIMNote()
    {

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

    public PIMNote(String priority, String owner, boolean flag)
    {
        setPriority(priority);
        this.owner = owner;
        this.flag = flag;
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
        return owner + ": NOTE " + "\n" + getVisibility() + "\n" + this.getPriority() + "\n" + string + "\n";
    }
}
