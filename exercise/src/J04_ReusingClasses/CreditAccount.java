package J04_ReusingClasses;

public class CreditAccount extends BankAccount
{
    @Override
    public boolean deposit(double money)
    {
        if (money < 0)
            return false;
        this.balance += money;
        return true;
    }

    @Override
    public boolean withdrawal(double money)
    {
        if (this.balance < money || money < 0)
            return false;
        this.balance -= money;
        return true;
    }

    @Override
    public String getType()
    {
        return "credit account";
    }
}
