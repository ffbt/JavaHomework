/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMContact implements PIMEntity
{
    private String priority;
    private String firstName;
    private String lastName;
    private String emailAddress;
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

    public PIMContact()
    {

    }

    public PIMContact(String priority, String owner, boolean flag)
    {
        setPriority(priority);
        this.owner = owner;
        this.flag = flag;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString()
    {
        return owner + ": CONTACT " + "\n" + getVisibility() + "\n" + this.getPriority() + "\n" + firstName + " " + lastName + " " + emailAddress + "\n";
    }
}
