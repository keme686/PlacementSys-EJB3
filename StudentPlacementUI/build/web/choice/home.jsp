<%-- 
    Document   : home
    Created on : Aug 19, 2013, 5:36:37 PM
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
<h1>
    Welcome <%= session.getAttribute("name")%> :)                
</h1>
<p style="font-size: larger;">As a student you can:
<ul style="font-size: larger;">
    <li>View list of departments that you can choose from for this particular placement- click on Departments menu</li>
    <li>Update personal information (note: you should enter a valid email address to get reset code in case you forget your password)</li>
    <li>Make department choice by re-arranging the departments list displayed on Choice menu in left panel</li>
    <li>Check your placement result if placement is made - click on Placement Result menu on left panel</li>
    <li>Reset your password - in Security category</li>
</ul>
</p>

