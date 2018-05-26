import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMEntityPanel extends JPanel
{
    private String entity;
    private PIMEntity pimEntity;
    private CalendarPanel calendarPanel;
    private JPanel entityShowPanel = new JPanel(new GridLayout(7, 2));

    public String getEntity()
    {
        return entity;
    }

    public PIMEntity getPimEntity()
    {
        return pimEntity;
    }

    public JPanel getEntityShowPanel()
    {
        return entityShowPanel;
    }

    public CalendarPanel getCalendarPanel()
    {
        return calendarPanel;
    }

    public PIMEntityPanel(String entity, Object object, CalendarPanel calendarPanel)
    {
        super(new BorderLayout());
        this.entity = entity;
        this.pimEntity = (PIMEntity) object;
        this.calendarPanel = calendarPanel;

        setCommonShowPanel();

        switch (entity)
        {
            case "todo":
                PIMTodo pimTodo = (PIMTodo) object;
                setTodoShowPanel(pimTodo);
                break;
            case "note":
                PIMNote pimNote = (PIMNote) object;
                setNoteShowPanel(pimNote);
                break;
            case "contact":
                PIMContact pimContact = (PIMContact) object;
                setContactShowPanel(pimContact);
                break;
            case "appointment":
                PIMAppointment pimAppointment = (PIMAppointment) object;
                setAppointmentShowPanel(pimAppointment);
                break;
            default:
                break;
        }

        this.add(entityShowPanel, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        JButton saveButton = new JButton("save");
        saveButton.addMouseListener(new SaveMouseAdapter(this));
        optionPanel.add(saveButton);

        this.add(optionPanel, BorderLayout.SOUTH);
    }

    private void setCommonShowPanel()
    {
        JLabel ownerLabel = new JLabel("owner");
        JTextField ownerTextField = new InfoTextField(pimEntity.getOwner());
        ownerTextField.setEditable(false);
        JLabel typeLabel = new JLabel("type");
        JTextField typeTextField = new InfoTextField(entity);
        typeTextField.setEditable(false);
        JLabel visibilityLabel = new JLabel("visibility");
        JTextField visibilityTextField = new InfoTextField(pimEntity.getVisibility());
        JLabel priorityLabel = new JLabel("priority");
        JTextField priorityTextField = new InfoTextField(pimEntity.getPriority());
        entityShowPanel.add(ownerLabel);
        entityShowPanel.add(ownerTextField);
        entityShowPanel.add(typeLabel);
        entityShowPanel.add(typeTextField);
        entityShowPanel.add(visibilityLabel);
        entityShowPanel.add(visibilityTextField);
        entityShowPanel.add(priorityLabel);
        entityShowPanel.add(priorityTextField);
    }

    private void setTodoShowPanel(PIMTodo pimTodo)
    {
        JLabel dateLabel = new JLabel("date");
        JTextField dateTextField = new InfoTextField(pimTodo.getDate());
        JLabel stringLabel = new JLabel("string");
        JTextField stringTextField = new InfoTextField(pimTodo.getString());
        entityShowPanel.add(dateLabel);
        entityShowPanel.add(dateTextField);
        entityShowPanel.add(stringLabel);
        entityShowPanel.add(stringTextField);
    }

    private void setNoteShowPanel(PIMNote pimNote)
    {
        JLabel stringLabel = new JLabel("string");
        JTextField stringTextField = new InfoTextField(pimNote.getString());
        entityShowPanel.add(stringLabel);
        entityShowPanel.add(stringTextField);
    }

    private void setContactShowPanel(PIMContact pimContact)
    {
        JLabel firstNameLabel = new JLabel("firstName");
        JTextField firstNameTextField = new InfoTextField(pimContact.getFirstName());
        JLabel lastNameLabel = new JLabel("lastName");
        JTextField lastNameTextField = new InfoTextField(pimContact.getLastName());
        JLabel emailAddressLabel = new JLabel("emailAddress");
        JTextField emailAddressTextField = new InfoTextField(pimContact.getEmailAddress());
        entityShowPanel.add(firstNameLabel);
        entityShowPanel.add(firstNameTextField);
        entityShowPanel.add(lastNameLabel);
        entityShowPanel.add(lastNameTextField);
        entityShowPanel.add(emailAddressLabel);
        entityShowPanel.add(emailAddressTextField);
    }

    private void setAppointmentShowPanel(PIMAppointment pimAppointment)
    {
        JLabel dateLabel = new JLabel("date");
        JTextField dateTextField = new InfoTextField(pimAppointment.getDate());
        JLabel descriptionLabel = new JLabel("description");
        JTextField descriptionTextField = new InfoTextField(pimAppointment.getDescription());
        entityShowPanel.add(dateLabel);
        entityShowPanel.add(dateTextField);
        entityShowPanel.add(descriptionLabel);
        entityShowPanel.add(descriptionTextField);
    }
}

class InfoTextField extends JTextField
{
    public InfoTextField(String text)
    {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, 15));
    }
}

class SaveMouseAdapter extends MouseAdapter
{
    private PIMEntityPanel pimEntityPanel;

    public SaveMouseAdapter(PIMEntityPanel pimEntityPanel)
    {
        this.pimEntityPanel = pimEntityPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        String entity = pimEntityPanel.getEntity();
        PIMEntity pimEntity = pimEntityPanel.getPimEntity();
        switch (entity)
        {
            case "todo":
                PIMTodo pimTodo = (PIMTodo) pimEntity;
                pimTodo.setFlag(getText(5).equals("public"));
                pimTodo.setPriority(getText(7));
                pimTodo.setDate(getText(9));
                pimTodo.setString(getText(11));
                break;
            case "note":
                PIMNote pimNote = (PIMNote) pimEntity;
                pimNote.setFlag(getText(5).equals("public"));
                pimNote.setPriority(getText(7));
                pimNote.setString(getText(9));
                break;
            case "contact":
                PIMContact pimContact = (PIMContact) pimEntity;
                pimContact.setFlag(getText(5).equals("public"));
                pimContact.setPriority(getText(7));
                pimContact.setFirstName(getText(9));
                pimContact.setLastName(getText(11));
                pimContact.setEmailAddress(getText(13));
                break;
            case "appointment":
                PIMAppointment pimAppointment = (PIMAppointment) pimEntity;
                pimAppointment.setFlag(getText(5).equals("public"));
                pimAppointment.setPriority(getText(7));
                pimAppointment.setDate(getText(9));
                pimAppointment.setDescription(getText(11));
                break;
            default:
                break;
        }
        CalendarPanel calendarPanel = pimEntityPanel.getCalendarPanel();
        if (calendarPanel != null)
        {
            calendarPanel.reset(0);
            JRootPane rootPane = pimEntityPanel.getCalendarPanel().getRootPane();
            if (rootPane != null)
                rootPane.getParent().validate();
        }
        ((JFrame) ((JButton) e.getSource()).getRootPane().getParent()).dispose();
    }

    private String getText(int index)
    {
        return ((JTextField) pimEntityPanel.getEntityShowPanel().getComponent(index)).getText();
    }
}
