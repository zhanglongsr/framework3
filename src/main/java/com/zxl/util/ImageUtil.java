package com.zxl.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageUtil
{
    /**
     * 生成随机数图片，一般用于网站验证码
     * 
     * @return
     */
    public void genRandomImage()
    {
        int width = 60, height = 18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        g.setColor(getRandColor(160, 200));
        for(int i = 0; i < 100; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        
        String sRand = "";
        for(int i = 0; i < 4; i++)
        {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
            
        }
        
        g.dispose();
        
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
    
    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    public static void main(String[] arg)
    {
        ImageUtil imageUtil = new ImageUtil();
        imageUtil.genRandomImage();
    }
    
}
