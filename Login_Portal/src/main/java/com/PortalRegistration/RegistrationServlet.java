package com.PortalRegistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("name");
		String upass = request.getParameter("pass");
		String urepass = request.getParameter("re_pass");
		String uemail = request.getParameter("email");
		String umobile = request.getParameter("contact");

//		PrintWriter out = response.getWriter();
//		out.println("user name:  "+uname);
//		out.println("pass:  "+upass);
//		out.println("email:  "+uemail);
//		out.println("mobile:  "+umobile);

		RequestDispatcher dispatcher = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		if(uname == null || uname.equals(" ") ){
			request.setAttribute("status", "InvalidName");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		if(uemail == null || uemail.equals(" ") ){
			request.setAttribute("status", "InvalidEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		if(upass == null || upass.equals(" ") ){
			request.setAttribute("status", "InvalidPassword");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}else if(! upass.equals(urepass)) {
			request.setAttribute("status", "InvalidConfirmPassword");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		if(umobile == null || umobile.equals(" ") ){
			request.setAttribute("status", "InvalidMobile");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}else if(umobile.length() >10) {
			request.setAttribute("status", "InvalidMobileLength");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/portal?useSSL =false","root","2912");
			
			 pst = con.prepareStatement("insert into users(uname,upass,uemail,umobile) values (?,?,?,?)");

			pst.setString(1, uname);
			pst.setString(2, upass);
			pst.setString(3, uemail);
			pst.setString(4, umobile);

			int rowCount = pst.executeUpdate();
			if(rowCount > 0) {
                System.out.println("Insert successful");
            } else {
                System.out.println("Insert failed");
            }
			
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
			}

			dispatcher.forward(request, response);
			
			System.out.println(con);
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		        if (con != null) {
		            try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        } else {
		            // Handle the case where con is null
		            System.out.println("Connection object is null, cannot close.");
		            System.out.println(con);
		        }
		    

		}

	}
