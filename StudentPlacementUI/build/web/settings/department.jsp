<%-- 
    Document   : department
    Created on : Aug 5, 2013, 7:05:02 PM
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
<script src="<%= getServletContext().getContextPath()%>/js/department.js" type="text/javascript"></script>

<div id="topMenu" class="ui-widget-header ui-corner-all">    
    <table>
        <tr>
            <td><a id="newdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">New<span class="ui-icon ui-icon-plusthick"></span> </a></td>
            <td><a id="editdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Edit<span class="ui-icon ui-icon-pencil"></span> </a></td>
            <td><a id="deletdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Remove<span class="ui-icon ui-icon-trash"></span> </a></td>
            <td><a id="refreshdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
        </tr>
    </table>
</div>
<table id="departmentlist" class="scroll " cellpadding="0" cellspacing="0"></table>
<div id="departmentpager" class="scroll" style="text-align:center;"></div>

<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>

