/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMContact implements PIMEntity
{
    private String priority;
    private String firstName;
    private String lastName;
    private String emailAddress;

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

    public PIMContact()
    {

    }

    public PIMContact(String priority)
    {
        setPriority(priority);
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

    @Override
    public void fromString(String string)
    {

    }

    @Override
    public String toString()
    {
        return "CONTACT " + this.getPriority() + " " + firstName + " " + lastName + " " + emailAddress;
    }
}
