package J04_ReusingClasses;

public class CashAccount extends BankAccount
{
    private Transactions transactions = new Transactions();

    @Override
    public Transactions getTransactions()
    {
        return transactions;
    }

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
        return "cash account";
    }
}
