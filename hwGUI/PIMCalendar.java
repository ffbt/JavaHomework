import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class PIMCalendar extends JFrame
{
    private final static String[] DAYS = {"Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"};
    private final static int row = 6;
    private final static int col = 7;
    private Calendar calendar;
    private RemotePIMCollection remotePIMCollection = new RemotePIMCollectionWithFile();

    private JPanel showPanel = new JPanel(new GridLayout(row, col));

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

    private void initFrame()
    {
        this.setSize(800, 600);
        this.setLocation(200, 50);
    }

    private void createFrame()
    {
        Calendar calendarTmp = Calendar.getInstance();
        calendarTmp.setTime(calendar.getTime());

        this.setTitle("PIMCalender " + calendarTmp.get(Calendar.YEAR) + "." + (calendarTmp.get(Calendar.MONTH) + 1));
        int month = calendarTmp.get(Calendar.MONTH);
        calendarTmp.add(Calendar.DATE, -(calendarTmp.get(Calendar.DAY_OF_WEEK) - 1));
        for (int i = 0; i < row * col; i++)
        {
            StringBuilder stringBuilder = new StringBuilder();
            PIMCollection pimCollection = remotePIMCollection.getItemsForDate(calendarTmp.getTime());
            for (Object object : pimCollection)
                stringBuilder.append(object.toString());
            showPanel.add(new DateCell(calendarTmp, stringBuilder.toString(), calendarTmp.get(Calendar.MONTH) == month));
            calendarTmp.add(Calendar.DATE, 1);
        }

        JPanel title = new JPanel(new GridLayout(1, col));
        JTextField[] titleTexts = new JTextField[col];
        for (int i = 0; i < titleTexts.length; i++)
        {
            titleTexts[i] = new JTextField(DAYS[i].substring(0, 2));
            titleTexts[i].setFont(new Font("宋体", Font.BOLD, 20));
            titleTexts[i].setEditable(false);
            titleTexts[i].setHorizontalAlignment(SwingConstants.CENTER);
            title.add(titleTexts[i]);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
        JButton button1 = new JButton("prev");
        JButton button2 = new JButton("next");
        button1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                calendar.add(Calendar.MONTH, -1);
                showPanel.removeAll();
                createFrame();
            }
        });
        button2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                calendar.add(Calendar.MONTH, 1);
                showPanel.removeAll();
                createFrame();
            }
        });
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        this.add(title, BorderLayout.NORTH);
        this.add(showPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public PIMCalendar(String[] date)
    {
        super();
        setCalendar(date);
        initFrame();
        createFrame();
    }

    public PIMCalendar()
    {
        this(null);
    }

    public static void main(String[] args)
    {
        new PIMCalendar(args);
    }
}

class DateCell extends JPanel
{
    public DateCell(Calendar calendar, String text, boolean currentMonth)
    {
        super(new BorderLayout());
        JTextField title = new JTextField((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE));
        title.setFont(new Font("宋体", Font.BOLD, 20));
        title.setEditable(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBackground(Color.LIGHT_GRAY);
        title.setBorder(new LineBorder(Color.GRAY));

        JTextArea info = new JTextArea(text);
        info.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JOptionPane.showMessageDialog(null, "233");
            }
        });
        info.setFont(new Font("宋体", Font.BOLD, 10));
        info.setEditable(false);
        info.setBorder(new LineBorder(Color.GRAY));
        info.setLineWrap(true);
        if (!currentMonth)
            info.setBackground(Color.LIGHT_GRAY);
        this.add(title, BorderLayout.NORTH);
        this.add(new JScrollPane(info), BorderLayout.CENTER);
    }
}
