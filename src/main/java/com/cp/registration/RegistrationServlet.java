package com.cp.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	
	private static final String INSERT_INTO_USERS = "INSERT INTO USERS(UNAME,UPWD,UEMAIL,UMOBILE) VALUES(?,?,?,?)";

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		pw.print("<h1>Working</h1>");
		
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upwd = req.getParameter("pass");
		String umobile = req.getParameter("contact");
		
		pw.print(uname+"<br/>");
		pw.print(uemail+"<br/>");
		pw.print(upwd+"<br/>");
		pw.print(umobile+"<br/>");
		
		RequestDispatcher dispatcher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql:///practice","root","Y1012Jqkhkp");
			PreparedStatement ps = con.prepareStatement(INSERT_INTO_USERS); 
			ps.setString(1,uname);
			ps.setString(2,upwd);
			ps.setString(3,uemail);
			ps.setString(4,umobile);
			
			int rowCount = ps.executeUpdate();
			dispatcher = req.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				req.setAttribute("status", "success");
			}else {
				req.setAttribute("status", "failed");
			}
			dispatcher.forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
