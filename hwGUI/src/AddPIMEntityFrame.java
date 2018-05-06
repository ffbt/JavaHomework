import javax.swing.*;
import java.util.Calendar;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class AddPIMEntityFrame extends JFrame
{
    private static final String[] pimEntityStrings = {"todos", "notes", "contacts", "appointments"};
    private RemotePIMCollection remotePIMCollection;
    private User user;

    public AddPIMEntityFrame(Calendar calendar, RemotePIMCollection remotePIMCollection, User user)
    {
        if (calendar == null)
            calendar = Calendar.getInstance();

        final Calendar calendarFinal = calendar;

        this.remotePIMCollection = remotePIMCollection;
        this.user = user;

        JComboBox comboBox = new JComboBox(pimEntityStrings);
        comboBox.addActionListener(e ->
        {
            PIMEntity pimEntity = null;
            String entity = ((JComboBox) e.getSource()).getSelectedItem().toString();
            switch (entity)
            {
                case "todos":
                    pimEntity = new PIMTodo();
                    ((PIMTodo) pimEntity).setDate(calendarFinal.getTime());
                    break;
                case "notes":
                    pimEntity = new PIMNote();
                    break;
                case "contacts":
                    pimEntity = new PIMContact();
                    break;
                case "appointments":
                    pimEntity = new PIMAppointment();
                    ((PIMAppointment) pimEntity).setDate(calendarFinal.getTime());
                    break;
                default:
                    break;
            }
            pimEntity.setOwner(user == null ? null : user.getUsername());
            remotePIMCollection.add(pimEntity);

            new EditPIMEntityFrame(entity, pimEntity);
        });

        this.add(comboBox);
        this.setSize(100, 100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
