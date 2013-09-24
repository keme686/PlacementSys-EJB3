/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.StudentBLRemote;
import com.placement.business.UsersBLRemote;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.entity.Student;
import com.placement.entity.UserField;
import com.placement.entity.Users;
import com.placement.utility.EJBWrapperFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemele
 */
public class PlacementDepartmentController extends HttpServlet {  

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
        StudentBLRemote studentBL =  factory.getStudentBL();
       UsersBLRemote userbl=  factory.getUsersBL();
        try {

            List<DepartmentPlacement> departments = new ArrayList<DepartmentPlacement>();
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");               
                String username = (String) request.getSession().getAttribute("user");
                if (username == null) {                    
                    return;
                }
                Users u = userbl.get(username);
                    if (u.getRole() == UserField.STUDENT) {
                        Student s = studentBL.get(username);
                        Placement p = s.getPlacementId();
                        departments = p.getDepartmentPlacementList();
                    }

                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                try {                    
                    int pageIndex = 0;
                    int pageSize = 10;
                    int totalPages = 0;
                    int totalRecords = departments.size();
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
                    List<DepartmentPlacement> departmentls = departments.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");

                    out.print("<page>" + request.getParameter("page") + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (DepartmentPlacement cdo : departmentls) {
                        try {
                            out.print("<row id='" + cdo.getId() + "'>");
                            out.print("<cell>" + cdo.getId() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentId().getCode() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentId().getName() + "</cell>");
                            out.print("<cell>" + cdo.getCapacity() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentId().getAddress() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentId().getDescription() + "</cell>");                            
                            out.print("</row>");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();

            } 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
