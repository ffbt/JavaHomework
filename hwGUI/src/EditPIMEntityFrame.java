import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 编辑PIM项的窗口
 *
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class EditPIMEntityFrame extends JFrame
{
    private InfoFrame infoFrame;
    private String entity;
    private PIMEntity pimEntity;
    private CalendarPanel calendarPanel;

    /**
     * @param entity        PIM项类型
     * @param pimEntity     待编辑的PIM项
     * @param calendarPanel 主界面的日历面板
     */
    public EditPIMEntityFrame(String entity, PIMEntity pimEntity, CalendarPanel calendarPanel)
    {
        super();

        this.entity = entity.substring(0, entity.length() - 1);
        this.pimEntity = pimEntity;
        this.calendarPanel = calendarPanel;

        this.add(new PIMEntityPanel());
        this.setSize(400, 300);
        this.setVisible(true);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                if (infoFrame != null)
                {
                    infoFrame.reset();
                    infoFrame.validate();
                }
                ((JFrame) e.getSource()).dispose();
            }
        });
    }

    /**
     * 若是从InfoFrame启动的，则调用该方法，以便更新
     *
     * @param infoFrame 启动该窗口的InfoFrame
     */
    public void setInfoFrame(InfoFrame infoFrame)
    {
        this.infoFrame = infoFrame;
    }

    /**
     * 显示PIM项的面板
     */
    private class PIMEntityPanel extends JPanel
    {
        /**
         * PIM项最多有七个字段
         */
        private JPanel entityShowPanel = new JPanel(new GridLayout(7, 2));

        /**
         * 将PIM项的字段添加到面板上
         */
        public PIMEntityPanel()
        {
            super(new BorderLayout());

            setCommonShowPanel();

            switch (entity)
            {
                case "todo":
                    PIMTodo pimTodo = (PIMTodo) pimEntity;
                    setTodoShowPanel(pimTodo);
                    break;
                case "note":
                    PIMNote pimNote = (PIMNote) pimEntity;
                    setNoteShowPanel(pimNote);
                    break;
                case "contact":
                    PIMContact pimContact = (PIMContact) pimEntity;
                    setContactShowPanel(pimContact);
                    break;
                case "appointment":
                    PIMAppointment pimAppointment = (PIMAppointment) pimEntity;
                    setAppointmentShowPanel(pimAppointment);
                    break;
                default:
                    break;
            }

            this.add(entityShowPanel, BorderLayout.CENTER);

            JPanel optionPanel = new JPanel();
            JButton saveButton = new JButton("save");
            saveButton.addMouseListener(new SaveMouseAdapter());
            optionPanel.add(saveButton);

            this.add(optionPanel, BorderLayout.SOUTH);
        }

        /**
         * 添加"save"按钮的监听事件
         * 将修改后的内容保存
         */
        private class SaveMouseAdapter extends MouseAdapter
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
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
                if (calendarPanel != null)
                {
                    calendarPanel.reset(0);
                    JRootPane rootPane = calendarPanel.getRootPane();
                    if (rootPane != null)
                        rootPane.getParent().validate();
                }
                ((JFrame) ((JButton) e.getSource()).getRootPane().getParent()).dispose();
            }

            /**
             * 通过索引获取PIMEntityPanel上的JTextField组件内容
             *
             * @param index 组件索引
             * @return 组件的内容
             */
            private String getText(int index)
            {
                return ((JTextField) entityShowPanel.getComponent(index)).getText();
            }
        }

        /**
         * 设置所有PIM项共有的字段
         */
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

        /**
         * 设置PIMTodo上的字段
         *
         * @param pimTodo 需显示的PIMTodo项
         */
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

        /**
         * 设置PIMNote上的字段
         *
         * @param pimNote 需显示的PIMNote项
         */
        private void setNoteShowPanel(PIMNote pimNote)
        {
            JLabel stringLabel = new JLabel("string");
            JTextField stringTextField = new InfoTextField(pimNote.getString());
            entityShowPanel.add(stringLabel);
            entityShowPanel.add(stringTextField);
        }

        /**
         * 设置PIMContact上的字段
         *
         * @param pimContact 需显示的PIMContact项
         */
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

        /**
         * 设置PIMAppointment上的字段
         *
         * @param pimAppointment 需显示的PIMAppointment项
         */
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

    /**
     * 内容显示格式
     */
    private class InfoTextField extends JTextField
    {
        /**
         * @param text 内容
         */
        public InfoTextField(String text)
        {
            super(text);
            this.setFont(new Font("微软雅黑", Font.BOLD, 15));
        }
    }
}
