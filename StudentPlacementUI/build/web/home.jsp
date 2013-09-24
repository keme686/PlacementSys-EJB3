<%-- 
    Document   : home
    Created on : Aug 28, 2013, 10:40:13 PM
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
<h1>Welcome <%= session.getAttribute("name")%> :)    </h1>