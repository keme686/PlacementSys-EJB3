/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.StudentBLRemote;
import com.placement.business.UsersBLRemote;
import com.placement.entity.Student;
import com.placement.entity.UserField;
import com.placement.entity.Users;
import com.placement.utility.EJBWrapperFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemele
 */
public class LoginController extends HttpServlet {
  
    @Override
    public void init() throws ServletException {
        super.init(); 
        
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EJBWrapperFactory factory = new EJBWrapperFactory();
       UsersBLRemote userbl = factory.getUsersBL();
       
        response.setContentType("text/xml;charset=UTF-8");
        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
        out.print("<infos>");
        out.print("<info>");
        try {
            if (request.getParameter("action") != null && request.getParameter("action").equals("updateData")) {
                String current = request.getParameter("currentpwd");
                String newpwd = request.getParameter("newpwd");
                String idnum = (String) request.getSession().getAttribute("user");
                Users u = userbl.get(idnum);
                if (!u.getPassword().equals(current)) {
                    out.println("<status>0</status>");
                } else {
                    u.setPassword(newpwd);
                    u.setLastModified(new Date());
                    if (userbl.updatePassword(u)) {
                        out.println("<status>1</status>");
                    }
                }
            } else if (request.getParameter("action") != null && request.getParameter("action").equals("login")) {             
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                if (userbl.validate(username, password)) {
                    System.out.println("Validated");
                    request.getSession().setAttribute("user", username);
                    request.getSession().setMaxInactiveInterval(600); //max idle time to 10 min
                    out.print("<status>1</status>");
                    Users user = userbl.get(username);
                    if (user != null && user.getRole() == UserField.ADMIN) {
                        user.setLastLogin(new Date());
                        userbl.update(user);
                        request.getSession().setAttribute("name", username);
                        out.print("<url>" + getServletContext().getContextPath() + "</url>");
                    } else if (user != null && user.getRole() == UserField.STUDENT) {
                        StudentBLRemote studentBL = factory.getStudentBL();
                        user.setLastLogin(new Date());
                        userbl.update(user);
                        Student s = studentBL.get(username);
                        request.getSession().setAttribute("name", s.getFirstName() + " " + s.getMiddleName() + " " + s.getLastName());
                        out.print("<url>" + getServletContext().getContextPath() + "/choice/</url>");                        
                    }
                } else {
                    out.print("<status>-1</status>");
                    out.print("<url></url>");
                    System.out.println("Invalid");
                }
            }
            out.print("</info>");
            out.print("</infos>");
            factory.close();
        } catch (Exception e) {
            out.print("<status>-1</status>");
            e.printStackTrace();
        } finally {
            userbl =null;
            factory.close();
            factory=null;
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
