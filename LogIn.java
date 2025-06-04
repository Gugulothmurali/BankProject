package com.approval;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/Log")
public class LogIn extends HttpServlet {
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String adminUsername = request.getParameter("username");
    String adminPassword = request.getParameter("password");

    
    String query = "SELECT * FROM registration WHERE uemail = ? AND password = ?";
    try 
    {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/honny", "root", "Murali@123");	        
        PreparedStatement stmt = conn.prepareStatement(query);
                    
        stmt.setString(1, adminUsername);
        stmt.setString(2, adminPassword);
        

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) 
        {
           	String role = rs.getString("role");
           	String status =rs.getString("status");
        	HttpSession session = request.getSession();
            session.setAttribute("admin", adminUsername); 
            session.setAttribute("role", role);
            if ("admin".equalsIgnoreCase(role)) 
            {
                response.sendRedirect(request.getContextPath() + "/Approval");
            } 
            else if ("user".equalsIgnoreCase(role) && adminUsername.equals(adminUsername) && adminPassword.equals(adminPassword) && "approved".equals(status))
            {

                response.sendRedirect(request.getContextPath() + "/demoServlet");
            }
                          
        } 
        else 
        {
            request.setAttribute("errorMessage", "Invalid username or password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("class.jsp"); 
            dispatcher.forward(request, response);
        }

    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
}
}