<%-- 
    Document   : users
    Created on : Aug 5, 2013, 7:04:47 PM
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
<script src="<%= getServletContext().getContextPath()%>/js/users.js" type="text/javascript"></script>
<div id="usersdiv" class="ui-widget ui-state-default ui-corner-all">   
    <h3 align="center">Users</h3>
    <div id="topMenu" class="ui-widget-header ui-corner-all">    
        <table>
            <tr>
                <td><a id="newuser" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">New<span class="ui-icon ui-icon-plusthick"></span> </a></td>
                <td><a id="resetpwd" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Reset Password<span class="ui-icon ui-icon-pencil"></span> </a></td>
                <td><a id="chstatus" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Change Status<span class="ui-icon ui-icon-trash"></span> </a></td>
                <td><a id="refreshusers" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
            </tr>
        </table>
    </div>
    <table id="userslist" class="scroll " cellpadding="0" cellspacing="0"></table>
    <div id="userspager" class="scroll" style="text-align:center;"></div>
</div>
<div title="Dialog" style="width: 100px; height: 100px;display: none" id ="resetpwdformdiv">
    <p id="rstmsg"></p>    
</div>
<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>