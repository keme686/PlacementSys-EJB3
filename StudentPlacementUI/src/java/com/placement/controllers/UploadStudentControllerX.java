/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.PlacementBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.entity.Choice;
import com.placement.entity.College;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.entity.Student;
import com.placement.utility.EJBWrapperFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author kemele
 */
public class UploadStudentControllerX extends HttpServlet {

    List<Student> cellDataList = new ArrayList<Student>();
    String collegeId = "";
    String colmns = "";
    String placementId = "";
    String str = "";
    String o = "";
    int totalonfile = 0;
    College college = new College();
    Placement placement = new Placement();

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        EJBWrapperFactory f = new EJBWrapperFactory();
       PlacementBLRemote placementBL = f.getPlacementBL();
       StudentBLRemote studentBL = f.getStudentBL();
        try {
            List<Student> notSaved = new ArrayList<Student>();
            int savedresult = 0;
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                if (request.getParameter("save").equals("save")) {
                    try {
                        savedresult = studentBL.saveAll(cellDataList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
                Iterator itr = items.iterator();
                File savedFile = new File("");
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        if (name.equals("columns")) {
                            colmns = value;
                        } else if (name.equals("placementId")) {
                            placementId = value;
                        }
                    } else {
                        try {
                            String itemName = item.getName();
                            Date date = new Date();
                            savedFile = new File(getServletConfig().getServletContext().getRealPath("/") + "students\\"
                                    + date.getDate() + "-" + date.getMonth() + "-" + date.getYear()
                                    + "-" + date.getHours() + "_" + date.getMinutes() + "_" + date.getSeconds()
                                    + "-" + itemName);
                            item.write(savedFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //out.print("Error: " + e.getMessage());
                        }
                    }//end of else
                }//end of while
                cellDataList = new ArrayList<Student>();
                if (!savedFile.getPath().equals("") && placementId != null && !placementId.equals("") && colmns != null && !colmns.equals("")) {
                    parseFile(savedFile, Integer.parseInt(placementId), colmns, out, placementBL);
                }

                try {
                    placement = placementBL.get(Integer.parseInt(placementId));
                    college = placement.getCollegeId();
                } catch (Exception e) {
                    college = new College();
                    placement = new Placement();
                }
            }//end of else/is multipart
            response.setContentType("text/html;charset=UTF-8");
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "        <title>Student Placement System</title>\n"
                    + "        \n"
                    + "         <link href=\"" + getServletContext().getContextPath() + "/js/jQuery/css/jquery-ui-1.8.14.custom.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                    + "        <link href=\"" + getServletContext().getContextPath() + "/js/jqGrid/css/ui.jqgrid.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                    + "        <link href=\"" + getServletContext().getContextPath() + "/css/Site.css\" rel=\"stylesheet\" type=\"text/css\" />             \n"
                    + "\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jQuery/jquery-latest.js\" type=\"text/javascript\"></script>\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jQuery/jquery.validate.js\" type=\"text/javascript\"></script>\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jQuery/jquery-ui-1.8.14.custom.min.js\" type=\"text/javascript\"></script>\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jqGrid/grid.locale-en.js\" type=\"text/javascript\"></script>\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jqGrid/jquery.jqGrid.min.js\" type=\"text/javascript\"></script>\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/jQuery/ui.tabs.closable.min.js\" type=\"text/javascript\"></script>\n"
                    + "\n"
                    + "        <script src=\"" + getServletContext().getContextPath() + "/js/admin.js\" type=\"text/javascript\"></script>\n"
                    + "\n");

            out.println(
                    "<script>\n"
                    + "            jQuery().ready(function (){\n"
                    + "                jQuery.jgrid.edit = {\n"
                    + "                    addCaption: \"New\", \n"
                    + "                    editCaption: \"Edit\", \n"
                    + "                    bSubmit: \"Save\", \n"
                    + "                    bCancel: \"Cancel\", \n"
                    + "                    processData: \"Processing...\", \n"
                    + "                    msg: { \n"
                    + "                        required:\"Field is required\", \n"
                    + "                        number:\"Please enter valid number!\", \n"
                    + "                        minValue:\"value must be greater than or equal to \", \n"
                    + "                        maxValue:\"value must be less than or equal to\"\n"
                    + "                    } \n"
                    + "                };    \n"
                    + "                $(\"#summery\").hide();");
            int i = 1;
            o = "";
            String rows = request.getParameter("rows");
            String pages = request.getParameter("page");
            if (rows == null) {
                rows = "10";
            }
            if (pages == null) {
                pages = "1";
            }
            int pageIndex = 0;
            int pageSize = 10;

            int totalPages = 0;
            int totalRecords = cellDataList.size();
            try {
                pageIndex = Integer.parseInt(pages) - 1;
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
            List<DepartmentPlacement> depts = placement.getDepartmentPlacementList();
            String deptcols = "", deptprops = "";
            for (DepartmentPlacement dd : depts) {
                deptcols += ",\'" + dd.getDepartmentId().getCode() + "\'";
                deptprops += "{name:\'" + dd.getDepartmentId().getCode() + "\',index:\'" + dd.getDepartmentId().getCode() + "\', sortable:true, editable: true, hidden:false},";
            }
            out.print("var mystr =\"<?xml version='1.0' encoding='utf-8'?><rows>");
            for (i = start; i < last; i++) {
                Student s = cellDataList.get(i);
                o += "<row id='" + i + "'>";
                o += "<cell>" + s.getIdNumber() + "</cell>";
                o += "<cell>" + s.getFirstName() + "</cell>";
                o += "<cell>" + s.getMiddleName() + "</cell>";
                o += "<cell>" + s.getLastName() + "</cell>";
                o += "<cell>" + s.getFirstName() + " " + s.getMiddleName() + " " + s.getLastName() + "</cell>";
                o += "<cell>" + s.getGender() + "</cell>";
                o += "<cell>" + s.getDisability() + "</cell>";
                o += "<cell>" + s.getRegion() + "</cell>";
                List<Choice> cch = s.getChoiceList();
                int h = 0;
                for (Choice dd : cch) {
                    o += "<cell>" + dd.getRank() + "</cell>";
                    h++;
                }
                o += "<cell>" + s.getCgpa() + "</cell>";
                o += "<cell>" + s.getPlacementId().getCode() + "</cell>";
                o += "</row>";
            }
            o += "</rows>";
            out.println(o + "\";");
            out.println(
                    "jQuery(\"#previewlist\").jqGrid({                         \n"
                    + "                        datatype: \"xmlstring\",       \n"
                    + "                        datastr: mystr, \n"
                    + "                        colNames:['Id No','fName','mname','lname','Full Name','Sex','Dissability','Region'" + deptcols + " ,'CGPA','placement' ],\n"
                    + "                        colModel:[\n"
                    + "                            {name:'idnum',index:'idnum', sortable:true, editable: true, hidden:false},\n"
                    + "                            {name:'fname',index:'fname', sortable:true, editable: true, hidden:true},\n"
                    + "                            {name:'mname',index:'mname', sortable:true, editable: true, hidden:true},\n"
                    + "                            {name:'lname',index:'lname', sortable:true, editable: true, hidden:true},\n"
                    + "                            {name:'fullname',index:'fullname', sortable:true, editable: true, hidden:false},\n"
                    + "                            {name:'sex',index:'sex', sortable:true, editable: true, hidden:false},\n"
                    + "                            {name:'diss',index:'diss', sortable:true, editable: true, hidden:false},\n"
                    + "                            {name:'region',index:'region', sortable:true, editable: true, hidden:false},\n"
                    + deptprops
                    + "                            {name:'cgpa',index:'cgpa', sortable:true, editable: true, hidden:false},\n"
                    + "                            {name:'placementid',index:'placementid', sortable:true, editable: true, hidden:false}],                           \n"
                    + "                          rowNum: 20,\n"
                    + "                        height: 'auto',    \n"
                    + "                        width: '100%', \n"
                    + "                        autowidth: true, \n"
                    + "                        page: 1,                       \n"
                    + "                        sortname: 'idnum', \n"
                    + "                        sortorder:'asc',\n"
                    + "                        loadtext: \"Loading...\",\n"
                    + "                        rowList:[20,30,50,100," + cellDataList.size() + "],\n"
                    + "                        imgpath: \"../css/images\", toolbar: [false,'bottom'], \n"
                    + "                        viewrecords: true,\n"
                    + "                        rownumbers: true,                                               \n"
                    + "                        pager: $(\"#previewpager\"),                            \n"
                    + "                        caption: \"Students Preview\"\n"
                    + "                    }); ");
            if (!isMultipart) {
                out.println("$(\"#maincontent\").hide();\n"
                        + "                    $(\"#summery\").show();");
            } else {
                out.println("$(\"#maincontent\").show();\n"
                        + "                    $(\"#summery\").hide();");
            }
            out.println("});\n"
                    + "        </script>");

            out.println(" </head>\n"
                    + "    <body>\n"
                    + "       \n"
                    + "        <div id=\"header\" class=\"ui-widget ui-widget-header ui-corner-all\" > \n"
                    + "            <div style=\"vertical-align: bottom;\">                              \n"
                    + "                <h1 style=\"text-align: center; vertical-align: middle;\">Jimma Institute of Technology</h1>               \n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "\n"
                    + "        <div id=\"maincontent\" class=\"ui-widget ui-widget-content ui-state-default ui-corner-all\" style=\"padding: 15px; margin: 10px; \">\n"
                    + "\n"
                    + "            <div class=\"ui-widget ui-widget-content ui-corner-all\" style=\"padding: 5px; margin: 10px;\">\n"
                    + "\n"
                    + "                <table border=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td>College:</td>\n"
                    + "                        <td><em>" + college.getCode() + " -- " + college.getName() + "</em></td> \n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <td>Placement:</td>\n"
                    + "                        <td><em> " + placement.getCode() + " -- " + placement.getName() + "</em> </td> \n"
                    + "                    </tr> \n"
                    + "                    <tr>\n"
                    + "                        <td>Total on File:</td>\n"
                    + "                        <td><em>" + (totalonfile) + "</em></td> \n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <td>Correctly filtered files:</td>\n"
                    + "                        <td><em>" + cellDataList.size() + "</em> </td> \n"
                    + "                    </tr> \n"
                    + "                    <tr>\n"
                    + "                        <td> \n"
                    + "                            <form name=\"saveall\" method=\"POST\"> \n"
                    + "                                <input type=\"submit\" value=\"save\" name=\"save\" class=\"ui-state-default ui-corner-all btn\" /> \n"
                    + "                            </form>                   \n"
                    + "                        </td>                         \n"
                    + "                        <td><a href=\"" + getServletContext().getContextPath() + "/index.jsp\" id=\"cancelu\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\"><span class=\"ui-icon ui-icon-cancel\"></span>Cancel</a></td> \n"
                    + "                    </tr> \n"
                    + "                </table> \n"
                    + "\n"
                    + "            </div>    \n"
                    + "\n"
                    + "            <div id=\"previewgrid\" class=\"ui-widget ui-widget-content ui-corner-all\" style=\"padding: 15px; margin: 10px; \" align=\"center\" style=\"height: auto;\"> \n"
                    + "                <table id=\"previewlist\"></table> \n"
                    + "                <div id=\"previewpager\" ></div> \n"
                    + "            </div> \n"
                    + "        </div> \n"
                    + "        <div id=\"summery\"  class=\"ui-widget ui-widget-content ui-state-default ui-corner-all\" style=\"padding: 15px; margin: 10px;\">\n"
                    + "\n"
                    + "            <div class=\"ui-widget ui-widget-content ui-corner-all\" style=\"padding: 5px; margin: 10px;\">\n"
                    + "                <h1>Summary</h1>\n"
                    + "                             \n"
                    + "                        <p>" + savedresult + " students successfully saved!</p> \n"
                    + "\n"
                    + "                   \n"
                    + "                <a href=\"" + getServletContext().getContextPath() + "/index.jsp\" id=\"saveu\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\"><span class=\"ui-icon ui-icon-home\"></span>Home</a>            \n"
                    + "            </div> \n"
                    + "\n"
                    + "        </div>\n"
                    + "       <div id=\"footer\">\n"
                    + "            Jimma University  &copy;2013, Developed by <a href=\"#\"> Kemele Muhammed Endris</a>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html>\n");

        } catch (Exception e) {
            e.printStackTrace();
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

    private void parseFile(File f, int placeid, String colmns, PrintWriter out, PlacementBLRemote placementBL) {
        try {
            FileInputStream fileInputStream = new FileInputStream(f);
            POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
            int sh = 0;

            Placement place = placementBL.get(placeid);
            List<DepartmentPlacement> depts = new ArrayList<DepartmentPlacement>();
            if (place != null) {
                depts = place.getDepartmentPlacementList();
            }
            totalonfile = 0;
            //parse between sheets
            for (sh = 0; sh < workBook.getNumberOfSheets(); sh++) {
                HSSFSheet hssfSheet = workBook.getSheetAt(sh);
                Iterator rowIterator = hssfSheet.rowIterator();
                //parse between rows in a sheet
                int a = 0;
                row:
                while (rowIterator.hasNext()) {
                    HSSFRow hssfRow = (HSSFRow) rowIterator.next();
                    if (a++ == 0) {
                        continue row;
                    }
                    totalonfile++;
                    Iterator colmniterator = hssfRow.cellIterator();
                    Student sc = new Student();
                    sc.setPlacementId(place);

                    //sc.setPlacementCode(place.getCode());
                    //sc.setCollegeId(place.getCollegeId());
                    sc.setAcademicYear(place.getAcademicYear());
                    int c = -1;
                    //parse between cells in a row
                    cell:
                    while (colmniterator.hasNext()) {
                        c++;
                        String idnum = "";
                        HSSFCell hssfCell = (HSSFCell) colmniterator.next();
                        if (sc.getChoiceList() == null) {
                            sc.setChoiceList(new ArrayList<Choice>());
                        }
                        if (c == 0) {
                            continue cell;
                        }
                        if (c == 1) {   /*  FirstName  */
                            sc.setFirstName(hssfCell.toString());
                        } else if (c == 2) {   /*  MiddleName  */
                            sc.setMiddleName(hssfCell.toString());
                        } else if (c == 3) {  /*  LastName  */
                            sc.setLastName(hssfCell.toString());
                        } else if (c == 4) { /*  sex  */
                            sc.setGender(hssfCell.toString());
                        } else if (c == 5) {    /*  IdNumber  */
                            idnum = hssfCell.toString();
                            sc.setIdNumber(idnum);
                        } else if (c == 6) {  /*  disability  */
                            if (hssfCell.toString().equals("")
                                    || hssfCell.toString().equals("NA")) {
                                sc.setDisability("NONE");
                            } else {
                                sc.setDisability(hssfCell.toString().toUpperCase());
                            }
                        } else if (c == 7) { /*  cgpa  */
                            try {
                                double d = Double.parseDouble(hssfCell.toString());
                                sc.setCgpa(d);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (c == 8) {/*  region  */
                            if (hssfCell.toString().equals("")
                                    || hssfCell.toString().equals("NA")) {
                                sc.setRegion("Other");
                            } else {
                                sc.setRegion(hssfCell.toString().toUpperCase());
                            }
                        } else {
                            if (Integer.parseInt(colmns) == 1 && c > 8) {
                                try {
                                    int col = 0;
                                    depts = place.getDepartmentPlacementList();
                                    int colsleft = depts.size();
                                    col = c - 9;
                                    if (col < colsleft) {
                                        DepartmentPlacement dt = depts.get(col);
                                        Choice ch = new Choice();
                                        ch.setDepartmentPlacementId(dt);
                                        ch.setPlacementId(place);
                                        try {
                                            if (hssfCell.toString().equals("")
                                                    || hssfCell.toString().equals("0.0")) {
                                                ch.setRank(depts.size());  //////////////////
                                            } else {
                                                double dr = Double.parseDouble(hssfCell.toString());
                                                if (dr > depts.size()) {
                                                    ch.setRank(depts.size());  ///////////////////////////////////////////////
                                                } else {
                                                    ch.setRank((int) dr);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ch.setRank(depts.size());
                                        }
                                        sc.getChoiceList().add(ch);
                                    }
                                } catch (Exception ed) {
                                    ed.printStackTrace();
                                }
                            }
                        }// last else
                    }
                    if (c == (8 + depts.size())) {

                        cellDataList.add(sc);
                    }
                } // for a row
            }// for a sheet                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
