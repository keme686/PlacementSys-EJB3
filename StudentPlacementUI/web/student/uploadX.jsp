<%-- 
    Document   : upload
    Created on : Aug 5, 2013, 7:04:24 PM
    Author     : kemele
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.isNew() || session.getAttribute("user") == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/login.jsp");
        return;
    }
%>
<script type="text/javascript" src="<%= getServletContext().getContextPath()%>/js/upload.js"></script>
<script type="text/javascript" src="<%= getServletContext().getContextPath()%>/js/jQuery/jquery.form.js"></script>
<div id="uploadform" style="padding: 20px;padding-left: 50px;" class="ui-widget ui-widget-header ui-state-default ui-corner-all">

    <form name="uploadForm" id="uploadForm" action="<%= getServletContext().getContextPath()%>/UploadStudentController" method="POST" enctype="multipart/form-data"> 
        <table border="0">       
            <tbody>
                <tr>
                    <td>Placement:</td>
                    <td>
                        <select name="placementId" id="placementId">
                            <option></option>
                        </select>
                    </td><td></td>
                </tr>                
                <tr>
                    <td>Columns:</td>
                    <td>
                        <select name="columns" id="columns">
                            <option>No,- ID Number - First name - middle name - last name - gender - cgpa - region - staff relation - disability</option>
                        </select>
                    </td><td class="smallselect"></td>
                </tr>
                <tr>
                    <td>File<em>(.xls)</em>:</td>
                    <td><input type="file" name="file" id="file" value="" /></td>
                </tr>
                <tr>
                    <td></td>              

                    <td colspan="2" align="right">
                        <input type="submit" value="Upload" name="uploadbtn" id="uploadbtn" />                       
                    </td>
                </tr>
            </tbody>
        </table> 
    </form>
</div>
<div id="dataDisplay"></div>
<div id="uploadpreview" class="ui-widget ui-widget-header ui-state-default ui-corner-all">
    <label>Submitted data:</label>
    <div id="uploadformsummery" class="ui-widget ui-widget-header ui-state-default ui-corner-all"  style="padding: 20px;padding-left: 50px;" >
        <a href="#" id="backu" class="fm-button ui-state-default ui-corner-all fm-button-icon-left"><span class="ui-icon ui-icon-arrow-1-nw"></span>Back</a>       
    </div>
    <div id="uploadbtnpanel" class="ui-widget ui-widget-content ui-corner-all" style="padding: 10px;">
        <table border="0">
            <tr>                    
                <td><a href="#" id="saveu" class="fm-button ui-state-default ui-corner-all fm-button-icon-left"><span class="ui-icon ui-icon-disk"></span>Save</a></td>
                <td><a href="#" id="cancelu" class="fm-button ui-state-default ui-corner-all fm-button-icon-left"><span class="ui-icon ui-icon-cancel"></span>Cancel</a></td>
            </tr>
        </table>
    </div>
    <div id="uploadstudentslist">
        <table id="previewlist" class="scroll"></table>
        <div id="previewpager" class="scroll" ></div>
    </div>
</div>
<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>