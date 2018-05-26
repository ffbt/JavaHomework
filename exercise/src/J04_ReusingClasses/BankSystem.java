package J04_ReusingClasses;

import java.lang.reflect.Constructor;
import java.util.*;

public class BankSystem
{
    private Map<String, Map<BankType, BankAccount>> accountMap;
    private Queue<Transaction> transactions;
    private static final int transactionNum = 6;

    public BankSystem()
    {
        this.accountMap = new HashMap<>();
        this.transactions = new LinkedList<>();
    }

    public boolean createAccount(String name, BankType bankType) throws IllegalArgumentException
    {
        return this.createAccount(name, bankType, 0);
    }

    public boolean createAccount(String name, BankType bankType, double balance) throws IllegalArgumentException
    {
        try
        {
            if (!this.accountMap.containsKey(name))
                this.accountMap.put(name, new HashMap<>());
            if (!(this.accountMap.get(name)).containsKey(bankType))
            {
                Class accountClass = Class.forName("J04_ReusingClasses." + bankType.toString());
                Constructor accountConstructor = accountClass.getConstructor();
                BankAccount bankAccount = (BankAccount) accountConstructor.newInstance();
                bankAccount.setName(name);
                (this.accountMap.get(name)).put(bankType, bankAccount);
                return bankAccount.deposit(balance);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void outputTransaction()
    {
        for (Transaction transaction : transactions)
            System.out.println(transaction);
    }

    private void addTransaction(Transaction transaction)
    {
        if (this.transactions.size() == transactionNum)
            this.transactions.poll();
        this.transactions.offer(transaction);
    }

    public boolean createTransaction(String name, BankType bankType, Operation operation, double money) throws IllegalArgumentException
    {
        if (!this.accountMap.containsKey(name) || !(this.accountMap.get(name)).containsKey(bankType))
            return false;
        Transaction transaction = new Transaction((this.accountMap.get(name)).get(bankType), operation, money);
        transaction.work();
        this.addTransaction(transaction);
        return true;
    }

    public boolean changeName(String oldName, String newName)
    {
        if (this.accountMap.containsKey(newName))
            return false;
        Map<BankType, BankAccount> map = this.accountMap.get(oldName);
        for (BankAccount bankAccount : map.values())
            bankAccount.setName(newName);
        this.accountMap.remove(oldName);
        this.accountMap.put(newName, map);
        return true;
    }
}
