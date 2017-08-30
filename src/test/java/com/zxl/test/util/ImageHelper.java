package com.zxl.test.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHelper
{
    private static BufferedImage makeThumbnail(Image img, int width, int height)
    {
        BufferedImage tag = new BufferedImage(width, height, 1);
        Graphics g = tag.getGraphics();
        g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
        g.dispose();
        return tag;
    }
    
    private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException
    {
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf(46) + 1);
        BufferedImage subImage = new BufferedImage(subImageBounds.width, subImageBounds.height, 1);
        Graphics g = subImage.getGraphics();
        
        g.setColor(Color.white);
        g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
        
        int srcX = subImageBounds.x, srcY = subImageBounds.y, width = subImageBounds.width, height = subImageBounds.height, descX = 0, descY = 0;
        
        if(srcX < 0)
        {
            width = srcX + subImageBounds.width;
            if(width < 0)
            {
                width = 0;
            }
            srcX = 0;
        }
        else if(srcX > image.getWidth())
        {
            srcX = image.getWidth();
            width = 0;
        }
        if(srcX + width > image.getWidth())
        {
            width = image.getWidth() - srcX;
        }
        
        if(srcY < 0)
        {
            height = srcY + subImageBounds.height;
            if(height < 0)
            {
                height = 0;
            }
            srcY = 0;
        }
        else if(srcY > image.getHeight())
        {
            srcY = image.getHeight();
            height = 0;
        }
        if(srcY + height > image.getHeight())
        {
            height = image.getHeight() - srcY;
        }
        
        if(subImageBounds.x >= 0)
        {
            descX = 0;
        }
        else
        {
            descX = -subImageBounds.x;
        }
        
        if(subImageBounds.y >= 0)
        {
            descY = 0;
        }
        else
        {
            descY = -subImageBounds.y;
        }
        if(width > 0 && height > 0)
        {
            g.drawImage(image.getSubimage(srcX, srcY, width, height), descX, descY, null);
        }
        g.dispose();
        ImageIO.write(subImage, formatName, subImageFile);
    }
    
    public static void cut(String srcImageFile, String descDir, int width, int height, Rectangle rect) throws IOException
    {
        Image image = ImageIO.read(new File(srcImageFile));
        BufferedImage bImage = makeThumbnail(image, width, height);
        
        saveSubImage(bImage, rect, new File(descDir));
    }
    
    public static void cut(File srcImageFile, File descDir, int width, int height, Rectangle rect) throws IOException
    {
        Image image = ImageIO.read(srcImageFile);
        BufferedImage bImage = makeThumbnail(image, width, height);
        
        saveSubImage(bImage, rect, descDir);
    }
    
    /**
     * 图片剪切
     * 
     * @param path
     *            图片路径
     * @param angle
     *            旋转度数
     * @param rurn_x
     *            是否水平翻转 1不翻转 -1翻转
     * @param rurn_y
     *            是否垂直翻转 1不翻转 -1翻转
     * @param s_x
     *            起始点x坐标
     * @param s_y
     *            起始点y坐标
     * @param e_x
     *            结束点x坐标
     * @param e_y
     *            结束点y坐标
     * @return BufferedImage 剪切后图片
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(BufferedImage img, int angle, String rurn_x, String rurn_y, int s_x, int s_y, int e_x, int e_y) throws IOException
    {
        BufferedImage img1 = ImageHelper.rotateImage(img, angle); // 旋转
        
        if("-1".equals(rurn_x))
        {
            img1 = ImageHelper.flipHorizontalJ2D(img1); // 水平翻转
        }
        if("-1".equals(rurn_y))
        {
            img1 = ImageHelper.flipVerticalJ2D(img1); // 垂直翻转
        }
        
        int cutWidth = e_x - s_x; // 裁剪的宽度
        int cutHeight = e_y - s_y;// 裁剪的高度
        
        // // 原图的宽
        // int width = img1.getWidth();
        // // 原图的高
        // int height = img1.getHeight();
        
        // // 裁剪图片的高宽不能比原图都大
        // if(cutWidth > width)
        // {
        // cutWidth = width;
        // }
        // if(cutHeight > height)
        // {
        // cutHeight = height;
        // }
        
        // 剪切图片
        img = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_INT_BGR);// 创建裁剪宽、高的图像缓冲区
        ImageHelper.cutImage(img1, img, s_x, s_y, cutWidth, cutHeight);
        
        // 缩放图片
        img = imageZoom(img, null, null);
        
        return img;
    }
    
    /**
     * 图片旋转
     * 
     * @param filename
     *            图片路径
     * @param angle
     *            旋转度数
     * @return 旋转后的图片
     */
    public static BufferedImage rotateImage(BufferedImage buffimg, int angle)
    {
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        // 目标图片
        BufferedImage tempimg = null;
        Graphics2D graphics2d = null;
        int type = buffimg.getColorModel().getTransparency();
        if(angle % 360 == 0)
        {
            return buffimg;
        }
        else if(angle % 180 == 0)
        {
            // 图片形状不变
            tempimg = new BufferedImage(w, h, type);
            graphics2d = tempimg.createGraphics();
            // Math.toRadians(angle), w / 2, h / 2, 三参数中,前者为角度，后两者为原图片左上角移动后的对齐点
            graphics2d.rotate(Math.toRadians(angle), w / 2, h / 2);
        }
        else if(angle % 90 == 0)
        {
            // 图片形状变了
            tempimg = new BufferedImage(h, w, type);
            graphics2d = tempimg.createGraphics();
            // 使之顺时针为+,逆时针为-
            // angle = -angle;
            // Math.toRadians(angle),x,y, 前者为角度，后两者为原图片 移动后的对齐点
            if(angle < 0)// 顺时针旋转90(h/2,h/2)
                graphics2d.rotate(Math.toRadians(angle), w / 2, w / 2);
            else
                // 逆时针旋转-90(w/2,w/2)
                graphics2d.rotate(Math.toRadians(angle), h / 2, h / 2);
        }
        else
        {
            return buffimg;
        }
        
        // 把buffimg写到目标图片中去
        graphics2d.drawImage(buffimg, 0, 0, null);
        graphics2d.dispose();
        buffimg = tempimg; // 让用于显示的缓冲区图像指向过滤后的图像
        // try {
        // ImageIO.write(buffimg, "jpg", file);
        // } catch (IOException e) {
        // //e.printStackTrace();
        // }
        // buffimg.flush();
        return buffimg;
    }
    
    // /**
    // * 图片旋转
    // *
    // * @param image
    // * 原图片
    // * @param degree
    // * 旋转的度数
    // * @return 旋转后的图片
    // */
    // public static BufferedImage rotateImage(BufferedImage image, int degree)
    // {
    // // 图片背景颜色
    // Color bgcolor = Color.white;
    // int iw = image.getWidth();// 原始图象的宽度
    // int ih = image.getHeight();// 原始图象的高度
    // int w = 0;
    // int h = 0;
    // int x = 0;
    // int y = 0;
    // degree = degree % 360;
    // if(degree < 0)
    // degree = 360 + degree;// 将角度转换到0-360度之间
    // // double ang = degree * 0.0174532925;//将角度转为弧度
    // double ang = Math.toRadians(degree);
    //
    // /**
    // * 确定旋转后的图象的高度和宽度
    // */
    // if(degree == 180 || degree == 0 || degree == 360)
    // {
    // w = iw;
    // h = ih;
    // }
    // else if(degree == 90 || degree == 270)
    // {
    // w = ih;
    // h = iw;
    // }
    // else
    // {
    // int d = iw + ih;
    // w = (int) (d * Math.abs(Math.cos(ang)));
    // h = (int) (d * Math.abs(Math.sin(ang)));
    // }
    //
    // x = (w / 2) - (iw / 2);// 确定原点坐标
    // y = (h / 2) - (ih / 2);
    // BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
    // Graphics gs = rotatedImage.getGraphics();
    // gs.setColor(bgcolor);
    // gs.fillRect(0, 0, w, h);// 以给定颜色绘制旋转后图片的背景
    // AffineTransform at = new AffineTransform();
    // at.rotate(ang, w / 2, h / 2);// 旋转图象
    // at.translate(x, y);
    // AffineTransformOp op = new AffineTransformOp(at,
    // AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    // op.filter(image, rotatedImage);
    // image = rotatedImage;
    // return image;
    // }
    
    /**
     * 图像水平翻转。
     * 
     * @param bufferedImage
     *            原图像。
     * @return 返回水平翻转后的图像。
     */
    public static BufferedImage flipHorizontalJ2D(BufferedImage bufferedImage)
    {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        
        BufferedImage dstImage = new BufferedImage(width, height, bufferedImage.getType());
        
        AffineTransform affineTransform = new AffineTransform(-1, 0, 0, 1, width, 0);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        return affineTransformOp.filter(bufferedImage, dstImage);
    }
    
    /**
     * 图像竖直翻转。
     * 
     * @param bufferedImage
     *            原图像。
     * @return 返回竖直翻转后的图像。
     */
    public static BufferedImage flipVerticalJ2D(BufferedImage bufferedImage)
    {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        
        BufferedImage dstImage = new BufferedImage(width, height, bufferedImage.getType());
        
        AffineTransform affineTransform = new AffineTransform(1, 0, 0, -1, 0, height);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        return affineTransformOp.filter(bufferedImage, dstImage);
    }
    
    /**
     * 图片剪切，支持补白
     * 
     * @param src
     *            原图片
     * @param dest
     *            剪切后的图片
     * @param startX
     *            剪切的起点X坐标
     * @param startY
     *            剪切的起点Y坐标
     * @param cutWidth
     *            需要剪切的宽度
     * @param cutHeight
     *            需要剪切的高度
     */
    public static void cutImage(BufferedImage src, BufferedImage dest, int startX, int startY, int cutWidth, int cutHeight)
    {
        // change by zhangxl 2012-10-17
        int width = src.getWidth();// 原始图片的宽
        int height = src.getHeight();// 原始图片的高
        
        for(int i = 0; i < cutWidth; i++)// 循环Y坐标
        {
            int _x = i + startX;
            for(int j = 0; j < cutHeight; j++)// 循环X坐标
            {
                int _y = j + startY;
                // 如果坐标值未超出原始图的宽、高值，那么读取原始图对应坐标的RGB值填充到新图缓冲区中
                if(_x < width && _y < height)
                {
                    dest.setRGB(i, j, src.getRGB(_x, _y));
                }
                else
                {// 超越了原始图，用白色进行补白
                    dest.setRGB(i, j, Color.WHITE.getRGB());
                }
                
            }
        }
        
    }
    
    /**
     * 图片等比缩放
     * 
     * @param buffimg
     * @return
     * @throws IOException
     */
    public static BufferedImage imageZoom(BufferedImage buffimg, Integer outWidth, Integer outHeight) throws IOException
    {
        // 是否等比例缩放
        boolean proportion = true;
        // 输出图片宽的极限
        int outputMaxWidth = 600;
        // 输出图片高的极限
        int outputMaxHeight = 500;
        
        if(outWidth != null && outWidth > 0)
        {
            outputMaxWidth = outWidth;
        }
        if(outHeight != null && outHeight > 0)
        {
            outputMaxHeight = outHeight;
        }
        
        // 原图的宽
        int width = buffimg.getWidth();
        // 原图的高
        int height = buffimg.getHeight();
        
        // 如果原图的高和宽在限制的比例范围之内
        if(width <= outputMaxWidth && height <= outputMaxHeight)
        {
            return buffimg;
        }
        
        int newWidth;
        int newHeight;
        
        // 判断是否是等比缩放
        if(proportion == true)
        {
            // 为等比缩放计算输出的图片宽度及高度
            double rate1 = ((double) buffimg.getWidth(null)) / (double) outputMaxWidth + 0.1;
            double rate2 = ((double) buffimg.getHeight(null)) / (double) outputMaxHeight + 0.1;
            // 根据缩放比率大的进行缩放控制
            double rate = rate1 > rate2 ? rate1 : rate2;
            newWidth = (int) (((double) buffimg.getWidth(null)) / rate);
            newHeight = (int) (((double) buffimg.getHeight(null)) / rate);
        }
        else
        {
            newWidth = outputMaxWidth; // 输出的图片宽度
            newHeight = outputMaxHeight; // 输出的图片高度
        }
        
        BufferedImage img = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(buffimg.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        
        return img;
    }
}
