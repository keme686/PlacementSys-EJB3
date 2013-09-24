/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.CollegeBLRemote;
import com.placement.entity.College;
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
public class CollegeController extends HttpServlet {

    

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
        try {
            PrintWriter out = response.getWriter();
            EJBWrapperFactory factory = new EJBWrapperFactory();
            CollegeBLRemote collegeBL = factory.getCollegeBL();
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                if (page == null) {
                    page = "1";
                }
                if (rows == null) {
                    rows = "5";
                }
                try {
                    List<College> colleges = collegeBL.getAll();
                    factory.close();
                    int pageIndex = 0;
                    int pageSize = 10;

                    int totalPages = 0;
                    int totalRecords = colleges.size();
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
                    List<College> collegels = colleges.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");

                    out.print("<page>" + page + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (College cdo : collegels) {
                        out.print("<row id='" + cdo.getId() + "'>");
                        out.print("<cell>" + cdo.getId() + "</cell>");
                        out.print("<cell>" + cdo.getCode() + "</cell>");
                        out.print("<cell>" + cdo.getName() + "</cell>");
                        out.print("<cell>" + cdo.getAddress() + "</cell>");
                        out.print("<cell>" + cdo.getDescription() + "</cell>");
                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");

                String code = request.getParameter("code");
                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String desc = request.getParameter("description");
                College c = new College();
                String id = request.getParameter("id");
                c.setCode(code);
                c.setName(name);
                c.setAddress(address);
                c.setDescription(desc);
                if (oper.equals("edit")) {
                    try {
                        if (id != null) {
                            c.setId(Integer.parseInt(id));
                        }
                        if (collegeBL.update(c)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.print("<info>Error</info>");
                    }
                    factory.close();
                } else if (oper.equals("add")) {
                    try {
                        if (collegeBL.save(c)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.print("<info>Error</info>");
                    }
                    factory.close();
                } else if (oper.equals("del")) {
                    try {
                        if (collegeBL.delete(Integer.parseInt(id))) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                        factory.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.print("<info>Error</info>");
                    }
                }
            }
            /*}else{
             out.println("<h1>College bl not found</h1>");
             }*/

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
