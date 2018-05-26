import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class EditPIMEntityFrame extends JFrame
{
    private InfoFrame infoFrame;

    public EditPIMEntityFrame(String entity, Object object, CalendarPanel calendarPanel)
    {
        super();
        JPanel entityPanel = new PIMEntityPanel(entity.substring(0, entity.length() - 1), object, calendarPanel);
        this.add(entityPanel);
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

    public void setInfoFrame(InfoFrame infoFrame)
    {
        this.infoFrame = infoFrame;
    }
}
