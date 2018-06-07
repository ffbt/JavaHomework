import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

/**
 * 主界面
 * 显示日历及其相关操作按钮
 * 标题显示当前操作状态
 *
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMCalendar extends JFrame
{
    private User user = null;
    private RemotePIMCollection remotePIMCollection = new RemotePIMCollectionWithFile();
    private CalendarPanel calendarPanel = new CalendarPanel(remotePIMCollection);
    private ShowPIMEntityPanel showPIMEntityPanel = new ShowPIMEntityPanel(remotePIMCollection);
    private PIMToolBar pimToolBar = new PIMToolBar(this);

    /**
     * 根据日期初始化主界面
     *
     * @param date 默认提供的日期
     */
    public PIMCalendar(String[] date)
    {
        super();

        this.setSize(800, 600);

        addMenuBar();

        this.add(pimToolBar, BorderLayout.NORTH);     // 0
        this.add(calendarPanel, BorderLayout.CENTER);       // 1

        calendarPanel.set(date);

//        pimToolBar.setEnabled();

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                ((RemotePIMCollectionWithFile) remotePIMCollection).write();
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    /**
     * 设置当前使用的用户
     *
     * @param user 当前使用的用户信息
     */
    public void setUser(User user)
    {
        this.user = user;
        this.calendarPanel.setUser(user);
        this.showPIMEntityPanel.setUser(user);
        this.validate();
    }

    /**
     * 返回当前用户
     *
     * @return 当前用户
     */
    public User getUser()
    {
        return user;
    }

    /**
     * 返回PIM项集合
     *
     * @return PIM项集合
     */
    public RemotePIMCollection getRemotePIMCollection()
    {
        return remotePIMCollection;
    }

    /**
     * 返回主界面日历面板
     *
     * @return 主界面日历面板
     */
    public CalendarPanel getCalendarPanel()
    {
        return calendarPanel;
    }

    /**
     * 返回主界面展示PIM项的面板
     *
     * @return 主界面PIM项面板
     */
    public ShowPIMEntityPanel getShowPIMEntityPanel()
    {
        return showPIMEntityPanel;
    }

    /**
     * 返回主界面工具栏
     *
     * @return 主界面工具栏
     */
    public PIMToolBar getPimToolBar()
    {
        return pimToolBar;
    }

    /**
     * 将菜单栏添加到界面上
     * view: 浏览日历和PIM项
     * new: 新建PIM项
     */
    private void addMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("view");
        viewMenu.add(new PIMMenuItem("calendar", this));
        viewMenu.add(new PIMMenuItem("todos", this));
        viewMenu.add(new PIMMenuItem("contacts", this));
        viewMenu.add(new PIMMenuItem("notes", this));
        viewMenu.add(new PIMMenuItem("appointments", this));

        JMenu newMenu = new JMenu("new");
        newMenu.add(new PIMMenuItem("todo", this));
        newMenu.add(new PIMMenuItem("contact", this));
        newMenu.add(new PIMMenuItem("note", this));
        newMenu.add(new PIMMenuItem("appointment", this));

        menuBar.add(viewMenu);
        menuBar.add(newMenu);
        this.setJMenuBar(menuBar);
    }
}

/**
 * 设置菜单项及其监听事件
 */
class PIMMenuItem extends JMenuItem
{
    private PIMCalendar pimCalendar;

    /**
     * 设置修改主界面的菜单项
     *
     * @param text        菜单项名字
     * @param pimCalendar 调用的主界面
     */
    public PIMMenuItem(String text, PIMCalendar pimCalendar)
    {
        super(text);

        this.pimCalendar = pimCalendar;

        switch (text)
        {
            case "calendar":
                this.addActionListener(new CalendarActionListener());
                break;
            case "todos":
            case "contacts":
            case "notes":
            case "appointments":
                this.addActionListener(new ShowPIMEntityActionListener());
                break;
            case "todo":
            case "contact":
            case "note":
            case "appointment":
                this.addActionListener(new AddPIMEntityActionListener());
        }
    }

