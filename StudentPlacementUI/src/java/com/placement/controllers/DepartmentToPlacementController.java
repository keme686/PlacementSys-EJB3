/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.DepartmentPlacementBLRemote;
import com.placement.business.PlacementBLRemote;
import com.placement.entity.Department;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
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
public class DepartmentToPlacementController extends HttpServlet {

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
        DepartmentPlacementBLRemote deptPlacementBL = factory.getDepartmentPlacementBL();
        PlacementBLRemote placementBL = factory.getPlacementBL();
        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String placementId = request.getParameter("placementId");
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                try {
                    List<DepartmentPlacement> departmentToPlaceIns = new ArrayList<DepartmentPlacement>();
                    int plcid = Integer.parseInt(placementId);
                    if (plcid > 0) {
                        Placement placement = placementBL.get(plcid);
                        departmentToPlaceIns = placement.getDepartmentPlacementList();
                        factory.close();
                    }
                    int pageIndex = 0;
                    int pageSize = 10;

                    int totalPages = 0;
                    int totalRecords = departmentToPlaceIns.size();
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
                    List<DepartmentPlacement> departmentls = departmentToPlaceIns.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");
                    out.print("<page>" + request.getParameter("page") + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (DepartmentPlacement cdo : departmentls) {
                        out.print("<row id='" + cdo.getId() + "'>");
                        out.print("<cell>" + cdo.getId() + "</cell>");
                        out.print("<cell>" + cdo.getDepartmentId() + "</cell>");
                        out.print("<cell>" + cdo.getDepartmentId().getCode() + "</cell>");
                        out.print("<cell>" + cdo.getDepartmentId().getName() + "</cell>");
                        out.print("<cell>" + cdo.getCapacity() + "</cell>");
                        out.print("<cell>" + cdo.getMaxViolation() + "</cell>");
                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");
                String deptId = request.getParameter("deptid");
                String placeId = request.getParameter("placementId");
                String capacity = request.getParameter("capacitys");
                String maxviolation = request.getParameter("maxviolation");
                DepartmentPlacement d = new DepartmentPlacement();

                if (oper.equals("edit")) {
                    String id = request.getParameter("id");

                    d.setId(Integer.parseInt(id));
                    d.setCapacity(Integer.parseInt(capacity));
                    d.setDepartmentId(new Department(Integer.parseInt(deptId)));
                    d.setPlacementId(new Placement(Integer.parseInt(placeId)));
                    d.setMaxViolation(Integer.parseInt(maxviolation));
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    try {

                        if (deptPlacementBL.update(d)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    factory.close();
                } else if (oper.equals("add")) {
                    try {
                        d.setCapacity(Integer.parseInt(capacity));
                        d.setDepartmentId(new Department(Integer.parseInt(deptId)));
                        d.setPlacementId(new Placement(Integer.parseInt(placeId)));
                        d.setMaxViolation(Integer.parseInt(maxviolation));

                        d.setMaxViolation(Integer.parseInt(maxviolation));
                        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                        if (deptPlacementBL.save(d)) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    factory.close();
                } else if (oper.equals("del")) {
                    String id = request.getParameter("id");
                    try {
                        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                        if (deptPlacementBL.delete(Integer.parseInt(id))) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    factory.close();
                }
            }
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
