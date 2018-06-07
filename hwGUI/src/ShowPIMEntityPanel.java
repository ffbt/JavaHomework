import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示PIM项的面板
 *
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

    /**
     * 设置当前使用的用户
     *
     * @param user 当前使用的用户信息
     */
    public void setUser(User user)
    {
        this.user = user;
        if (this.getRootPane() != null)
            this.reset(0);
    }

    /**
     * 设置当前显示的PIM项类型
     *
     * @param entity PIM项类型
     */
    public void setEntity(String entity)
    {
        this.entity = entity;
    }

    /**
     * @param remotePIMCollection PIM项集合
     */
    public ShowPIMEntityPanel(RemotePIMCollection remotePIMCollection)
    {
        super(new BorderLayout());
        this.remotePIMCollection = remotePIMCollection;
    }

    /**
     * 设置当前PIM项索引，重新显示PIM项
     *
     * @param index PIM项的索引
     */
    public void reset(int index)
    {
        this.index = index;
        this.removeAll();
        this.set();
    }

    /**
     * 根据当前PIM项类型和当前索引显示PIM项内容
     */
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

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("delete");
        menuItem.addActionListener(e ->
        {
            remotePIMCollection.remove(list.get(index));
            reset(index);
            this.getRootPane().getParent().validate();
        });
        popupMenu.add(menuItem);
        infoTextArea.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    new EditPIMEntityFrame(entity, (PIMEntity) list.get(index), null);
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                {
//                        popupMenu.setLocation(e.getX(), e.getY());
//                        popupMenu.setVisible(true);
                    popupMenu.show((JTextArea) e.getSource(), e.getX(), e.getY());
                }
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
