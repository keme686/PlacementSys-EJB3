<%-- 
    Document   : changepwd
    Created on : Aug 5, 2013, 7:02:07 PM
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
    #changepwddiv{
        font-size: larger;
        padding-left: 30px;
        margin: 10px;
    }
</style>
<script src="<%= getServletContext().getContextPath()%>/js/jQuery/jquery.validate.js" type="text/javascript"></script>
<script src="<%= getServletContext().getContextPath()%>/js/changepwd.js" type="text/javascript"></script>
<div id="changepwddiv">
    <form action="" method="POST" id="changepwdform">
        <span class="validation-summary-errors" id="errormsg"></span>   
        <table border="0" cellspacing="1" cellpadding="5">   
            <tbody>
                <tr>
                    <td>Current Password:</td>
                    <td><input type="password" name="currentpwd" id="currentpwd" class="textboxes required" value="" /></td>
                </tr>
                <tr>
                    <td>New Password:</td>
                    <td><input type="password" name="newpwd" id="newpwd" class="textboxes required" value="" /></td>
                </tr>
                <tr>
                    <td>Repeat New Password:</td>
                    <td><input type="password" name="repeatpwd" id="repeatpwd" class="textboxes required" value="" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Update" class="fm-button ui-state-default ui-corner-all fm-button-icon-left" style="float:right; width: 103px; height: 31px; margin-left: 45px;
                               margin-top: 4px;  cursor:pointer; " title="Login" id="changepwdbtn" />
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>