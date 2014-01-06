/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.validate;

/**
 *
 * @author Administrator
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


import javax.imageio.ImageIO;


/**
 * 用来自动生成验证图和验证码,验证图是背景图加上干扰点加上验证码
 *
 * @author td
 *
 */

public final class CodeImageGenerator {

    private final static int DEF_WIDTH = 60;

    private final static int DEF_HEIGHT = 20;

    /**
     * 验证码
     */
    private String code = "";

    /**
     * 验证图的地址
     */
    private String path;

    private int width;

    private int height;

    private BufferedImage image;

    /**
     * 验证图对应的File对象
     */
    private File target;

    public CodeImageGenerator() {
        this(DEF_WIDTH, DEF_HEIGHT);
    }

    public CodeImageGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        generateCodeImage();
    }

    /**
     * 生成验证码和验证图
     *
     */
    public void generateCodeImage() {
        // create the image
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // set the font
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // create a random instance to generate the codes
        Random random = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 0, 0);
        } // generate a random code
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();
        try {
            //所添加的路径应该为部署的路径，而非实际路径
            File dir = new File("E:\\jsfproject\\TestOnlineFree\\dist\\gfdeploy\\TestOnlineFree\\TestOnlineFree-war_war\\interfaces\\image");
            String s = new Double(Math.random() * 995596390219L).toString();
            File imgFile = new File(dir, s + ".jpg");
            if (!imgFile.exists()) {
                imgFile.createNewFile();
            }
            target = imgFile;
            ImageIO.write(image, "JPG", imgFile);
            path =  "../image/" + s + ".jpg";
            System.err.println(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getCode() {
        if (code == null) {
            code = "";
        }
        return code;
    }

    public static void main(String[] args) throws Exception {
        // File imgFile = new File("codeImage.jpeg");
        // CodeImageGenerator cig = new CodeImageGenerator();
        // ImageIO.write(cig.getImage(), "JPEG", imgFile);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 当这个对象被回收时,同时销毁其对应的验证图
     */
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        // System.err.println("finalize");
        if (target.exists()) {
            target.delete();
        }
        super.finalize();
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }
}
