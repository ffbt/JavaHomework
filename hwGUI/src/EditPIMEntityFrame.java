import javax.swing.*;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class EditPIMEntityFrame extends JFrame
{
    public EditPIMEntityFrame(String entity, Object object)
    {
        super();
        JPanel entityPanel = new PIMEntityPanel(entity.substring(0, entity.length() - 1), object);
        this.add(entityPanel);
        this.setSize(400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
