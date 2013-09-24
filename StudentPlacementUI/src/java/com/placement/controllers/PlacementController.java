/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.PlacementBLRemote;
import com.placement.entity.College;
import com.placement.entity.Placement;
import com.placement.entity.Rule;
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
public class PlacementController extends HttpServlet {

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
       PlacementBLRemote placementBL =  factory.getPlacementBL();
        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                try {                    
                    List<Placement> students = placementBL.getAll();
                    int pageIndex = 0;
                    int pageSize = 10;
                    int totalPages = 0;
                    int totalRecords = students.size();
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
                    List<Placement> studentls = students.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");
                    out.print("<page>" + request.getParameter("page") + "</page>");
                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (Placement cdo : studentls) {
                        Rule r = cdo.getRuleId();
                        out.print("<row id='" + cdo.getId() + "'>");
                        out.print("<cell>" + cdo.getId() + "</cell>");
                        out.print("<cell>" + cdo.getCode() + "</cell>");
                        out.print("<cell>" + cdo.getName() + "</cell>");
                        out.print("<cell>" + cdo.getAcademicYear() + "</cell>");
                        out.print("<cell>" + cdo.getCollegeId().getCode() + "</cell>");
                        out.print("<cell>" + " CGPA-" + (r.getTopPercentage())
                                + "%, Gender-" + (r.getFemalePercentage())
                                + "%, Region-" + (r.getRegionPercentage())
                                + "%, Disability-" + (r.getDisabilityPercentage())
                                + "%, CGPA Cut Point-" + (r.getCutPoint())
                                + "</cell>");
                        out.print("<cell>" + (r.getId()) + "</cell>");
                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                }
                factory.close();
            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");
                String code = request.getParameter("code");
                String name = request.getParameter("name");
                String acyear = request.getParameter("acyear");
                String ruleId = request.getParameter("ruleId");
                String collegeI = request.getParameter("college");
                Placement p = new Placement();
                p.setCode(code);
                p.setName(name);
                p.setAcademicYear(acyear);

                if (oper.equals("add")) {
                    try {
                        p.setCollegeId(new College(Integer.parseInt(collegeI)));
                        p.setRuleId(new Rule(Integer.parseInt(ruleId)));

                        if (placementBL.save(p)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (oper.equals("edit")) {
                    try {
                        p.setCollegeId(new College(Integer.parseInt(collegeI)));
                        p.setRuleId(new Rule(Integer.parseInt(ruleId)));
                        String id = request.getParameter("id");
                        p.setId(Integer.parseInt(id));
                        if (placementBL.update(p)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (oper.equals("del")) {
                    try {                        
                        String id = request.getParameter("id");
                        if (placementBL.delete(Integer.parseInt(id))) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                factory.close();
            }
        } finally {
            placementBL=null;
            factory.close();
            factory = null;
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
