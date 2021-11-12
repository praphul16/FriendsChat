package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchProcess extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
        HttpSession session=request.getSession();
        HashMap userDetails=(HashMap)session.getAttribute("userDetails");
        if(userDetails!=null){
        String s=request.getParameter("state");
        String c=request.getParameter("city");
        String a=request.getParameter("area");
        db.DbConnect db=new db.DbConnect();
        java.util.ArrayList<java.util.HashMap> allUserDetails=
         db.searchPeople(s, c, (String)userDetails.get("email"), a);
        if(! allUserDetails.isEmpty()){
            java.util.HashMap address=new java.util.HashMap();
            address.put("state", s);
            address.put("city", c);
            address.put("area", a);
            session.setAttribute("allUserDetails",allUserDetails);
            session.setAttribute("address",address);
            response.sendRedirect("peoplesearch.jsp");
        }else{
            session.setAttribute("msg", "No Data Found!");
            response.sendRedirect("profile.jsp");
        }
        }else{
            session.setAttribute("msg", "Plz login First!");
            response.sendRedirect("home.jsp");
        }
        } catch (Exception ex) {
            
        }
    }
}
