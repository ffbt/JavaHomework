package exam;

import javax.swing.*;
import java.awt.*;

public class Leaf
{
    int i = 0;

    Leaf increment()
    {
        i++;
        return this;
    }

    void print()
    {
        System.out.println("i = " + i);
    }

    public static void main(String[] args)
    {
        Leaf x = new Leaf();
        x.increment().increment().increment().print();
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        JButton jButton = new JButton();
        JComponent jComponent = new JComboBox();
        Container container = new Container();
        container.add(jFrame);
        container.add(jPanel);
        container.add(jButton);
        container.add(jComponent);
    }
}
