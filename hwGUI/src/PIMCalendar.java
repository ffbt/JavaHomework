import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class PIMCalendar extends JFrame
{
    private String[] date;
    private User user = null;
    private RemotePIMCollection remotePIMCollection = new RemotePIMCollectionWithFile();
    private CalendarPanel calendarPanel = new CalendarPanel(remotePIMCollection);
    private ShowPIMEntityPanel showPIMEntityPanel = new ShowPIMEntityPanel(remotePIMCollection);

    public PIMCalendar(String[] date)
    {
        super();
        this.date = date;

        this.setSize(800, 600);

        addMenuBar();

        this.add(new PIMToolBar(this), BorderLayout.NORTH);     // 0
        this.add(calendarPanel, BorderLayout.CENTER);       // 1

        calendarPanel.set(date);

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

    public void setUser(User user)
    {
        this.user = user;
        this.calendarPanel.setUser(user);
        this.showPIMEntityPanel.setUser(user);
        this.validate();
    }

    public User getUser()
    {
        return user;
    }

    public RemotePIMCollection getRemotePIMCollection()
    {
        return remotePIMCollection;
    }

    public CalendarPanel getCalendarPanel()
    {
        return calendarPanel;
    }

    public ShowPIMEntityPanel getShowPIMEntityPanel()
    {
        return showPIMEntityPanel;
    }

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

    public static void main(String[] args)
    {
        new PIMCalendar(args);
    }
}

class CalendarActionListener implements ActionListener
{
    private PIMCalendar pimCalendar;

    public CalendarActionListener(PIMCalendar pimCalendar)
    {
        this.pimCalendar = pimCalendar;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (pimCalendar.getContentPane().getComponent(1) != null)
            pimCalendar.getContentPane().remove(1);
        pimCalendar.add(pimCalendar.getCalendarPanel(), BorderLayout.CENTER);
        ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(0);
        pimCalendar.validate();
    }
}

class ShowPIMEntityActionListener implements ActionListener
{
    private PIMCalendar pimCalendar;

    public ShowPIMEntityActionListener(PIMCalendar pimCalendar)
    {
        this.pimCalendar = pimCalendar;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (pimCalendar.getContentPane().getComponent(1) != null)
            pimCalendar.getContentPane().remove(1);
        ShowPIMEntityPanel showPIMEntityPanel = pimCalendar.getShowPIMEntityPanel();
        showPIMEntityPanel.setEntity(e.getActionCommand());
        pimCalendar.add(showPIMEntityPanel);
        showPIMEntityPanel.reset();
        pimCalendar.validate();
    }
}

class AddPIMEntityActionListener implements ActionListener
{
    private PIMCalendar pimCalendar;

    public AddPIMEntityActionListener(PIMCalendar pimCalendar)
    {
        this.pimCalendar = pimCalendar;
    }

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
        remotePIMCollection.add(pimEntity);
        new EditPIMEntityFrame(entity, pimEntity, pimCalendar.getCalendarPanel());
    }
}

class PIMMenuItem extends JMenuItem
{
    public PIMMenuItem(String text, PIMCalendar pimCalendar)
    {
        super(text);

        switch (text)
        {
            case "calendar":
                this.addActionListener(new CalendarActionListener(pimCalendar));
                break;
            case "todos":
            case "contacts":
            case "notes":
            case "appointments":
                this.addActionListener(new ShowPIMEntityActionListener(pimCalendar));
                break;
            case "todo":
            case "contact":
            case "note":
            case "appointment":
                this.addActionListener(new AddPIMEntityActionListener(pimCalendar));
        }
    }
}

class PIMToolBar extends JToolBar
{
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
                JTextField passwordTextField = new JTextField();

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

        JButton lastMonth = new JButton("last month");
        lastMonth.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(-1);
                pimCalendar.validate(); // 刷新frame
            }
        });
        this.add(lastMonth);

        JButton nextMonth = new JButton("next month");
        nextMonth.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ((CalendarPanel) pimCalendar.getContentPane().getComponent(1)).reset(1);
                pimCalendar.validate();
            }
        });
        this.add(nextMonth);
    }
}
