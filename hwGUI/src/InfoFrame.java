import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * 显示日期单元格内所有PIM项
 *
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class InfoFrame extends JFrame
{
    private static final String[] pimEntityStrings = {"todos", "notes", "contacts", "appointments"};
    private PIMCollection pimCollection;
    private JPanel showPanel = new JPanel();
    private CalendarPanel calendarPanel;
    private RemotePIMCollection remotePIMCollection;
    private JScrollPane scrollPane = new JScrollPane(showPanel);
    private Calendar calendar;
    private User user;

    /**
     * 显示PIM集合中的所有PIM项
     */
    private void set()
    {
        int textAreaNum = pimCollection.size();
        showPanel.setLayout(new GridLayout(textAreaNum, 1));

        for (Object o : pimCollection)
        {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("delete");
            menuItem.addActionListener(e ->
            {
                pimCollection.remove(o);
                remotePIMCollection.remove(o);
                reset();
                InfoFrame.this.validate();
                calendarPanel.reset(0);
                calendarPanel.getRootPane().getParent().validate();
            });
            popupMenu.add(menuItem);

            JTextArea infoTextArea = new InfoTextArea(o.toString());
            infoTextArea.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        EditPIMEntityFrame editPIMEntityFrame;
                        if (o instanceof PIMTodo)
                            editPIMEntityFrame = new EditPIMEntityFrame("todos", (PIMEntity) o, calendarPanel);
                        else
                            editPIMEntityFrame = new EditPIMEntityFrame("appointments", (PIMEntity) o, calendarPanel);
                        editPIMEntityFrame.setInfoFrame((InfoFrame) ((JTextArea) e.getSource()).getRootPane().getParent());
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3)
                    {
//                        popupMenu.setLocation(e.getX(), e.getY());
//                        popupMenu.setVisible(true);
                        popupMenu.show((JTextArea) e.getSource(), e.getX(), e.getY());
                    }
                }
            });
            showPanel.add(infoTextArea);
        }
    }

    /**
     * 重新显示PIM集合中的所有PIM项
     */
    public void reset()
    {
        showPanel.removeAll();
        set();
    }

    /**
     * @param x                   窗口的横坐标
     * @param y                   窗口的纵坐标
     * @param pimCollection       需要显示的PIM项集合
     * @param calendar            对应的单元格日期
     * @param remotePIMCollection 所有PIM项集合
     * @param user                当前用户
     * @param calendarPanel       启动该窗口的面板
     */
    public InfoFrame(int x, int y, PIMCollection pimCollection, Calendar calendar, RemotePIMCollection remotePIMCollection, User user, CalendarPanel calendarPanel)
    {
        super((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        this.pimCollection = pimCollection;
        this.calendarPanel = calendarPanel;
        this.remotePIMCollection = remotePIMCollection;
        this.user = user;
        this.calendar = calendar;

        set();

        JPanel optionPanel = new JPanel(new GridLayout(1, 2));
        optionPanel.setPreferredSize(new Dimension(this.getWidth(), 30));

        JLabel addLabel = new JLabel("add");

        JComboBox comboBox = getJComboBox();

        optionPanel.add(addLabel);
        optionPanel.add(comboBox);

        this.add(optionPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(400, 300);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 获取屏幕大小
        if (x + this.getWidth() > screenSize.width)
            x = screenSize.width - this.getWidth();
        if (y + this.getHeight() > screenSize.height)
            y = screenSize.height - this.getHeight();

        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);    // 只关闭该frame
    }

    /**
     * 生成组合框，并添加监听事件
     *
     * @return 组合框
     */
    private JComboBox getJComboBox()
    {
        if (calendar == null)
            calendar = Calendar.getInstance();

        JComboBox comboBox = new JComboBox(pimEntityStrings);
        comboBox.addActionListener(e ->
        {
            PIMEntity pimEntity = null;
            String entity = ((JComboBox) e.getSource()).getSelectedItem().toString();
            switch (entity)
            {
                case "todos":
                    pimEntity = new PIMTodo();
                    ((PIMTodo) pimEntity).setDate(calendar.getTime());
                    break;
                case "notes":
                    pimEntity = new PIMNote();
                    break;
                case "contacts":
                    pimEntity = new PIMContact();
                    break;
                case "appointments":
                    pimEntity = new PIMAppointment();
                    ((PIMAppointment) pimEntity).setDate(calendar.getTime());
                    break;
                default:
                    break;
            }
            pimEntity.setOwner(user == null ? null : user.getUsername());
            pimEntity.setPublic(user == null);
            remotePIMCollection.add(pimEntity);
            pimCollection.add(pimEntity);
            EditPIMEntityFrame editPIMEntityFrame = new EditPIMEntityFrame(entity, pimEntity, calendarPanel);
            editPIMEntityFrame.setInfoFrame((InfoFrame) this.getRootPane().getParent());
        });
        return comboBox;
    }
}
