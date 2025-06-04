package com.approval;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet 
{
	
	private static final String driver="com.mysql.cj.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/honny";
	private static final String userName="root";
	private static final String password="Murali@123";
	private Connection con=null;
	private Statement stmt=null;	
	public void init(ServletConfig config) throws ServletException 
	{		
		try
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userName,password);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		PrintWriter out=response.getWriter();
		try {			
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from transactions");
			out.println("<h1>Transactions</h1>");
			while(rs.next())
			{	
				out.println("<table border='1'>");
				out.println("<tr><th>Transaction Id</th><th>Receiver Account No</th><th>Amount</th><th>Transaction Time</th></tr>");
				out.println("<tr><td>"+rs.getInt("transaction_id")+"</td><td>"+rs.getInt("receiver_id")+"</td><td>"+rs.getDouble("amount")+"</td><td>"+rs.getDate("transaction_time")+"</td></tr></table>");
			}
	      	}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	

}
