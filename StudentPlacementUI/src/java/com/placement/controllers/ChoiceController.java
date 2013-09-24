/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.ChoiceBLRemote;
import com.placement.entity.Choice;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.entity.Student;
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
public class ChoiceController extends HttpServlet {


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
        PrintWriter out = response.getWriter();
        EJBWrapperFactory factory = new EJBWrapperFactory();
     ChoiceBLRemote   choiceBL = factory.getChoiceBL();

        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String idnum = request.getParameter("studentid");
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                try {
                    List<Choice> studentChoice = new ArrayList<Choice>();
                    try {
                        studentChoice = choiceBL.getChoices(idnum);                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int pageIndex = 0;
                    int pageSize = 10;

                    int totalPages = 0;
                    int totalRecords = studentChoice.size();
                    try {
                        pageIndex = Integer.parseInt(page) - 1;
                        pageSize = Integer.parseInt(rows);

                        if (totalRecords > 0) {
                            if (totalRecords % Integer.parseInt(rows) == 0) {
                                totalPages = totalRecords / Integer.parseInt(rows);
                            } else {
                                totalPages = (totalRecords / Integer.parseInt(rows)) + 1;
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
                    List<Choice> departmentls = studentChoice.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");

                    out.print("<page>" + request.getParameter("page") + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (Choice cdo : departmentls) {
                        out.print("<row id='" + cdo.getId() + "'>");
                        out.print("<cell>" + cdo.getId() + "</cell>");
                        out.print("<cell>" + cdo.getDepartmentPlacementId().getDepartmentId().getCode() + "</cell>");
                        out.print("<cell>" + cdo.getDepartmentPlacementId().getDepartmentId().getName() + "</cell>");
                        out.print("<cell>" + cdo.getRank() + "</cell>");

                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");

                String deptId = request.getParameter("departmentname");
                String id = request.getParameter("id");
                String rank = request.getParameter("rank");
                Choice d = new Choice();

                if (oper.equals("edit")) {
                    try {
                        d.setId(Integer.parseInt(id));
                        d.setRank(Integer.parseInt(rank));
                        d.setDepartmentPlacementId(new DepartmentPlacement(Integer.parseInt(deptId)));
                        if (choiceBL.update(d)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                        factory.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (oper.equals("add")) {
                    try {
                        String studentid = request.getParameter("studentid");
                        String placementid = request.getParameter("placementId");
                        d.setIdNumber(new Student(studentid));
                        d.setRank(Integer.parseInt(rank));
                        d.setDepartmentPlacementId(new DepartmentPlacement(Integer.parseInt(deptId)));
                        d.setPlacementId(new Placement(Integer.parseInt(placementid)));
                        if (choiceBL.save(d)) {
                            System.out.println("success " + oper);
                            out.print("<info>success</info>");
                        } else {
                            System.out.println("failed to " + oper);
                            out.print("<info>failed</info>");
                        }
                        factory.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (oper.equals("del")) {
                    try {

                        if (choiceBL.delete(Integer.parseInt(id))) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                        factory.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
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
