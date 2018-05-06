import java.util.Date;

public interface RemotePIMCollection
{
    PIMCollection getNotes();
    PIMCollection getNotes(String owner);
    PIMCollection getTodos();
    PIMCollection getTodos(String owner);
    PIMCollection getAppointments();
    PIMCollection getAppointments(String owner);
    PIMCollection getContacts();
    PIMCollection getContacts(String owner);
    PIMCollection getItemsForDate(Date d);
    PIMCollection getItemsForDate(Date d, String owner);
    PIMCollection getAll();
    PIMCollection getAllByOwner(String owner);
    boolean add(PIMEntity pimEntity);
}
