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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemele
 */
public class UsersController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

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
        EJBWrapperFactory factory = new EJBWrapperFactory();
        UsersBLRemote userBL = factory.getUsersBL();
        StudentBLRemote studentBL = factory.getStudentBL();
        try {
            PrintWriter out = response.getWriter();
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                if (page == null) {
                    page = "1";
                }
                if (rows == null) {
                    rows = "15";
                }
                try {
                    List<Users> users = userBL.getAll();
                    int pageIndex = 0;
                    int pageSize = 10;

                    int totalPages = 0;
                    int totalRecords = users.size();
                    try {
                        pageIndex = Integer.parseInt(page) - 1;
                        pageSize = Integer.parseInt(rows);

                        if (totalRecords > 0) {
                            if (totalRecords % pageSize == 0) {
                                totalPages = totalRecords / pageSize;
                            } else {
                                totalPages = (totalRecords / pageSize) + 1;
                            }
                        } else {
                            totalPages = 0;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int last = (pageIndex * pageSize) + pageSize;
                    if (last >= totalRecords) {
                        last = totalRecords;
                    }
                    int start = pageIndex * pageSize;
                    if (start < 0) {
                        start = 0;
                    }
                    List<Users> usersls = users.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");

                    out.print("<page>" + page + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (Users cdo : usersls) {
                        out.print("<row id='" + cdo.getUsername() + "'>");
                        if (cdo.getRole() == UserField.STUDENT) {
                            Student s = studentBL.get(cdo.getUsername());
                            if (s != null) {
                                out.print("<cell>" + s.getFirstName() + " " + s.getMiddleName() + " " + s.getLastName() + "</cell>");
                            }
                        } else {
                            out.print("<cell>" + cdo.getUsername() + "</cell>");
                        }
                        out.print("<cell>" + cdo.getUsername() + "</cell>");
                        out.print("<cell>" + (cdo.getRole() == UserField.ADMIN ? "Admin" : "Student") + "</cell>");
                        out.print("<cell>" + (cdo.getStatus() == 1 ? "Active" : "Not Active") + "</cell>");
                        out.print("<cell>" + (cdo.getLastLogin() == null ? "Never" : cdo.getLastLogin()) + "</cell>");
                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory=null;

            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");
                Users c;
                String username = request.getParameter("username");
                if (oper.equals("resetpwd")) {
                    try {
                        if (username != null) {
                            c = userBL.get(username);
                            if (c != null && userBL.generatePassword(c)) {
                                out.println("<info>Success</info>");
                            } else {
                                out.println("<info>Cant reset password!</info>");
                            }
                        } else {
                            out.println("<info>Failed to reset password!</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.print("<info>Error</info>");
                    }
                } else if (oper.equals("chstatus")) {
                    try {
                        if (username != null) {
                            Users us = userBL.get(username);
                            us.setStatus((us.getStatus() == UserField.STATUS_ACTIVE ? UserField.STATUS_DEACTIVATED : UserField.STATUS_ACTIVE));
                            userBL.update(us);
                            out.println("<info>Success</info>");
                        } else {
                            out.println("<info>Cannot change status!</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.print("<info>Error</info>");
                    }
                }
                factory.close();
                factory=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
