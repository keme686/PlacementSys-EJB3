/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.lowagie.text.html.HtmlEncoder;
import com.placement.business.StudentBLRemote;
import com.placement.entity.Placement;
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
public class StudentController extends HttpServlet {

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
        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                try {
                    List<Student> students = studentBL.getAll();
                    getAllStudents(out, request, students);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();
                factory = null;
            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");

                String oper = request.getParameter("oper");

                String fname = HtmlEncoder.encode(request.getParameter("fname") == null ? "" : request.getParameter("fname"));
                String mname = HtmlEncoder.encode(request.getParameter("mname") == null ? "" : request.getParameter("mname"));
                String lname = HtmlEncoder.encode(request.getParameter("lname") == null ? "" : request.getParameter("lname"));

                String idnum = HtmlEncoder.encode(request.getParameter("id") == null ? "" : request.getParameter("id"));
                String sex = HtmlEncoder.encode(request.getParameter("sex") == null ? "" : request.getParameter("sex"));
                String disability = HtmlEncoder.encode(request.getParameter("disability") == null ? "" : request.getParameter("disability"));
                String region = HtmlEncoder.encode(request.getParameter("region") == null ? "" : request.getParameter("region"));
                String cgpa = HtmlEncoder.encode(request.getParameter("cgpa") == null ? "0.0" : request.getParameter("cgpa"));

                String placementId = request.getParameter("placement");
                Student s = new Student();
                if (placementId != null && !placementId.equals(" ")) {
                    try {
                        s.setPlacementId(new Placement(Integer.parseInt(placementId.trim())));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                }
                if (oper.equals("del")) {
                    try {
                        if (studentBL.delete(idnum)) {
                            out.print("<info>success</info>");
                            System.out.println("Success");
                        } else {
                            out.print("<info>failed</info>");
                            System.out.println("Failed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    s.setCgpa(Double.parseDouble(cgpa));
                    s.setFirstName(fname);
                    s.setMiddleName(mname);
                    s.setLastName(lname);
                    s.setRegion(region);
                    s.setDisability(disability);
                    s.setIdNumber(idnum);
                    s.setGender(sex);
                    if (oper.equals("add")) {
                        try {
                            if (studentBL.save(s)) {
                                out.print("<info>success</info>");
                            } else {
                                out.print("<info>failed</info>");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (oper.equals("edit")) {
                        try {
                            if (studentBL.update(s)) {
                                out.print("<info>success</info>");
                            } else {
                                out.print("<info>failed</info>");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                factory.close();
                factory = null;
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
            int pageIndex;
            int pageSize;

            int totalPages;
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
            out.print("<page>" + page + "</page>");
            out.print("<total>" + totalPages + "</total>");
            out.print("<records>" + totalRecords + "</records>");

            for (Student cdo : studentls) {
                out.print("<row id='" + cdo.getIdNumber() + "'>");
                out.print("<cell>" + cdo.getIdNumber() + "</cell>");
                out.print("<cell>" + cdo.getIdNumber() + "</cell>");
                out.print("<cell>" + cdo.getFirstName() + "</cell>");
                out.print("<cell>" + cdo.getMiddleName() + "</cell>");
                out.print("<cell>" + cdo.getLastName() + "</cell>");
                out.print("<cell>" + cdo.getFirstName() + " " + cdo.getMiddleName() + " " + cdo.getLastName() + "</cell>");
                out.print("<cell>" + cdo.getGender() + "</cell>");
                out.print("<cell>" + cdo.getDisability() + "</cell>");
                out.print("<cell>" + cdo.getRegion() + "</cell>");
                out.print("<cell>" + cdo.getCgpa() + "</cell>");
                if (cdo.getDepartmentId() != null) {
                    out.print("<cell>" + cdo.getDepartmentId().getCode() + "</cell>");
                } else {
                    out.print("<cell>-</cell>");
                }
                if (cdo.getPlacementId() != null) {
                    out.print("<cell>" + cdo.getPlacementId().getCode() + "</cell>");
                } else {
                    out.print("<cell>-</cell>");
                }
                out.print("</row>");
            }
            out.print("</rows>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
