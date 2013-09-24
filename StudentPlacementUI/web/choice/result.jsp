<%-- 
    Document   : result
    Created on : Aug 21, 2013, 11:36:20 AM
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
<style>
    input{
        padding: 2px;
        margin: 2px;                
    }   
    #resultsummary{
        font-size: larger;
        padding-left: 30px;
        margin: 10px;
    }
</style>
<script src="<%= getServletContext().getContextPath()%>/js/plcresult.js" type="text/javascript"></script>
<div  style="float: left;margin-right: 30px; padding: 20px;">
    <h1 class="ui-widget-header">Choices</h1>
    <ol id="choiceslist">
    </ol>
</div>
<div id="resultsummary">
    <table border="0">        
        <tbody>
            <tr>
                <td>Department Placed:</td>
                <td><input type="text" name="departmentplaced" id="departmentplaced" value="" size="35" disabled="disabled" /></td>
            </tr>
            <tr>
                <td>Reason:</td>
                <td><input type="text" name="placedreason" id="placedreason" value="" size="35" disabled="disabled" /></td>
            </tr>
            <tr>
                <td>Id Number:</td>
                <td><input type="text" name="idnum" id="ridnum" value="" size="20" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="fullname" id="fullname" value="" size="35" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>Gender:</td>
                <td><input type="text" name="gender" id="rgender" value="" size="20" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>CGPA:</td>
                <td><input type="text" name="cgpa" id="rcgpa" value="" size="20" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>Region:</td>
                <td><input type="text" name="region" id="rregion" value="" size="20" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>Disability:</td>
                <td><input type="text" name="disability" id="rdisability" value="" size="20" readonly="readonly" /></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>   
</div>

