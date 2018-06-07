package J04_ReusingClasses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction
{
    private Date date;
    private BankAccount bankAccount;
    private Operation operation;
    private double money;
    private boolean state = false;

    public static Transaction createTransaction(BankAccount bankAccount, Operation operation, double money)
    {
        Transaction transaction = new Transaction(bankAccount, operation, money);
        transaction.work();
        return transaction;
    }

    private Transaction(BankAccount bankAccount, Operation operation, double money)
    {
        this.date = new Date();
        this.bankAccount = bankAccount;
        this.operation = operation;
        this.money = money;
    }

    private void work() throws IllegalArgumentException
    {
        switch (operation)
        {
            case DEPOSIT:
                state = this.bankAccount.deposit(money);
                break;
            case WITHDRAWAL:
                state = this.bankAccount.withdrawal(money);
                break;
        }
    }

    @Override
    public String toString()
    {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(this.date)) + " " +
                this.bankAccount.getType() + " " +
                this.bankAccount.getName() + " " +
                this.operation + " " +
                this.money + " state: " +
                state + "\tbalance: " +
                this.bankAccount.getBalance();
    }
}
