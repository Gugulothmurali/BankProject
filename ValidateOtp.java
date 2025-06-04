package com.approval;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ValidateOtp")
public class ValidateOtp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int value = Integer.parseInt(request.getParameter("otp"));
        HttpSession session = request.getSession();
        int otp = (int) session.getAttribute("otp");

        RequestDispatcher dispatcher;

        if (value == otp) {
            request.setAttribute("email", session.getAttribute("email")); // get from session, not from request
            request.setAttribute("status", "success");
            dispatcher = request.getRequestDispatcher("newPassword.jsp");
        } else {
            request.setAttribute("message", "Wrong OTP");
            dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
        }

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("Error: Could not find target JSP page.");
        }
    }
}
