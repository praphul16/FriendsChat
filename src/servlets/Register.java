package servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class Register extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
        try{
            String n=request.getParameter("name");
            String ph=request.getParameter("phone");
            String e=request.getParameter("email");
            String g=request.getParameter("gender");
            String s=request.getParameter("state");
            String c=request.getParameter("city");
            String a=request.getParameter("area");
            String p=request.getParameter("password");
            //code to convert String into Date
            String dt=request.getParameter("dob");
            java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date=sdf.parse(dt);
            java.sql.Date d=new java.sql.Date(date.getTime());
            Part part=request.getPart("photo");
            InputStream is=null;
            if(part!=null){
                is=part.getInputStream();
            }
            java.util.HashMap userDetails=new java.util.HashMap();
            userDetails.put("email", e);
            userDetails.put("name", n);
            userDetails.put("pass", p);
            userDetails.put("phone", ph);
            userDetails.put("gender", g);
            userDetails.put("dob", d);
            userDetails.put("state", s);
            userDetails.put("city", c);
            userDetails.put("area", a);
            userDetails.put("photo", is);
            db.DbConnect db=new db.DbConnect();
            String m=db.doInsertUser(userDetails);
            db.disconnect();
            if(m.equalsIgnoreCase("Success")){
                userDetails.remove("pass");
                userDetails.remove("photo");
                session.setAttribute("userDetails", userDetails);
                response.sendRedirect("profile.jsp");
            }else if(m.equalsIgnoreCase("Already")){
                session.setAttribute("msg", "Email ID Already Exist!");
                response.sendRedirect("home.jsp");
            }else {
                session.setAttribute("msg", "Registration Failed!");
                response.sendRedirect("home.jsp");
            }
        } catch (Exception ex) {
            session.setAttribute("msg", "Registration Failed: "+ex);
            response.sendRedirect("home.jsp");
        }
	}

}
