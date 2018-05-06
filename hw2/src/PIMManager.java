import java.io.*;
import java.util.*;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMManager
{
    private List<PIMEntity> oldList = new ArrayList<>();
    private List<PIMEntity> newList = new ArrayList<>();
    private Set<String> set = new HashSet<>();
    private Scanner scanner = new Scanner(System.in);
    private boolean loaded = false;

    public static void main(String[] args)
    {
        PIMManager pimManager = new PIMManager();
        pimManager.work();
    }

    private void createFile(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private List<PIMEntity> read()
    {
        List<PIMEntity> tmpList = new ArrayList<>();
        File file = new File("list.txt");
        createFile(file);
        try (ObjectInputStream osi = new ObjectInputStream(new FileInputStream(file)))
        {
            PIMEntity pimEntity;
            while ((pimEntity = (PIMEntity) osi.readObject()) != null)
                add(tmpList, pimEntity);
        }
        catch (IOException | ClassNotFoundException e)
        {
        }
        return tmpList;
    }

    private void load()
    {
        loaded = true;
        quit();
        set.clear();
        List<PIMEntity> tmpList = read();
        for (PIMEntity pimEntity : oldList)
            add(tmpList, pimEntity);
        oldList = tmpList;
    }

    private void write()
    {
        if (!loaded)
            load();
        File file = new File("list.txt");
        createFile(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            for (PIMEntity pimEntity : oldList)
                oos.writeObject(pimEntity);
        }
        catch (IOException e)
        {
        }
    }

    private void add2NewList(PIMEntity pimEntity)
    {
        if (set.add(pimEntity.toString()))
            newList.add(pimEntity);
    }

    private void add(List<PIMEntity> list, PIMEntity pimEntity)
    {
        if (set.add(pimEntity.toString()))
            list.add(pimEntity);
    }

    public void work()
    {
        System.out.println("Welcome to PIM.");
        while (true)
        {
            System.out.println("---Enter a command (suported commands are List Create Save Load Quit(case insensitive))---");
            System.out.print(">>");
            String command = scanner.nextLine();
            switch (command.toLowerCase())
            {
                case "list":
                    list();
                    break;
                case "create":
                    create();
                    break;
                case "save":
                    save();
                    break;
                case "load":
                    load();
                    System.out.println("Items have been loaded.");
                    break;
                case "quit":
                    quit();
                    write();
                    return;
                default:
                    System.out.println("Error command!");
            }
        }
    }

    private void list()
    {
        int num = oldList.size() + newList.size();
        System.out.println("There are " + num + " item(s).");
        int i = 1;
        for (PIMEntity pimEntity : oldList)
        {
            System.out.println("Item " + i + ": " + pimEntity);
            i++;
        }
        for (PIMEntity pimEntity : newList)
        {
            System.out.println("Item " + i + ": " + pimEntity);
            i++;
        }
    }

    private void save()
    {
        oldList.addAll(newList);
        newList.clear();
        System.out.println("Items have been saved.");
    }

    private void create()
    {
        System.out.println("Enter an item type ( todo, note, contact or appointment(case insensitive) )");
        System.out.print(">>");
        String type = scanner.nextLine();
        switch (type.toLowerCase())
        {
            case "todo":
                todo();
                break;
            case "note":
                note();
                break;
            case "contact":
                contact();
                break;
            case "appointment":
                appointment();
                break;
            default:
                System.out.println("Error type!");
        }
    }

    private void todo()
    {
        PIMTodo pimTodo = new PIMTodo();
        System.out.println("Enter todo priority:");
        System.out.print(">>");
        pimTodo.setPriority(scanner.nextLine());
        System.out.println("Enter date for todo item(type: MM/dd/yyyy):");
        setDate(pimTodo);
        System.out.println("Enter todo text:");
        System.out.print(">>");
        pimTodo.setString(scanner.nextLine());
        add2NewList(pimTodo);
    }

    private void note()
    {
        PIMNote pimNote = new PIMNote();
        System.out.println("Enter note priority:");
        System.out.print(">>");
        pimNote.setPriority(scanner.nextLine());
        System.out.println("Enter note text:");
        System.out.print(">>");
        pimNote.setString(scanner.nextLine());
        add2NewList(pimNote);
    }

    private void contact()
    {
        PIMContact pimContact = new PIMContact();
        System.out.println("Enter contact priority:");
        System.out.print(">>");
        pimContact.setPriority(scanner.nextLine());
        System.out.println("Enter contact first name:");
        System.out.print(">>");
        pimContact.setFirstName(scanner.nextLine());
        System.out.println("Enter contact last name:");
        System.out.print(">>");
        pimContact.setLastName(scanner.nextLine());
        System.out.println("Enter contact email address:");
        System.out.print(">>");
        pimContact.setEmailAddress(scanner.nextLine());
        add2NewList(pimContact);
    }

    private void appointment()
    {
        PIMAppointment pimAppointment = new PIMAppointment();
        System.out.println("Enter appointment priority:");
        System.out.print(">>");
        pimAppointment.setPriority(scanner.nextLine());
        System.out.println("Enter date for appointment item(type: MM/dd/yyyy):");
        setDate(pimAppointment);
        System.out.println("Enter appointment description:");
        System.out.print(">>");
        pimAppointment.setDescription(scanner.nextLine());
        add2NewList(pimAppointment);
    }

    private void setDate(Dateable pimEntity)
    {
        boolean flag = false;
        while (!flag)
        {
            System.out.print(">>");
            flag = pimEntity.setDate(scanner.nextLine());
            if (!flag)
                System.out.println("Error type, enter it again");
        }
    }

    private void quit()
    {
        if (!newList.isEmpty())
        {
            System.out.println("Detection of the data has not been saved. Do you want to save it? (y/n)");
            System.out.print(">>");
            String info = scanner.nextLine();
            if ("y".equals(info.toLowerCase()))
                save();
            newList.clear();
        }
    }
}
