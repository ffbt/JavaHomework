package J04_ReusingClasses;

import java.util.LinkedList;
import java.util.Queue;

public class Transactions
{
    private Queue<Transaction> transactions = new LinkedList<>();
    private static final int transactionNum = 6;

    public void add(Transaction transaction)
    {
        if (this.transactions.size() == transactionNum)
            this.transactions.poll();
        this.transactions.offer(transaction);
    }

    public void print()
    {
        for (Transaction transaction : transactions)
            System.out.println(transaction);
    }
}
