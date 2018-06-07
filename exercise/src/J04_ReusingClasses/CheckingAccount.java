package J04_ReusingClasses;

public class CheckingAccount extends BankAccount
{
    private Transactions transactions = new Transactions();

    @Override
    public Transactions getTransactions()
    {
        return transactions;
    }

    public CheckingAccount()
    {
    }

    public CheckingAccount(String name, double balance) throws IllegalArgumentException
    {
        if (balance < 0)
            throw new IllegalArgumentException("the balance is negative");
        this.name = name;
        this.balance = balance;
    }

    @Override
    public boolean deposit(double money) throws IllegalArgumentException
    {
        if (money < 0)
            throw new IllegalArgumentException("the amount deposit is negative");
        this.balance += money;
        return true;
    }

    @Override
    public boolean withdrawal(double money) throws IllegalArgumentException
    {
        if (money < 0)
            throw new IllegalArgumentException("the amount withdrawn is negative");
        if (money > this.balance)
            throw new IllegalArgumentException("the amount withdrawn exceeds the current balance");
        this.balance -= money;
        return true;
    }

    @Override
    public String getType()
    {
        return "checking account";
    }
}
