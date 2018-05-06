import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMCollection implements Collection
{
    private ArrayList<PIMEntity> pimEntities = new ArrayList<>();

    @Override
    public int size()
    {
        return pimEntities.size();
    }

    @Override
    public boolean isEmpty()
    {
        return pimEntities.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return pimEntities.contains(o);
    }

    @Override
    public Iterator iterator()
    {
        return pimEntities.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return pimEntities.toArray();
    }

    @Override
    public boolean add(Object o)
    {
        return pimEntities.add((PIMEntity) o);
    }

    @Override
    public boolean remove(Object o)
    {
        return pimEntities.remove(o);
    }

    @Override
    public boolean addAll(Collection c)
    {
        return pimEntities.addAll(c);
    }

    @Override
    public void clear()
    {
        pimEntities.clear();
    }

    @Override
    public boolean retainAll(Collection c)
    {
        return pimEntities.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c)
    {
        return pimEntities.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c)
    {
        return pimEntities.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a)
    {
        return pimEntities.toArray();
    }

    public Collection getNotes()
    {
        ArrayList list = new ArrayList();
        for (PIMEntity pimEntity : pimEntities)
        {
            if (pimEntity instanceof PIMNote)
                list.add(pimEntity);
        }
        return list;
    }

    public Collection getTodos()
    {
        ArrayList list = new ArrayList();
        for (PIMEntity pimEntity : pimEntities)
        {
            if (pimEntity instanceof PIMTodo)
                list.add(pimEntity);
        }
        return list;
    }

    public Collection getAppointments()
    {
        ArrayList list = new ArrayList();
        for (PIMEntity pimEntity : pimEntities)
        {
            if (pimEntity instanceof PIMAppointment)
                list.add(pimEntity);
        }
        return list;
    }

    public Collection getContact()
    {
        ArrayList list = new ArrayList();
        for (PIMEntity pimEntity : pimEntities)
        {
            if (pimEntity instanceof PIMContact)
                list.add(pimEntity);
        }
        return list;
    }

    public Collection getItemsForDate(Date d)
    {
        ArrayList list = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(d);
        for (PIMEntity pimEntity : pimEntities)
        {
            if (pimEntity instanceof Dateable && ((Dateable) pimEntity).getDate().equals(date))
                list.add(pimEntity);
        }
        return list;
    }

    public static void main(String[] args)
    {
        Collection collection = new PIMCollection();
        for (int i = 0; i < 10; i++)
        {
            PIMContact pimContact = new PIMContact(String.valueOf(i * 4));
            PIMAppointment pimAppointment = new PIMAppointment(String.valueOf(i * 4 + 1));
            PIMTodo pimTodo = new PIMTodo(String.valueOf(i * 4 + 2));
            PIMNote pimNote = new PIMNote(String.valueOf(i * 4 + 3));
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date d1 = new Date();
            String date1 = sdf.format(d1);
            Date d2 = new Date(d1.getTime() + 24 * 60 * 60 * 1000);
            String date2 = sdf.format(d2);
            if (i % 2 == 0)
            {
                pimAppointment.setDate(date1);
                pimTodo.setDate(date1);
            }
            else
            {
                pimAppointment.setDate(date2);
                pimTodo.setDate(date2);
            }
            collection.add(pimContact);
            collection.add(pimAppointment);
            collection.add(pimTodo);
            collection.add(pimNote);
        }

        Collection pimContactCollection = ((PIMCollection)collection).getContact();
        Collection pimAppointmentCollection = ((PIMCollection)collection).getAppointments();
        Collection pimTodoCollection = ((PIMCollection)collection).getTodos();
        Collection pimNoteCollection = ((PIMCollection)collection).getNotes();
        Collection dateCollection = ((PIMCollection)collection).getItemsForDate(new Date());

        for (Object object : pimContactCollection)
            System.out.println(((PIMEntity)object).getPriority());
        System.out.println("===============================");
        for (Object object : pimAppointmentCollection)
            System.out.println(((PIMEntity)object).getPriority());
        System.out.println("===============================");
        for (Object object : pimTodoCollection)
            System.out.println(((PIMEntity)object).getPriority());
        System.out.println("===============================");
        for (Object object : pimNoteCollection)
            System.out.println(((PIMEntity)object).getPriority());
        System.out.println("===============================");
        for (Object object : dateCollection)
            System.out.println(((PIMEntity)object).getPriority());
        System.out.println("===============================");
    }
}
