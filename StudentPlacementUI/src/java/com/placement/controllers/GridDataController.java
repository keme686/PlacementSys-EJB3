/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.CollegeBLRemote;
import com.placement.business.DepartmentBLRemote;
import com.placement.business.DepartmentPlacementBLRemote;
import com.placement.business.PlacementBLRemote;
import com.placement.business.RuleBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.entity.College;
import com.placement.entity.Department;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
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
public class GridDataController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates

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
        CollegeBLRemote collegeBL = factory.getCollegeBL();
        PlacementBLRemote placementBL = factory.getPlacementBL();
        DepartmentBLRemote departmentBL = factory.getDepartmentBL();
        DepartmentPlacementBLRemote placementDepartmentBL = factory.getDepartmentPlacementBL();
        try {
            String q = request.getParameter("q");
            String dataw = request.getParameter("data");
            String action = request.getParameter("action");
            String placementId = request.getParameter("placementId");

            if (q != null && dataw != null && action != null) {
                if (q.equals("data") && placementId != null && dataw.equals("departments") && action.equals("uploadheader")) {
                    response.setContentType("text/xml;charset=UTF-8");
                    Placement p = placementBL.get(Integer.parseInt(placementId));
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<fileheadings>");
                    if (p != null) {
                        out.print("<fileheading>");
                        out.print("<heading>No,- First name - Middle Name - Last Name - Gender - ID Number - Disability - CGPA -  Region - Staff Child"
                                + "</heading><type>0</type>");
                        out.print("</fileheading>");
                        List<DepartmentPlacement> depts = p.getDepartmentPlacementList();
                        if (depts != null && depts.size() > 0) {
                            out.print("<fileheading>");
                            out.print("<heading>No,- First name - Middle Name - Last Name - Gender - ID Number - Disability - CGPA -  Region - Staff Child");
                            for (int j = 0; j < depts.size(); j++) {
                                out.print(" - " + depts.get(j).getDepartmentId().getCode());
                            }
                            out.print("</heading><type>1</type>");
                            out.print("</fileheading>");
                        }

                    }
                    out.print("</fileheadings>");
                } else //<editor-fold defaultstate="collapsed" desc="get all colleges with frid <select></select>">
                if (q.equals("editgrid") && dataw.equals("college") && action.equals("all")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select> ";//"<select><option value=\'\'>Select a College</option>";                     
                    List<College> colleges = collegeBL.getAll();
                    if (colleges != null) {
                        for (College c : colleges) {
                            data += " <option value=\'" + c.getId() + "\'>" + c.getName() + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all placementdepartments for grid <select></select>">
                else if (q.equals("editgrid") && dataw.equals("placementdepartment") && action.equals("all")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select><option value=\'\'>Select a Department</option>";
                    int plcid = 0;
                    if (placementId != null) {
                        try {
                            plcid = Integer.parseInt(placementId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    List<DepartmentPlacement> depts = placementDepartmentBL.getAll(plcid);
                    if (depts != null) {
                        for (DepartmentPlacement c : depts) {
                            data += " <option value=\'" + c.getId() + "\'>" + c.getDepartmentId().getName() + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all departments for grid <select></select>">
                else if (q.equals("editgrid") && placementId != null && dataw.equals("department") && action.equals("plcall")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select><option value=\'\'>Select a Department</option>";
                    List<DepartmentPlacement> depts = placementDepartmentBL.getAll();
                    if (depts != null) {
                        for (DepartmentPlacement c : depts) {
                            data += " <option value=\'" + c.getDepartmentId().getId() + "\'>" + c.getDepartmentId().getName() + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get departments in a placement for grid <select></select>">
                else if (q.equals("editgrid") && dataw.equals("department") && action.equals("all")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select><option value=\'\'>Select a Department</option>";
                    List<Department> depts = departmentBL.getAll();
                    if (depts != null) {
                        for (Department c : depts) {
                            data += " <option value=\'" + c.getId() + "\'>" + c.getName() + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all placements for grid <select></select>">
                else if (q.equals("editgrid") && dataw.equals("placement") && action.equals("all")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select><option value=\'\'>Select a Placement</option>";
                    List<Placement> depts = placementBL.getAll();
                    if (depts != null) {
                        for (Placement c : depts) {
                            data += " <option value=\'" + c.getId() + "\'>" + c.getCode() + "-" + c.getName() + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all rules for grid <select></select>">
                else if (q.equals("editgrid") && dataw.equals("rule") && action.equals("all")) {
                    response.setContentType("text/html;charset=UTF-8");
                    String data = "<select><option value=\'\'>Select a rule</option>";
                    RuleBLRemote ruleBL = factory.getRuleBL();
                    List<Rule> depts = ruleBL.getAll();
                    if (depts != null) {
                        for (Rule c : depts) {
                            data += " <option value=\'" + c.getId() + "\'>"
                                    + " CGPA-" + (c.getTopPercentage())
                                    + "%, Sex-" + (c.getFemalePercentage())
                                    + "%, Region-" + (c.getRegionPercentage())
                                    + "%, Disability-" + (c.getDisabilityPercentage())
                                    + "%, CGPA Cut Point-" + (c.getCutPoint())
                                    + " </option>";
                        }
                    }
                    data += "</select>";
                    out.println(data);
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all placement id and codename in XML">
                else if (q.equals("data") && dataw.equals("placement") && action.equals("all")) {
                    response.setContentType("text/xml;charset=UTF-8");
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<placements>");
                    List<Placement> placements = placementBL.getAll();
                    if (placements != null) {
                        for (Placement p : placements) {
                            out.print("<placement>");
                            out.print("<codename>" + p.getCode() + " " + p.getName() + "</codename>"
                                    + " <id>" + p.getId() + "</id>");
                            out.print("</placement>");
                        }
                    }
                    out.print("</placements>");
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all college id and codename  in XML">
                else if (q.equals("data") && dataw.equals("college") && action.equals("all")) {
                    response.setContentType("text/xml;charset=UTF-8");
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<colleges>");
                    List<College> Colleges = collegeBL.getAll();
                    if (Colleges != null) {
                        for (College p : Colleges) {
                            out.print("<college>");
                            out.print("<codename>" + p.getCode() + " " + p.getName() + "</codename>"
                                    + " <id>" + p.getId() + "</id>");
                            out.print("</college>");
                        }
                    }
                    out.print("</colleges>");
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all departments id and codename  in XML">
                else if (q.equals("data") && dataw.equals("department") && action.equals("all")) {
                    response.setContentType("text/xml;charset=UTF-8");
                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<departments>");
                    List<Department> Departments = departmentBL.getAll();
                    if (Departments != null) {
                        for (Department p : Departments) {
                            out.print("<department>");
                            out.print("<codename>" + p.getCode() + " " + p.getName() + "</codename>"
                                    + " <id>" + p.getId() + "</id>");
                            out.print("</department>");
                        }
                    }
                    out.print("</departments>");
                    factory.close();
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="get all departments for placement in a placementId, id and codename  in XML">
                else if (q.equals("data") && dataw.equals("departmentsforplacement") && action.equals("all")) {
                    response.setContentType("text/xml;charset=UTF-8");

                    out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                    out.print("<departments>");
                    String placementid = request.getParameter("placementId");

                    List<DepartmentPlacement> Departmenttoplaceins = new ArrayList<DepartmentPlacement>();
                    if (placementid != null) {
                        Placement placementDO = placementBL.get(Integer.parseInt(placementid));
                        Departmenttoplaceins = placementDO.getDepartmentPlacementList();
                    }
                    if (Departmenttoplaceins != null) {
                        for (DepartmentPlacement p : Departmenttoplaceins) {
                            out.print("<department>");
                            out.print("<codename>" + p.getDepartmentId().getCode() + " " + p.getDepartmentId().getName() + "</codename>"
                                    + " <id>" + p.getDepartmentId().getId() + "</id>");
                            out.print("</department>");
                        }
                    }
                    out.print("</departments>");
                }
                factory.close();
                //</editor-fold>
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            placementBL = null;
            collegeBL = null;
            departmentBL = null;
            placementBL = null;
            factory.close();
            factory = null;
            placementDepartmentBL = null;
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
