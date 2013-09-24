/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.controllers;

import com.placement.business.PlacementBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.business.UploadStudentBLRemote;
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
public class UploadController extends HttpServlet {
   
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        EJBWrapperFactory f = new EJBWrapperFactory();
        PlacementBLRemote placementBL = f.getPlacementBL();        
        UploadStudentBLRemote uploadStudentBL = (UploadStudentBLRemote) f.getBusiness("UploadStudentBL", "UploadStudentBLRemote?stateful");
        try {            
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                if (request.getParameter("action") != null && request.getParameter("action").equals("fetchData")) {
                    List<Student> students = uploadStudentBL.getStudents();                    
                    getAllStudents(out, request, students);
                } else if (request.getParameter("action") != null && request.getParameter("action").equals("updateData")) {
                } else if (request.getParameter("action") != null && request.getParameter("action").equals("fetchChoice")) {
                    String idnum = request.getParameter("idnum");
                    Student s = uploadStudentBL.get(idnum);
                    String rows = request.getParameter("rows");
                    String page = request.getParameter("page");
                    List<Choice> studentChoice = s.getChoiceList();
                    try {

                        int pageIndex = 0;
                        int pageSize = 10;

                        int totalPages = 0;
                        int totalRecords = studentChoice.size();
                        try {
                            if (page == null) {
                                pageIndex = 0;
                            } else {
                                pageIndex = Integer.parseInt(page) - 1;
                            }
                            if (rows == null) {
                                pageSize = 10;
                            } else {
                                pageSize = Integer.parseInt(rows);
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
                        List<Choice> departmentls = studentChoice.subList(start, last);
                        out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                        out.print("<rows>");

                        out.print("<page>" + request.getParameter("page") + "</page>");

                        out.print("<total>" + totalPages + "</total>");
                        out.print("<records>" + totalRecords + "</records>");

                        for (Choice cdo : departmentls) {
                            out.print("<row id='" + cdo.getId() + "'>");
                            out.print("<cell>" + cdo.getId() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentPlacementId().getDepartmentId().getCode() + "</cell>");
                            out.print("<cell>" + cdo.getDepartmentPlacementId().getDepartmentId().getName() + "</cell>");
                            out.print("<cell>" + cdo.getRank() + "</cell>");
                            out.print("</row>");
                        }
                        out.print("</rows>");
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
                    uploadStudentBL.setStudentsList(cellDataList);
                }                
                response.setContentType("text/xml;charset=UTF-8");
                out.print("<?xml version='1.0' encoding='utf-8'?>\n");
                out.print("<infos>");
                out.print("<info>");
                out.print(cellDataList.size() + " students information found!");
                out.print("</info>");
                out.print("</infos>");
                try {
                    placement = placementBL.get(Integer.parseInt(placementId));
                    college = placement.getCollegeId();
                } catch (Exception e) {
                    college = new College();
                    placement = new Placement();
                }
            }//end of else/is multipart                      
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

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
                out.print("<cell>" + cdo.getFirstName() + " " + cdo.getLastName() + "</cell>");
                out.print("<cell>" + cdo.getGender() + "</cell>");
                out.print("<cell>" + cdo.getDisability() + "</cell>");
                out.print("<cell>" + cdo.getRegion() + "</cell>");
                out.print("<cell>-</cell>");
                out.print("<cell>" + cdo.getCgpa() + "</cell>");
                out.print("<cell>-</cell>");
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
