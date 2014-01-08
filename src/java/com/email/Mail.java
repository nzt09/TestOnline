/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email;

/**
 *
 * @author Administrator
 */
import java.util.Properties;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
public class Mail {

    public static void sendMail(String email,String newpassword) {
        String host = "smtp.163.com"; // 指定的smtp服务器  
        String from = "xsdonlinetest@163.com"; // 邮件发送人的邮件地址  
        String to = email; // 邮件接收人的邮件地址  
        final String username = "xsdonlinetest@163.com";  //发件人的邮件帐户  
        final String password = "xsd123456";   //发件人的邮件密码  

        // 创建Properties 对象  
        Properties props = System.getProperties();

        // 添加smtp服务器属性  
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");   //163的stmp不是免费的也不公用的，需要验证  
//这个比较重要
        // 创建邮件会话   原先用的getdefaultinstance 使session的使用出错
        Session session = Session.getInstance(props, new Authenticator() {  //验账账户  
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 定义邮件信息  
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            message.setSubject("在线考试系统");
            message.setText("系统给您分配的新密码:" + newpassword);

            // 发送消息  
            //session.getTransport("smtp").send(message);  //也可以这样创建Transport对象  
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
