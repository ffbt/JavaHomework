package J04_ReusingClasses;

import java.lang.reflect.Constructor;
import java.util.*;

public class BankSystem
{
    private Map<String, Map<BankType, BankAccount>> accountMap;

    public BankSystem()
    {
        this.accountMap = new HashMap<>();
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
                return this.createTransaction(name, bankType, Operation.DEPOSIT, balance);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTransaction(String name, BankType bankType, Operation operation, double money) throws IllegalArgumentException
    {
        if (!this.accountMap.containsKey(name) || !(this.accountMap.get(name)).containsKey(bankType))
            return false;
        Transaction transaction = Transaction.createTransaction((this.accountMap.get(name)).get(bankType), operation, money);
        (this.accountMap.get(name)).get(bankType).getTransactions().add(transaction);
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

    public int getAccountNum()
    {
        int num = 0;
        for (Map<BankType, BankAccount> map : accountMap.values())
            num += map.size();
        return num;
    }

    public void printTransactions(String name, BankType bankType)
    {
        if (!this.accountMap.containsKey(name) || !(this.accountMap.get(name)).containsKey(bankType))
            return;
        (this.accountMap.get(name)).get(bankType).getTransactions().print();
    }
}
