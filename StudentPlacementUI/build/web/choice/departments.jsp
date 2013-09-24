<%-- 
    Document   : departments
    Created on : Aug 21, 2013, 9:04:41 AM
    Author     : kemele
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
            if(session.isNew() || session.getAttribute("user") == null){                
                response.sendRedirect(getServletContext().getContextPath()+"/login.jsp");
                return;
            }
            %>
<script src="<%= getServletContext().getContextPath() %>/js/placementdepartments.js" type="text/javascript"></script>

<div id="deptsdiv" class="ui-widget-content ui-corner-all">    
    <h3>Departments for choice</h3>

<table id="departmentlist" class="scroll " cellpadding="0" cellspacing="0"></table>
<div id="departmentpager" class="scroll" style="text-align:center;"></div>
</div>
<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>

