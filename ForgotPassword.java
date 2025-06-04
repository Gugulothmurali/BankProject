
package com.approval;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/forgotPassword")
public class ForgotPassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        RequestDispatcher dispatcher = null;
        int otpvalue = 0;
        HttpSession mySession = request.getSession();

        if (email != null && !email.equals("")) { // use AND here
            // sending otp
            Random rand = new Random();
            otpvalue = rand.nextInt(1255650);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("gugulothmurali6@gmail.com", "tqqayctrvscqdqkv"); // App password
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("gugulothmurali6@gmail.com")); // ✅ Set from your email
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject("Your OTP Code");
                message.setText("Your OTP is: " + otpvalue);

                Transport.send(message);
                System.out.println("Message sent successfully");

                request.setAttribute("message", "OTP is sent to your email id");
                mySession.setAttribute("otp", otpvalue);
                mySession.setAttribute("email", email);

                dispatcher = request.getRequestDispatcher("EnterOtp.jsp"); // ✅ FIXED HERE
            } catch (MessagingException e) {
                e.printStackTrace();
                request.setAttribute("message", "Failed to send OTP. Please try again.");
                dispatcher = request.getRequestDispatcher("forgotPassword.jsp"); // fallback
            }
        } else {
            request.setAttribute("message", "Email is required");
            dispatcher = request.getRequestDispatcher("forgotPassword.jsp");
        }

        // ✅ Dispatch the response
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("Error: Dispatcher is null.");
        }
    }
}