    /**
     * 浏览日历选项监听事件
     */
    private class CalendarActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            pimCalendar.getPimToolBar().setSwitchMonthEnabled();
            if (pimCalendar.getContentPane().getComponent(1) != null)
                pimCalendar.getContentPane().remove(1);
            pimCalendar.add(pimCalendar.getCalendarPanel(), BorderLayout.CENTER);
            ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(0);
            pimCalendar.validate();
        }
    }

    /**
     * 浏览PIM项监听事件
     */
    private class ShowPIMEntityActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            pimCalendar.getPimToolBar().setSwitchMonthDisabled();
            if (pimCalendar.getContentPane().getComponent(1) != null)
                pimCalendar.getContentPane().remove(1);
            ShowPIMEntityPanel showPIMEntityPanel = pimCalendar.getShowPIMEntityPanel();
            showPIMEntityPanel.setEntity(e.getActionCommand());
            pimCalendar.add(showPIMEntityPanel);
            showPIMEntityPanel.reset(0);
            pimCalendar.validate();
        }
    }

    /**
     * 添加PIM项监听事件
     */
    private class AddPIMEntityActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            RemotePIMCollection remotePIMCollection = pimCalendar.getRemotePIMCollection();
            User user = pimCalendar.getUser();
            PIMEntity pimEntity = null;
            Calendar calendar = Calendar.getInstance();
            String entity = e.getActionCommand() + "s";
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
            new EditPIMEntityFrame(entity, pimEntity, pimCalendar.getCalendarPanel());
        }
    }
}

/**
 * 设置工具栏及其监听事件
 * login: 登录
 * last month: 浏览上一月的日历
 * next month: 浏览下一月的日历
 */
class PIMToolBar extends JToolBar
{
    private JButton lastMonth = new JButton("last month");
    private JButton nextMonth = new JButton("next month");

    /**
     * 启用月份切换按钮
     */
    public void setSwitchMonthEnabled()
    {
        lastMonth.setEnabled(true);
        nextMonth.setEnabled(true);
    }

    /**
     * 禁用月份切换按钮
     */
    public void setSwitchMonthDisabled()
    {
        lastMonth.setEnabled(false);
        nextMonth.setEnabled(false);
    }

    public PIMToolBar(PIMCalendar pimCalendar)
    {
        JButton loginButton = new JButton("login");
        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFrame loginFrame = new JFrame("login");
                JPanel loginPanel = new JPanel(new GridLayout(2, 2));
                JLabel usernameLabel = new JLabel("username");
                JLabel passwordLabel = new JLabel("password");
                JTextField usernameTextField = new JTextField();
                usernameTextField.setText("user1");
                JTextField passwordTextField = new JPasswordField();
                passwordTextField.setText("user1");

                loginPanel.add(usernameLabel);
                loginPanel.add(usernameTextField);
                loginPanel.add(passwordLabel);
                loginPanel.add(passwordTextField);

                JPanel optionPanel = new JPanel();
                optionPanel.setPreferredSize(new Dimension(loginFrame.getWidth(), 30));

                JButton login = new JButton("login");
                login.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        String username = usernameTextField.getText();
                        String password = passwordTextField.getText();
//                        System.out.println(password);
                        User user = User.verify(username, password);
                        pimCalendar.setUser(user);
                        ((JFrame) ((JButton) e.getSource()).getRootPane().getParent()).dispose();
                    }
                });

                optionPanel.add(login);

                loginFrame.add(loginPanel, BorderLayout.CENTER);
                loginFrame.add(optionPanel, BorderLayout.SOUTH);

                loginFrame.setSize(400, 120);
                loginFrame.setVisible(true);
                loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        this.add(loginButton);

        lastMonth.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (((JButton) e.getSource()).isEnabled())
                {
                    ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(-1);
                    pimCalendar.validate(); // 刷新frame
                }
            }
        });
        this.add(lastMonth);

        nextMonth.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (((JButton) e.getSource()).isEnabled())
                {
                    ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(1);
                    pimCalendar.validate();
                }
            }
        });
        this.add(nextMonth);
    }
}
