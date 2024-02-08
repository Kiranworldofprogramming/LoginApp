package com.cp.registration;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final String GET_PASSWORD = "SELECT * FROM USERS WHERE UEMAIL=? AND UPWD=?";
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String uemail = req.getParameter("username");
		String upwd = req.getParameter("password");
		Connection con = null;
		HttpSession session = req.getSession();
		RequestDispatcher dispatcher = null;
		
		if(uemail == null || uemail.equals("")) {
			req.setAttribute("status", "invalidEmail");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, res);
		}
		
		if(upwd == null || upwd.equals("")) {
			req.setAttribute("status", "invalidUpwd");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, res);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql:///practice","root","Y1012Jqkhkp");
			PreparedStatement ps = con.prepareStatement(GET_PASSWORD);
			ps.setString(1, uemail);
			ps.setString(2, upwd);
			
			ResultSet rs = ps.executeQuery();
			 if(rs.next()) {
				 session.setAttribute("name", rs.getString("uname"));
				 dispatcher = req.getRequestDispatcher("index.jsp");
			 }else {
				 req.setAttribute("status", "failed");
				 dispatcher = req.getRequestDispatcher("login.jsp");
			 }
			 dispatcher.forward(req, res);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
