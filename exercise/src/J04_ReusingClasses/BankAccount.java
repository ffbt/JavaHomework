package J04_ReusingClasses;

public abstract class BankAccount
{
    protected String name;
    protected double balance;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public abstract boolean deposit(double money) throws IllegalArgumentException;

    public abstract boolean withdrawal(double money) throws IllegalArgumentException;

    public double getBalance()
    {
        return balance;
    }

    public abstract String getType();
}
