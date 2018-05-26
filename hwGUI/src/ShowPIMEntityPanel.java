import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class ShowPIMEntityPanel extends JPanel
{
    private RemotePIMCollection remotePIMCollection;
    private List list = null;
    private User user;
    private String entity;
    private int size;
    private int index = 0;

    public void setUser(User user)
    {
        this.user = user;
        if (this.getRootPane() != null)
            this.reset();
    }

    public void setEntity(String entity)
    {
        this.entity = entity;
    }

    public ShowPIMEntityPanel(RemotePIMCollection remotePIMCollection)
    {
        super(new BorderLayout());
        this.remotePIMCollection = remotePIMCollection;
    }

    public void reset()
    {
        index = 0;
        this.removeAll();
        this.set();
    }

    public void set()
    {
        String username = (user == null) ? "" : user.getUsername();
        ((JFrame) this.getRootPane().getParent()).setTitle(entity + " " + username);
        switch (entity)
        {
            case "todos":
                if (user == null)
                    list = (ArrayList) remotePIMCollection.getTodos().getTodos();
                else
                    list = (ArrayList) remotePIMCollection.getTodos(user.getUsername()).getTodos(user.getUsername());
                break;
            case "contacts":
                if (user == null)
                    list = (ArrayList) remotePIMCollection.getContacts().getContacts();
                else
                    list = (ArrayList) remotePIMCollection.getContacts(user.getUsername()).getContacts(user.getUsername());
                break;
            case "notes":
                if (user == null)
                    list = (ArrayList) remotePIMCollection.getNotes().getNotes();
                else
                    list = (ArrayList) remotePIMCollection.getNotes(user.getUsername()).getNotes(user.getUsername());
                break;
            case "appointments":
                if (user == null)
                    list = (ArrayList) remotePIMCollection.getAppointments().getAppointments();
                else
                    list = (ArrayList) remotePIMCollection.getAppointments(user.getUsername()).getAppointments(user.getUsername());
                break;
            default:
                break;
        }
        size = list.size();

        JTextArea infoTextArea = new InfoTextArea("");
        if (size != 0)
            infoTextArea.setText(list.get(index).toString());
        infoTextArea.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new EditPIMEntityFrame(entity, list.get(index), null);
            }
        });

        JPanel optionPanel = new JPanel();
        optionPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
        JButton last = new JButton("last");
        last.setEnabled(false);
        JButton next = new JButton("next");
        if (size <= 1)
            next.setEnabled(false);
        last.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!((JButton) e.getSource()).isEnabled())
                    return;
                index--;
                infoTextArea.setText(list.get(index).toString());
                if (index == 0)
                    last.setEnabled(false);
                if (!next.isEnabled())
                    next.setEnabled(true);
            }
        });
        next.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!((JButton) e.getSource()).isEnabled())
                    return;
                index++;
                infoTextArea.setText(list.get(index).toString());
                if (index == size - 1)
                    next.setEnabled(false);
                if (!last.isEnabled())
                    last.setEnabled(true);
            }
        });

        optionPanel.add(last);
        optionPanel.add(next);

        this.add(infoTextArea, BorderLayout.CENTER);
        this.add(optionPanel, BorderLayout.SOUTH);
    }
}
