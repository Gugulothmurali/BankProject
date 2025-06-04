package com.approval;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/demoServlet")
public class DemoServlet extends HttpServlet {
	PreparedStatement stmt, stmt1;
	ResultSet rs, rs1;
	String nm;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("admin");
		String query = "SELECT firstname from registration WHERE uemail = ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/honny", "root", "Murali@123");
			stmt = conn.prepareStatement(query);

			stmt.setString(1, name);

			rs = stmt.executeQuery();
			while (rs.next()) {
				nm = rs.getString("firstname");
				out.println("<div style= 'text-align: center;padding: 15px; border-radius: 10px;'>");
				out.println("<h1>Welcome....." + nm + "</h1>");
			}
			String query1 = "select balance from account where firstname=?";
			stmt1 = conn.prepareStatement(query1);
			stmt1.setString(1, nm);
			rs1 = stmt1.executeQuery();
			while (rs1.next()) {
			    Double amt = rs1.getDouble("balance");
			    out.println("<h2 style='color: green; font-family: Arial, sans-serif;'>Available Balance</h2>");
			    out.println("<p style='font-size: 24px; color: #333; font-family: Verdana, sans-serif;'>₹ " + amt + "</p>");
			}

			out.println("<h3 style='color: red; font-weight: bold; margin-bottom: 10px;'>Actions:</h3>");

			out.println("<form action='benefi.jsp' method='get' style='margin: 5px;'>");
			out.println("<input type='submit' value='Add a Beneficiary' style='padding:10px 20px;border:none;background-color:#4CAF50;color:white;border-radius:5px;'>");
			out.println("</form>");

			out.println("<form action='transfer.html' method='get' style='margin: 5px;'>");
			out.println("<input type='submit' value='Transfer' style='padding:10px 20px;border:none;background-color:#2196F3;color:white;border-radius:5px;'>");
			out.println("</form>");

			out.println("<form action='transaction' method='get' style='margin: 5px;'>");
			out.println("<input type='submit' value='Transactions' style='padding:10px 20px;border:none;background-color:#FF9800;color:white;border-radius:5px;'>");
			out.println("</form>");
			
			// Logout Button
			out.println("<div style='text-align: center;'>");
			out.println("<form action='LogoutServlet' method='get'>");
			out.println("<input type='submit' value='Logout' style='background-color:red;color:white;padding:10px 20px;border:none;border-radius:5px;cursor:pointer;'>");
			out.println("</form>");
			out.println("</div>");

			
		} catch (Exception e) {
			out.println(e);
		}
	}

}
