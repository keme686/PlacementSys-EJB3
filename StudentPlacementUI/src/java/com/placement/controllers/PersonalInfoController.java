/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.lowagie.text.html.HtmlEncoder;
import com.placement.business.StudentBLRemote;
import com.placement.business.UsersBLRemote;
import com.placement.entity.Choice;
import com.placement.entity.Student;
import com.placement.exception.PlacementAppException;
import com.placement.utility.EJBWrapperFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemele
 */
public class PersonalInfoController extends HttpServlet {

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
        StudentBLRemote studentBL = factory.getStudentBL();
        try {
            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                try {
                    String idnum = (String) request.getSession().getAttribute("user");
                    Student student = studentBL.get(idnum);
                    if (student != null) {
                        out.print("<students>");
                        out.print("<student>");
                        out.print("<idnum>" + student.getIdNumber() + "</idnum>");
                        out.print("<firstName>" + student.getFirstName() + "</firstName>");
                        out.print("<middleName>" + student.getMiddleName() + "</middleName>");
                        out.print("<lastName>" + student.getLastName() + "</lastName>");
                        out.print("<gender>" + student.getGender() + "</gender>");
                        out.print("<classYear>" + student.getClassYear() + "</classYear>");
                        out.print("<semester>" + student.getSemester() + "</semester>");
                        out.print("<cgpa>" + student.getCgpa() + "</cgpa>");
                        out.print("<prepaverage>" + student.getPreparatoryAverage() + "</prepaverage>");
                        out.print("<eheeceaverage>" + student.getEheeeceAverage() + "</eheeceaverage>");
                        out.print("<eheeceregnum>" + student.getEheeceRegistrationNumber() + "</eheeceregnum>");
                        out.print("<country>" + student.getCountry() + "</country>");
                        out.print("<email>" + student.getEmail() + "</email>");
                        out.print("<region>" + student.getRegion() + "</region>");
                        out.print("<zone>" + student.getZone() + "</zone>");
                        out.print("<woreda>" + student.getWoreda() + "</woreda>");
                        out.print("<town>" + student.getTown() + "</town>");
                        out.print("<mobile>" + student.getMobileNumber() + "</mobile>");
                        out.print("<disability>" + student.getDisability() + "</disability>");
                        out.println("</student>");
                        out.println("</students>");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();               
            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String idnum = (String) request.getSession().getAttribute("user");

                String firstName = request.getParameter("firstName");
                String middleName = request.getParameter("middleName");
                String lastName = request.getParameter("lastName");
                String classYear = request.getParameter("classYear");
                String semester = request.getParameter("semester");
                String prepaverage = request.getParameter("prepaverage");
                String eheeceaverage = request.getParameter("eheeceaverage");
                String eheeceregnum = request.getParameter("eheeceregnum");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                String zone = request.getParameter("zone");
                String woreda = request.getParameter("woreda");
                String town = request.getParameter("town");
                String mobile = request.getParameter("mobile");

                Student s = studentBL.get(idnum);
                if (classYear != null && !classYear.trim().equals("")) {

                    try {
                        s.setClassYear(Integer.parseInt(classYear));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                }
                if (semester != null && !semester.trim().equals("")) {
                    try {
                        s.setSemester(Integer.parseInt(semester));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                }
                if (prepaverage != null && !prepaverage.trim().equals("")) {

                    try {
                        s.setPreparatoryAverage(Double.parseDouble(prepaverage));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                }
                if (eheeceaverage != null && !eheeceaverage.trim().equals("")) {
                    try {
                        s.setEheeeceAverage(Double.parseDouble(eheeceaverage));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                }
                if (firstName != null) {
                    s.setFirstName(HtmlEncoder.encode(firstName));
                }
                if (middleName != null) {
                    s.setMiddleName(HtmlEncoder.encode(middleName));
                }
                if (lastName != null) {
                    s.setLastName(HtmlEncoder.encode(lastName));
                }
                if (email != null) {
                    s.setEmail(HtmlEncoder.encode(email));
                }
                if (eheeceregnum != null) {
                    s.setEheeceRegistrationNumber(HtmlEncoder.encode(eheeceregnum));
                }
                if (country != null) {
                    s.setCountry(HtmlEncoder.encode(country));
                }
                if (zone != null) {
                    s.setZone(HtmlEncoder.encode(zone));
                }
                if (woreda != null) {
                    s.setWoreda(HtmlEncoder.encode(woreda));
                }
                if (town != null) {
                    s.setTown(HtmlEncoder.encode(town));
                }
                if (mobile != null) {
                    s.setMobileNumber(HtmlEncoder.encode(mobile));
                }
                try {
                    response.setContentType("text/xml;charset=UTF-8");
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<infos>");
                    out.print("<info>");
                    if (studentBL.update(s)) {
                        out.print("<status>1</status>");
                    } else {
                        out.print("<status>0</status>");
                    }
                } catch (PlacementAppException e) {
                    out.print("<status>-1</status>");
                    e.printStackTrace();
                } catch (Exception e) {
                    out.print("<status>-2/status>");
                    e.printStackTrace();
                }
                out.print("</info>");
                out.print("</infos>");
                factory.close();
                
            } else if (request.getParameter("action").equals("resultData")) {
                response.setContentType("text/xml;charset=UTF-8");
                out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                try {
                    String idnum = (String) request.getSession().getAttribute("user");
                    Student student = studentBL.get(idnum);
                    if (student != null) {
                        out.print("<students>");
                        out.print("<student>");
                        out.print("<idnum>" + student.getIdNumber() + "</idnum>");
                        out.print("<name>" + student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName() + "</name>");
                        out.print("<gender>" + student.getGender() + "</gender>");
                        out.print("<cgpa>" + student.getCgpa() + "</cgpa>");
                        out.print("<region>" + student.getRegion() + "</region>");
                        out.print("<reason>" + student.getPlacementReason() + "</reason>");
                        out.print("<disability>" + student.getDisability() + "</disability>");
                        out.print("<department>" + (student.getDepartmentId() == null ? "" : student.getDepartmentId().getName()) + "</department>");
                        out.print("<choices>");
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
                            for (Choice c : choices) {
                                out.print("<choice>");
                                out.print("<rank>" + c.getRank() + "</rank>");
                                out.print("<departmentchoice>" + c.getDepartmentPlacementId().getDepartmentId().getName() + "</departmentchoice>");
                                out.print("</choice>");
                            }
                        }
                        out.print("</choices>");
                        out.println("</student>");
                        out.println("</students>");
                    }
                    factory.close();                
                } catch (Exception e) {
                    e.printStackTrace();
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
