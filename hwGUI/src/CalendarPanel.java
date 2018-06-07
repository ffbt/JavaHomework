import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * 日历及其操作按钮面板
 * 本月项为白色背景
 * 非本月项为灰色背景
 *
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

    /**
     * 根据日期设置日历
     * 若日期为空，则日历设置为当前日期
     *
     * @param date 默认设置的日期，若为空，则默认为当前日期
     */
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

    /**
     * 根据月份偏移量修改日历
     *
     * @param monthDelta 月份偏移量
     */
    private void setCalendar(int monthDelta)
    {
        calendar.add(Calendar.MONTH, monthDelta);
    }

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
     * 修改当前日期，并将该日历面板添加到主界面上
     *
     * @param date 默认显示的日期
     */
    public void set(String[] date)
    {
        setCalendar(date);
        JPanel showPanel = ((JPanel) this.getComponent(1));
        addPIMEntity(showPanel);
    }

    /**
     * 根据月份偏移量重新显示该日历面板
     *
     * @param monthDelta 月份偏移量
     */
    public void reset(int monthDelta)
    {
        setCalendar(monthDelta);
        JPanel showPanel = ((JPanel) this.getComponent(1));
        showPanel.removeAll();
        addPIMEntity(showPanel);
    }

    /**
     * 将日历面板及其对应的具有时间属性的PIM项显示到主界面上
     *
     * @param showPanel 日历面板
     */
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
            showPanel.add(new DateCell(calendarTmp, stringBuilder.toString(), calendarTmp.get(Calendar.MONTH) == month, pimCollection));
            calendarTmp.add(Calendar.DATE, 1);
        }
    }

    /**
     * @param remotePIMCollection PIM项集合
     */
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

    /**
     * 日期单元格
     */
    private class DateCell extends JPanel
    {
        /**
         * 以上面为日期，下面为内容初始化日期单元格
         *
         * @param calendar      单元格的日期
         * @param text          单元格的内容
         * @param currentMonth  是否为当前月
         * @param pimCollection 属于该单元格的PIM项集合
         */
        public DateCell(Calendar calendar, String text, boolean currentMonth, PIMCollection pimCollection)
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
                    new InfoFrame(e.getXOnScreen(), e.getYOnScreen(), pimCollection, calendarTmp, remotePIMCollection, user, CalendarPanel.this);
                }
            });
            if (!currentMonth)
                infoTextArea.setBackground(Color.LIGHT_GRAY);

            this.add(titleTextField, BorderLayout.NORTH);
//        this.add(infoTextArea, BorderLayout.CENTER);
            this.add(new JScrollPane(infoTextArea), BorderLayout.CENTER);
        }
    }
}

/**
 * 标题显示格式
 */
class TitleTextField extends JTextField
{
    /**
     * @param text 标题名称
     */
    public TitleTextField(String text)
    {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.setEditable(false);
        this.setHorizontalAlignment(CENTER);
    }
}

/**
 * 信息域显示格式
 */
class InfoTextArea extends JTextArea
{
    /**
     * @param text 显示内容
     */
    public InfoTextArea(String text)
    {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, 15));
        this.setEditable(false);
        this.setBorder(new LineBorder(Color.GRAY));
        this.setLineWrap(true);
    }
}
