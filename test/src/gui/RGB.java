package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RGB
{
    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame("RGB");
        jFrame.setLayout(new GridLayout(2, 1));
        jFrame.setSize(400, 300);
        ShowPanel showPanel = new ShowPanel();
        jFrame.add(showPanel);
        JSlider rSlider = new JSlider();
        rSlider.setMaximum(255);
        JSlider gSlider = new JSlider();
        gSlider.setMaximum(255);
        JSlider bSlider = new JSlider();
        bSlider.setMaximum(255);
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField rTextField = new ColorTextField(rSlider);
        JTextField gTextField = new ColorTextField(gSlider);
        JTextField bTextField = new ColorTextField(bSlider);
        panel.add(rSlider);
        panel.add(rTextField);
        panel.add(gSlider);
        panel.add(gTextField);
        panel.add(bSlider);
        panel.add(bTextField);
        jFrame.add(panel);
        rSlider.addChangeListener(l ->
        {
            rTextField.setText(String.valueOf(rSlider.getValue()));
            showPanel.setR(rSlider.getValue());
        });
        gSlider.addChangeListener(l ->
        {
            gTextField.setText(String.valueOf(gSlider.getValue()));
            showPanel.setG(gSlider.getValue());
        });
        bSlider.addChangeListener(l ->
        {
            bTextField.setText(String.valueOf(bSlider.getValue()));
            showPanel.setB(bSlider.getValue());
        });
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}

class ShowPanel extends JPanel
{
    private int r;
    private int g;
    private int b;

    public void setR(int r)
    {
        this.r = r;
        setColor();
    }

    public void setG(int g)
    {
        this.g = g;
        setColor();
    }

    public void setB(int b)
    {
        this.b = b;
        setColor();
    }

    private void setColor()
    {
        this.setBackground(new Color(r, g, b));
    }
}

class ColorTextField extends JTextField
{
    public ColorTextField(JSlider jSlider)
    {
        JTextField textField = this;
        this.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (textField.getText().equals(""))
                    jSlider.setValue(0);
                else if(Integer.parseInt(textField.getText()) > 255)
                    jSlider.setValue(255);
                else
                    jSlider.setValue(Integer.parseInt(textField.getText()));
            }
        });
    }
}
