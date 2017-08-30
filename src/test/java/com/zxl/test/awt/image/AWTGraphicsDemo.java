package com.zxl.test.awt.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AWTGraphicsDemo extends Frame
{
    public AWTGraphicsDemo()
    {
        super("Java AWT Examples");
        prepareGUI();
    }
    
    private void prepareGUI()
    {
        super.setSize(400, 400);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.GRAY);
        Font font = new Font("Serif", Font.PLAIN, 24);
        g.setFont(font);
        g.drawString("Welcome to TutorialsPoint", 50, 150);
    }
    
    public static void main(String[] args)
    {
        AWTGraphicsDemo awtGraphicsDemo = new AWTGraphicsDemo();
        awtGraphicsDemo.setVisible(true);
    }
}
