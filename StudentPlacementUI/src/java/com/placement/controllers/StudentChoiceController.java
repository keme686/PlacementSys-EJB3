/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.ChoiceBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.entity.Choice;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Student;
import com.placement.utility.EJBWrapperFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemele
 */
public class StudentChoiceController extends HttpServlet {

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
        ChoiceBLRemote choiceBL = factory.getChoiceBL();
        response.setContentType("text/xml;charset=UTF-8");
        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
        try {
            if (request.getParameter("action").equals("fetchData")) {
                String idnum = (String) request.getSession().getAttribute("user");
                Student student = studentBL.get(idnum);
                List<Choice> choices = studentBL.getChoices(idnum);
                if (choices != null && choices.size() > 0) {
                    Collections.sort(choices, new Comparator<Choice>() {
                        @Override
                        public int compare(Choice c1, Choice c2) {
                            if (c1.getRank() < c2.getRank()) {
                                return -1;
                            } else if (c1.getRank() > c2.getRank()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    out.println("<departments>");
                    for (Choice c : choices) {
                        DepartmentPlacement dp = c.getDepartmentPlacementId();
                        out.println("<department>");
                        out.println("<id>" + dp.getId() + "</id>");
                        out.println("<code>" + dp.getDepartmentId().getCode() + "</code>");
                        out.println("<name>" + dp.getDepartmentId().getName() + "</name>");
                        out.println("</department>");
                    }
                    out.println("</departments>");
                } else {
                    List<DepartmentPlacement> depts = student.getPlacementId().getDepartmentPlacementList();
                    if (depts != null && depts.size() > 0) {
                        out.println("<departments>");
                        for (DepartmentPlacement dp : depts) {
                            out.println("<department>");
                            out.println("<id>" + dp.getId() + "</id>");
                            out.println("<code>" + dp.getDepartmentId().getCode() + "</code>");
                            out.println("<name>" + dp.getDepartmentId().getName() + "</name>");
                            out.println("</department>");
                        }
                        out.println("</departments>");
                    }
                }
                factory.close();  
                factory=null;
            } else if (request.getParameter("action").equals("updateData")) {
                out.print("<infos>");
                out.print("<info>");
                String idnum = (String) request.getSession().getAttribute("user");
                Student student = studentBL.get(idnum);
                if (student.getDepartmentId() != null) {
                    out.print("<status>-1</status>");
                } else {
                    String id = request.getParameter("id");
                    String rank = request.getParameter("rank");
                    List<Choice> choices = studentBL.getChoices(idnum);
                    if (choices != null && choices.size() > 0) {
                        for (Choice c : choices) {
                            if (c.getDepartmentPlacementId().getId() == Integer.parseInt(id)) {
                                c.setRank(Integer.parseInt(rank));
                                c.setLastModified(new Date());
                                if (choiceBL.update(c)) {
                                    out.print("<status>1</status>");
                                } else {
                                    out.print("<status>0</status>");
                                }
                            }
                        }
                    } else {
                        Choice c = new Choice();
                        c.setDepartmentPlacementId(new DepartmentPlacement(Integer.parseInt(id)));
                        c.setIdNumber(student);
                        c.setPlacementId(student.getPlacementId());
                        c.setLastModified(new Date());
                        c.setRank(Integer.parseInt(rank));
                        if (choiceBL.save(c)) {
                            out.print("<status>1</status>");
                        } else {
                            out.print("<status>0</status>");
                        }
                    }
                }
                out.print("</info>");
                out.print("</infos>");
                factory.close();
                factory = null;
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
