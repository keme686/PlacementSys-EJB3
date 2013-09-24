/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.RuleBLRemote;
import com.placement.entity.Rule;
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
public class RuleController extends HttpServlet {

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
        RuleBLRemote ruleBL = factory.getRuleBL();
        try {

            if (request.getParameter("action").equals("fetchData")) {
                response.setContentType("text/xml;charset=UTF-8");
                List<Rule> rules = new ArrayList<Rule>();
                String rows = request.getParameter("rows");
                String page = request.getParameter("page");
                if (rows == null) {
                    rows = "5";
                }
                if (page == null) {
                    page = "1";
                }
                try {
                    rules = ruleBL.getAll();
                    int pageIndex = 0;
                    int pageSize = 10;

                    int totalPages = 0;
                    int totalRecords = 0;
                    if (rules != null) {
                        totalRecords = rules.size();
                    }
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
                    List<Rule> rls = rules.subList(start, last);
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<rows>");

                    out.print("<page>" + page + "</page>");

                    out.print("<total>" + totalPages + "</total>");
                    out.print("<records>" + totalRecords + "</records>");

                    for (Rule r : rls) {
                        out.print("<row id='" + r.getId() + "'>");
                        out.print("<cell>" + r.getId() + "</cell>");
                        out.print("<cell>" + (r.getTopPercentage()) + "</cell>");
                        out.print("<cell>" + (r.getFemalePercentage()) + "</cell>");
                        out.print("<cell>" + (r.getDisabilityPercentage()) + "</cell>");
                        out.print("<cell>" + (r.getRegionPercentage()) + "</cell>");
                        out.print("<cell>" + (r.getCutPoint()) + "</cell>");
                        out.print("</row>");
                    }
                    out.print("</rows>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                factory.close();                
            } else if (request.getParameter("action").equals("updateData")) {
                response.setContentType("text/xml;charset=UTF-8");
                String oper = request.getParameter("oper");

                String cgpa = request.getParameter("cgpa");
                String sex = request.getParameter("sex");
                String disability = request.getParameter("disability");
                String region = request.getParameter("region");
                String cutpoint = request.getParameter("cutpoint");
                String id = request.getParameter("id");

                Rule c = new Rule();

                if (oper.equals("del") && id != null) {
                    try {
                        if (ruleBL.delete(Integer.parseInt(id))) {
                            out.print("<info>success</info>");
                        } else {
                            out.print("<info>failed</info>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (cgpa != null) {
                            c.setTopPercentage(Double.parseDouble(cgpa));
                        }
                        if (disability != null) {
                            c.setDisabilityPercentage(Double.parseDouble(disability));
                        }
                        if (region != null) {
                            c.setRegionPercentage(Double.parseDouble(region));
                        }
                        if (sex != null) {
                            c.setFemalePercentage(Double.parseDouble(sex));
                        }
                        if (cutpoint != null) {
                            c.setCutPoint(Double.parseDouble(cutpoint));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (oper.equals("edit")) {
                        try {
                            if (id != null) {
                                c.setId(Integer.parseInt(id));
                                if (ruleBL.update(c)) {
                                    out.print("<info>success</info>");
                                } else {
                                    out.print("<info>failed</info>");
                                }
                            } else {
                                out.print("<info>Invalid id</info>");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (oper.equals("add")) {
                        try {
                            if (ruleBL.save(c)) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
