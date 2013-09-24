/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.StudentPlacementBLRemote;
import com.placement.entity.Student;
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
public class StudentPlacementController extends HttpServlet {

    List<Student> students = null;
    int status = 0;

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
        StudentPlacementBLRemote studentPlacementBL = factory.getStudentPlacementBL();
        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                try {
                    int placementid;
                    String placeid = request.getParameter("placementId");
                    placementid = Integer.parseInt(placeid);
                    if ((placementid > 0 && students == null) || (placementid > 0 && status == 0)) {
                        students = studentPlacementBL.getStudents(placementid);
                        getAllStudents(out, request, students);
                    } else if (students != null) {
                        getAllStudents(out, request, students);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory = null;
            } else if (request.getParameter("action").equals("start")) {
                try {
                    int placementid;
                    String placeid = request.getParameter("placementId");
                    placementid = Integer.parseInt(placeid);
                    if (status == 0) {
                        students = studentPlacementBL.placeOncePreview(placementid);
                        status = 1;
                        getAllStudents(out, request, students);
                    } else if (status == 1) {
                        getAllStudents(out, request, students);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory = null;
            } else if (request.getParameter("action").equals("save")) {
                try {
                    if (status == 1) {
                        students = studentPlacementBL.placeOnceSave();
                        status = 2;
                        getAllStudents(out, request, students);
                    } else if (status == 2) {
                        getAllStudents(out, request, students);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory = null;
            } else if (request.getParameter("action").equals("unplaceall")) {
                try {

                    students = studentPlacementBL.unplaceAll();
                    status = 0;
                    getAllStudents(out, request, students);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory = null;
            }

        } finally {            
            factory = null;
            out.close();
        }
    }

    private void getAllStudents(PrintWriter out, HttpServletRequest request, List<Student> students) {
        String rows = request.getParameter("rows");
        String page = request.getParameter("page");
        if (rows == null) {
            rows = "10";
        }
        if (page == null) {
            page = "1";
        }
        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
        if (students == null) {
            return;
        }
        try {
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
            List<Student> studentls = students.subList(start, last);

            out.print("<rows>");
            //
            out.print("<page>" + request.getParameter("page") + "</page>");
            out.print("<total>" + totalPages + "</total>");
            out.print("<records>" + totalRecords + "</records>");

            for (Student cdo : studentls) {
                out.print("<row id='" + cdo.getIdNumber() + "'>");
                out.print("<cell>" + cdo.getIdNumber() + "</cell>");
                out.print("<cell>" + cdo.getIdNumber() + "</cell>");
                out.print("<cell>" + cdo.getFirstName() + "</cell>");
                out.print("<cell>" + cdo.getMiddleName() + "</cell>");
                out.print("<cell>" + cdo.getLastName() + "</cell>");
                out.print("<cell>" + cdo.getFirstName() + " " + cdo.getLastName() + "</cell>");
                out.print("<cell>" + cdo.getGender() + "</cell>");
                out.print("<cell>" + cdo.getDisability() + "</cell>");
                out.print("<cell>" + cdo.getRegion() + "</cell>");
                out.print("<cell>" + cdo.getCgpa() + "</cell>");
                out.print("<cell>" + (cdo.getDepartmentId() == null ? "" : cdo.getDepartmentId().getName()) + "</cell>");
                out.print("<cell>" + (cdo.getPlacementId() == null ? "" : cdo.getPlacementId().getName()) + "</cell>");
                out.print("<cell>" + (cdo.getPlacementReason() == null ? "" : cdo.getPlacementReason()) + "</cell>");
                out.print("</row>");
            }
            out.print("</rows>");
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
