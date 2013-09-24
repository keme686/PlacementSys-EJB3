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
public class ForgetPasswordController extends HttpServlet {

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
            System.out.println("reset");
            if (request.getParameter("action") != null && request.getParameter("action").equals("reset")) {
                String idnum = request.getParameter("idnum");
                String email = request.getParameter("email");               
                Users u = userbl.get(idnum);
                if(u != null){
                    StudentBLRemote studentBL = factory.getStudentBL();
                    Student s = studentBL.get(idnum);
                    if(email != null && email.equals(s.getEmail()) && userbl.generatePassword(u)){                         
                         out.println("<status>1</status>");
                         out.print("<url>" + getServletContext().getContextPath() + "</url>");
                    }else{
                        out.println("<status>-1</status>");
                    }
                }else{
                    out.println("<status>-1</status>");
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
