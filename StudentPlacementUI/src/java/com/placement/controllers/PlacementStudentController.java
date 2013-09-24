/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.lowagie.text.html.HtmlEncoder;
import com.placement.business.PlacementBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.entity.Department;
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
public class PlacementStudentController extends HttpServlet {

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
        StudentBLRemote studentBL = factory.getStudentBL();
        PlacementBLRemote placementBL = factory.getPlacementBL();
        try {
            //<editor-fold defaultstate="collapsed" desc="fetch all data in a placement">
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");

                try {
                    int placementid = 0;
                    String placeid = request.getParameter("placementId");
                    String idnumbersrch = request.getParameter("idnumbersrch");
                    String req = request.getParameter("req");
                    String q = request.getParameter("q");
                    if (placeid != null) {
                        try {
                            placementid = Integer.parseInt(placeid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    List<Student> students = null;

                    if (placementid > 0) {

                        if (q != null && q.trim().equals("search")) {
                            if (idnumbersrch != null && !idnumbersrch.trim().equals("")) {
                                students = studentBL.search(idnumbersrch);
                                factory.close();
                            } else if (req != null) {
                                Student s = new Student();
                                String deptsrch = request.getParameter("deptsrch");
                                String dissrch = request.getParameter("dissrch");
                                String gendersrch = request.getParameter("gendersrch");
                                String regionsrch = request.getParameter("regionsrch");
                                String opsrch = request.getParameter("opsrch");
                                String cgpasrch = request.getParameter("cgpasrch");
                                if (deptsrch != null && !deptsrch.trim().equals("") && !deptsrch.trim().equals("0")) {
                                    try {
                                        int deptid = Integer.parseInt(deptsrch);
                                        if (deptid > 0) {
                                            s.setDepartmentId(new Department(deptid));
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                s.setDisability(dissrch);
                                s.setGender(gendersrch);
                                s.setRegion(regionsrch);
                                s.setPlacementId(new Placement(placementid));
                                if (cgpasrch != null && !cgpasrch.trim().equals("")) {
                                    try {
                                        s.setCgpa(Double.parseDouble(cgpasrch));
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                int cgpabound = 0;
                                if (opsrch != null && !opsrch.trim().equals("")) {
                                    cgpabound = Integer.parseInt(opsrch);
                                }
                                students = studentBL.search(s, cgpabound);
                                factory.close();
                            }
                        } else {

                            students = placementBL.getAllStudents(placementid);
                            factory.close();
                        }
                    } else {
                        students = new ArrayList<Student>();
                    }

                    getAllStudents(out, request, students);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="update data action">
            else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/html;charset=UTF-8");

                String oper = request.getParameter("oper");
                String idnum = request.getParameter("id");
                String sex = request.getParameter("sex");
                String disability = request.getParameter("disability");
                String region = request.getParameter("region");
                String cgpa = request.getParameter("cgpa");
                String reason = request.getParameter("reason");
                if (oper.equals("edit")) {
                    try {
                        Student s = studentBL.get(HtmlEncoder.encode(idnum));
                        if (s == null) {
                            return;
                        }
                        try {
                            s.setCgpa(Double.parseDouble(cgpa));
                        } catch (NumberFormatException ex) {
                        }
                        if (region != null) {
                            s.setRegion(HtmlEncoder.encode(region));
                        }
                        if (disability != null) {
                            s.setDisability(HtmlEncoder.encode(disability));
                        }
                        if (sex != null) {
                            s.setGender(HtmlEncoder.encode(sex));
                        }
                        if (reason != null) {
                            s.setPlacementReason(HtmlEncoder.encode(reason));
                        }
                        if (studentBL.update(s)) {
                            out.print("success");
                        } else {
                            out.print("failed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    factory.close();
                } else if (oper.equals("del")) {
                    try {
                        Student s = studentBL.get(HtmlEncoder.encode(idnum));
                        s.setPlacementId(null);
                        s.setDepartmentId(null);
                        if (studentBL.update(s)) {
                            out.print("success");
                        } else {
                            out.print("failed");
                        }
                    } catch (Exception e) {
                        out.print("error");
                    }
                    factory.close();
                }
            } //</editor-fold>   
        } finally {
            studentBL = null;
            placementBL = null;
            factory.close();
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
        try {
            int pageIndex = 0;
            int pageSize = 10;

            int totalPages = 0;
            int totalRecords = students.size();
            try {
                pageIndex = Integer.parseInt(page) - 1;
                pageSize = Integer.parseInt(rows);
                if (pageSize == 0) {
                    pageSize = 10;
                }
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
                totalPages = 0;
                pageSize = 10;
                pageIndex = 0;
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
            out.print("<?xml version='1.0' encoding='utf-8'?>\n");
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
                out.print("<cell>" + cdo.getFullName() + "</cell>");
                out.print("<cell>" + cdo.getGender() + "</cell>");
                out.print("<cell>" + cdo.getDisability() + "</cell>");
                out.print("<cell>" + cdo.getRegion() + "</cell>");
                out.print("<cell>" + cdo.getCgpa() + "</cell>");
                out.print("<cell>" + (cdo.getDepartmentId() == null ? "" : cdo.getDepartmentId().getId()) + "</cell>");
                out.print("<cell>" + (cdo.getDepartmentId() == null ? "" : cdo.getDepartmentId().getCode()) + "</cell>");
                out.print("<cell>" + cdo.getPlacementId().getCode() + "</cell>");
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
