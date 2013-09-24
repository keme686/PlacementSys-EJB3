<%-- 
    Document   : uploadstudents
    Created on : Aug 25, 2013, 2:24:40 PM
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
<script type="text/javascript" src="<%= getServletContext().getContextPath()%>/js/jQuery/jquery.form.js"></script>
<script type="text/javascript" src="<%= getServletContext().getContextPath()%>/js/uploadstudents.js"></script>

<div id="uploadform" style="padding: 20px;padding-left: 50px;" class="ui-widget ui-widget-header ui-state-default ui-corner-all">

    <form name="uploadForm" id="uploadForm" action="<%= getServletContext().getContextPath()%>/UploadController" method="POST" enctype="multipart/form-data"> 
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

    <div  id="topMenu" class="ui-widget-header ui-corner-all">
        <table border="0">
            <tr>                    
                <td><a id="newstud" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">New<span class="ui-icon ui-icon-plusthick"></span> </a></td>
                <td><a id="editstud" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Edit<span class="ui-icon ui-icon-pencil"></span> </a></td>
                <td><a id="removestud" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Remove<span class="ui-icon ui-icon-trash"></span> </a></td>
                <td><a id="refreshstud" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
                <td><a id="saveu" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Save All<span class="ui-icon ui-icon-disk"></span></a></td>
                <td><a id="cancelu" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Cancel<span class="ui-icon ui-icon-cancel"></span></a></td>
            </tr>
        </table>
    </div>
    <table id="previewlist" class="scroll"></table>
    <div id="previewpager" class="scroll" ></div>    
</div>
<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>
