package com.approval;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/newPassword")
public class NewPassword extends HttpServlet 
{
		private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException
		    {
	        PrintWriter out=response.getWriter();
			HttpSession session = request.getSession();
			String uemail = request.getParameter("uemail");
			String newPassword = request.getParameter("password");
			String confPassword = request.getParameter("conPassword");
			
			
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/honny","root","Murali@123");
					PreparedStatement pst = con.prepareStatement("update registration set password = ?,conpassword=? where uemail = ?");
					pst.setString(1, newPassword);
					pst.setString(2, confPassword);
					pst.setString(3, uemail);

					int rowCount = pst.executeUpdate();
					if (rowCount > 0)
					{
						RequestDispatcher rd=request.getRequestDispatcher("updatePassword.jsp");	
						rd.forward(request, response);
					} 
					else
					{
						
						RequestDispatcher rd=request.getRequestDispatcher("newPassword.jsp");	
						rd.forward(request, response);
						
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


