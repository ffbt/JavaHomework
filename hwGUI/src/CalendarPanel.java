import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class CalendarPanel extends JPanel
{
    private final static String[] DAYS = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"};
    private final static int row = 6;
    private final static int col = 7;
    private Calendar calendar;
    private RemotePIMCollection remotePIMCollection;
    private User user = null;

    private void setCalendar(String[] date)
    {
        int month, year;
        calendar = Calendar.getInstance();
        try
        {
            month = Integer.parseInt(date[0]);
            year = Integer.parseInt(date[1]);
            if (month < 1 || month > 12 || year < 0)
                throw new Exception();
            month--;
        }
        catch (Exception e)
        {
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
    }

    private void setCalendar(int monthDelta)
    {
        calendar.add(Calendar.MONTH, monthDelta);
    }

    public void setUser(User user)
    {
        this.user = user;
        if (this.getRootPane() != null)
            this.reset(0);
    }

    public void set(String[] date)
    {
        setCalendar(date);
        JPanel showPanel = ((JPanel) this.getComponent(1));
        addPIMEntity(showPanel);
    }

    public void reset(int monthDelta)
    {
        setCalendar(monthDelta);
        JPanel showPanel = ((JPanel) this.getComponent(1));
        showPanel.removeAll();
        addPIMEntity(showPanel);
    }

    private void addPIMEntity(JPanel showPanel)
    {
        Calendar calendarTmp = Calendar.getInstance();
        calendarTmp.setTime(calendar.getTime());
        JFrame rootFrame = (JFrame) this.getRootPane().getParent();

        StringBuilder title = new StringBuilder("PIMCalender " + calendarTmp.get(Calendar.YEAR) + "." + (calendarTmp.get(Calendar.MONTH) + 1));
        if (user != null)
            title.append(" " + user.getUsername());
        rootFrame.setTitle(title.toString());

        int month = calendarTmp.get(Calendar.MONTH);
        calendarTmp.add(Calendar.DATE, -(calendarTmp.get(Calendar.DAY_OF_WEEK) - 1));
        for (int i = 0; i < row * col; i++)
        {
            StringBuilder stringBuilder = new StringBuilder();
            PIMCollection pimCollection;
            if (user == null)
                pimCollection = remotePIMCollection.getItemsForDate(calendarTmp.getTime());
            else
                pimCollection = remotePIMCollection.getItemsForDate(calendarTmp.getTime(), user.getUsername());
            for (Object object : pimCollection)
                stringBuilder.append(object.toString());
            showPanel.add(new DateCell(calendarTmp, stringBuilder.toString(), calendarTmp.get(Calendar.MONTH) == month, pimCollection, remotePIMCollection, user, this));
            calendarTmp.add(Calendar.DATE, 1);
        }
    }

    public CalendarPanel(RemotePIMCollection remotePIMCollection)
    {
        super(new BorderLayout());
        this.remotePIMCollection = remotePIMCollection;
        JPanel titlePanel = new JPanel(new GridLayout(1, col));
        JTextField[] titleTextFields = new JTextField[col];
        for (int i = 0; i < titleTextFields.length; i++)
        {
            titleTextFields[i] = new TitleTextField(DAYS[i].substring(0, 2));
            titlePanel.add(titleTextFields[i]);
        }

        JPanel showPanel = new JPanel(new GridLayout(row, col));

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(showPanel, BorderLayout.CENTER);
    }
}

class DateCell extends JPanel
{
    public DateCell(Calendar calendar, String text, boolean currentMonth, PIMCollection pimCollection, RemotePIMCollection remotePIMCollection, User user, CalendarPanel calendarPanel)
    {
        super(new BorderLayout());
        JTextField titleTextField = new TitleTextField((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        titleTextField.setBackground(Color.LIGHT_GRAY);
        titleTextField.setBorder(new LineBorder(Color.GRAY));

        JTextArea infoTextArea = new InfoTextArea(text);
        Calendar calendarTmp = Calendar.getInstance();
        calendarTmp.setTime(calendar.getTime());
        infoTextArea.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                new InfoFrame(e.getXOnScreen(), e.getYOnScreen(), pimCollection, calendarTmp, remotePIMCollection, user, calendarPanel);
            }
        });
        if (!currentMonth)
            infoTextArea.setBackground(Color.LIGHT_GRAY);

        this.add(titleTextField, BorderLayout.NORTH);
//        this.add(infoTextArea, BorderLayout.CENTER);
        this.add(new JScrollPane(infoTextArea), BorderLayout.CENTER);
    }
}

class TitleTextField extends JTextField
{
    public TitleTextField(String text)
    {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.setEditable(false);
        this.setHorizontalAlignment(CENTER);
    }
}

class InfoTextArea extends JTextArea
{
    public InfoTextArea(String text)
    {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, 15));
        this.setEditable(false);
        this.setBorder(new LineBorder(Color.GRAY));
        this.setLineWrap(true);
    }
}

class InfoFrame extends JFrame
{
    private static final String[] pimEntityStrings = {"todos", "notes", "contacts", "appointments"};
    private PIMCollection pimCollection;
    private JPanel showPanel = new JPanel();
    private CalendarPanel calendarPanel;

    private void set()
    {
        int textAreaNum = pimCollection.size();
        showPanel.setLayout(new GridLayout(textAreaNum, 1));

        for (Object o : pimCollection)
        {
            JTextArea infoTextArea = new InfoTextArea(o.toString());
            infoTextArea.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (o instanceof PIMTodo)
                        new EditPIMEntityFrame("todos", o, calendarPanel);
                    else
                        new EditPIMEntityFrame("appointments", o, calendarPanel);
                }
            });
            showPanel.add(infoTextArea);
        }
    }

    public void reset()
    {
        showPanel.removeAll();
        set();
    }

    public InfoFrame(int x, int y, PIMCollection pimCollection, Calendar calendar, RemotePIMCollection remotePIMCollection, User user, CalendarPanel calendarPanel)
    {
        super((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        this.pimCollection = pimCollection;
        this.calendarPanel = calendarPanel;

        set();

        JPanel optionPanel = new JPanel(new GridLayout(1, 2));
        optionPanel.setPreferredSize(new Dimension(this.getWidth(), 30));

        JLabel addLabel = new JLabel("add");

        JComboBox comboBox = getJComboBox(calendar, remotePIMCollection, user, calendarPanel, pimCollection);

        optionPanel.add(addLabel);
        optionPanel.add(comboBox);

        this.add(optionPanel, BorderLayout.SOUTH);
        this.add(showPanel, BorderLayout.CENTER);

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

    private JComboBox getJComboBox(Calendar calendar, RemotePIMCollection remotePIMCollection, User user, CalendarPanel calendarPanel, PIMCollection pimCollection)
    {
        if (calendar == null)
            calendar = Calendar.getInstance();

        final Calendar calendarFinal = calendar;

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
            pimCollection.add(pimEntity);
            EditPIMEntityFrame editPIMEntityFrame = new EditPIMEntityFrame(entity, pimEntity, calendarPanel);
            editPIMEntityFrame.setInfoFrame((InfoFrame) this.getRootPane().getParent());
        });
        return comboBox;
    }
}
