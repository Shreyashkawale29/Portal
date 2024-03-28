package com.PortalRegistration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String uemail = request.getParameter("username");
		String upass = request.getParameter("password");
		Connection con = null;
		PreparedStatement pst = null;
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		
		if(uemail == null || uemail.equals(" ") ){
			request.setAttribute("status", "InvalidEmail");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		
		
		if(upass == null || upass.equals(" ") ){
			request.setAttribute("status", "InvalidPassword");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/portal?useSSL =false","root","2912");
			 pst = con.prepareStatement("select * from users where uemail=? and upass=?");
			 pst.setString(1, uemail);
			 pst.setString(2, upass);
			  
			 ResultSet rs= pst.executeQuery();
			 if(rs.next()) {
				 session.setAttribute("name", rs.getString("uname"));
				 dispatcher = request.getRequestDispatcher("index.jsp");
			 }else {
				 request.setAttribute("status", "failed");
				 dispatcher = request.getRequestDispatcher("login.jsp");
			 }
			 dispatcher.forward(request, response);
	
			 
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
