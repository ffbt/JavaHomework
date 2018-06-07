package J04_ReusingClasses;

import java.util.Random;

public class TestBankSystem
{
    public static void main(String[] args) throws IllegalArgumentException
    {
        String[] names = new String[10];
        Random random = new Random();
        BankSystem bankSystem = new BankSystem();
        for (int i = 0; i < names.length; i++)
        {
            String name = "name_" + i;
            names[i] = name;
            bankSystem.createAccount(name, BankType.CashAccount);
            bankSystem.createTransaction(name, BankType.CashAccount, Operation.DEPOSIT, i);
        }
        bankSystem.printTransactions("name_1", BankType.CashAccount);
        System.out.println("========================================================================");
        bankSystem.changeName(names[5], "Name_5");
        names[5] = "Name_5";
        for (int i = 0; i < names.length; i++)
            bankSystem.createTransaction(names[i], BankType.CashAccount, Operation.WITHDRAWAL, random.nextInt(10));
        bankSystem.printTransactions("name_1", BankType.CashAccount);
        System.out.println("========================================================================");
        for (int i = 0; i < names.length; i++)
        {
            String name = names[i];
            bankSystem.createAccount(name, BankType.CheckingAccount, random.nextInt(20) - 10);
        }
        for (int i = 0; i < names.length; i++)
        {
            String name = names[i];
            bankSystem.createTransaction(name, BankType.CheckingAccount, Operation.WITHDRAWAL, random.nextInt(30));
        }
        bankSystem.printTransactions("name_1", BankType.CheckingAccount);
        System.out.println(bankSystem.getAccountNum());
    }
}
