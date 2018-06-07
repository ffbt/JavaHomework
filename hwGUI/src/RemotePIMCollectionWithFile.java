import java.io.*;
import java.util.*;

/**
 * 通过读写文件实现RemotePIMCollection接口
 */
public class RemotePIMCollectionWithFile implements RemotePIMCollection
{
    private PIMCollection pimCollection = new PIMCollection();

    public RemotePIMCollectionWithFile()
    {
        read();
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

    /**
     * 从文件中反序列化类
     */
    private void read()
    {
        File file = new File("list.txt");
        createFile(file);
        try (ObjectInputStream osi = new ObjectInputStream(new FileInputStream(file)))
        {
            PIMEntity pimEntity;
            while ((pimEntity = (PIMEntity) osi.readObject()) != null)
                pimCollection.add(pimEntity);
        }
        catch (IOException | ClassNotFoundException e)
        {
        }
    }

    /**
     * 将对象序列化到文件
     */
    public void write()
    {
        File file = new File("list.txt");
        createFile(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            for (Object object : pimCollection)
                oos.writeObject(object);
        }
        catch (IOException e)
        {
        }
    }

    @Override
    public PIMCollection getNotes()
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getNotes();
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getNotes(String owner)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getNotes(owner);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getTodos()
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getTodos();
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getTodos(String owner)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getTodos(owner);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getAppointments()
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getAppointments();
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getAppointments(String owner)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getAppointments(owner);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getContacts()
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getContacts();
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getContacts(String owner)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getContacts(owner);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getItemsForDate(Date d)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getItemsForDate(d);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getItemsForDate(Date d, String owner)
    {
        PIMCollection tmp = new PIMCollection();
        Collection collection = pimCollection.getItemsForDate(d, owner);
        for (Object object : collection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getAll()
    {
        PIMCollection tmp = new PIMCollection();
        for (Object object : pimCollection)
            tmp.add(object);
        return tmp;
    }

    @Override
    public PIMCollection getAllByOwner(String owner)
    {
        PIMCollection tmp = new PIMCollection();
        for (Object object : pimCollection)
        {
            if (((PIMEntity) object).getOwner().equals(owner))
                tmp.add(object);
        }
        return tmp;
    }

    @Override
    public boolean add(PIMEntity pimEntity)
    {
        pimCollection.add(pimEntity);
        return true;
    }

    @Override
    public void remove(Object object)
    {
        pimCollection.remove(object);
    }
}
