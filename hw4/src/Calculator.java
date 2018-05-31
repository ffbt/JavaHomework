import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class Calculator extends JFrame
{
    private final String[] buttonTexts = {"(", ")", "E", "CLR", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0", ".", "=", "/"};
    private boolean ok = false;

    public Calculator()
    {
        super("Calculator");
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(panel);
        JPanel showPanel = new JPanel(new GridLayout(1, 1));
        JTextField textField = new JTextField();
        textField.setFont(new Font("宋体", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        showPanel.add(textField);
        panel.add(showPanel, BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        for (String buttonText : buttonTexts)
        {
            JButton button = new JButton(buttonText);
            switch (buttonText)
            {
                case "CLR":
                    button.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            textField.setText("");
                        }
                    });
                    break;
                case "=":
                    button.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            textField.setText(Solver.conversion(textField.getText()));
//                            ok = true;
                        }
                    });
                    break;
                default:
                    button.addMouseListener(new ButtonMouseAdapter(textField));
            }
            buttonsPanel.add(button);
        }
        panel.add(buttonsPanel, BorderLayout.CENTER);
        this.setSize(300, 300);
        this.setLocation(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Calculator();
    }

    class ButtonMouseAdapter extends MouseAdapter
    {
        private JTextField textField;

        public ButtonMouseAdapter(JTextField textField)
        {
            this.textField = textField;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (ok)
            {
                ok = false;
                textField.setText("");
            }
            textField.setText(textField.getText() + ((JButton) e.getSource()).getText());
        }
    }
}
