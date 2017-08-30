package com.zxl.test.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.junit.Test;

public class IMageTest
{
    @Test
    public void testCreateImage4Color()
    {
        int width = 160;
        int height = 60;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.setColor(new Color(0, 0, 0));
        OutputStream os;
        try
        {
            os = new FileOutputStream(new File("f:\\a.jpg"));
            ImageIO.write(image, "JPEG", os);
        }
        catch(FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
