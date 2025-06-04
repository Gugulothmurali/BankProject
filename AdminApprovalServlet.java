package com.approval;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/Approval")
public class AdminApprovalServlet extends HttpServlet {
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	 {		String query = "SELECT id, firstname, uemail FROM registration WHERE status = 'pending'";
        List<Map<String, String>> pendingUsers = new ArrayList<>();

        try 
        {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/honny", "root", "Murali@123");	        
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Map<String, String> user = new HashMap<>();
                user.put("id", rs.getString("id"));
                user.put("firstname", rs.getString("firstname"));
                user.put("uemail", rs.getString("uemail"));
                pendingUsers.add(user);
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e);
		}

       
        request.setAttribute("pendingUsers", pendingUsers);

       
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin.jsp");
        dispatcher.forward(request, response);
    }


   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        String action = request.getParameter("action");  

        if ("Approval".equals(action)) {
            updateUserStatus(userId, "approved");
        } else if ("reject".equals(action)) {
            updateUserStatus(userId, "rejected");
        }

        System.out.println("Redirecting to /Approval");
        response.sendRedirect(request.getContextPath() + "/Approval");
        System.out.println("Redirect statement executed");
    }

    // Method to update the user's status (approve/reject)
    private void updateUserStatus(String userId, String status) 
    {
        String query = "UPDATE registration SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/honny", "root", "Murali@123");
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {

            stmt.setString(1, status);
            stmt.setString(2, userId);

            stmt.executeUpdate();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}

	
