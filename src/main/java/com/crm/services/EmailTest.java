//package com.crm.services;
//
//import java.util.Properties;
//import jakarta.mail.*;
//import jakarta.mail.internet.*;
//
//public class EmailTest {
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("vishwakarma3.mithilesh@gmail.com", "blbhwijacksflbna");
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("vishwakarma3.mithilesh@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vishwakarma3.mithilesh@gmail.com"));
//            message.setSubject("Test Email");
//            message.setText("This is a test email.");
//
//            Transport.send(message);
//            System.out.println("Email sent successfully!");
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//}
