<%-- 
    Document   : forgetPassword
    Created on : Sep 13, 2013, 9:52:02 AM
    Author     : kemele
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forget Password</title>
        <link href="<%=getServletContext().getContextPath()%>/js/jQuery/css/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
        <link href="<%=getServletContext().getContextPath()%>/css/Site.css" rel="stylesheet" type="text/css" />     

        <script src="<%=getServletContext().getContextPath()%>/js/jQuery/jquery-latest.js" type="text/javascript"></script>
        <script src="<%=getServletContext().getContextPath()%>/js/jQuery/jquery.validate.js" type="text/javascript"></script>
        <script src="<%=getServletContext().getContextPath()%>/js/jQuery/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>

        <script src="<%=getServletContext().getContextPath()%>/js/forgetpass.js" type="text/javascript"></script>


    </head>
    <body>
        <%
            session.invalidate();
        %>

        <div id="banner"  class="ui-widget ui-widget-header ui-corner-all">
            <div style="text-align: center; vertical-align: middle; padding-top: 25px;width: 75%;float: left">                              
                <h1>Jimma University</h1>                               
            </div>
            <div style="float: right"><a style="text-decoration: none; color: #e17009" href="<%= getServletContext().getContextPath() %>/login.jsp">Login</a></div>
        </div>
        <div id="main">
            <div style="margin: 30px 0px 0px 30px">          
                <div id="formContainer" class="ui-widget ui-widget-header ui-corner-all">    

                    <form action="" method="POST" id="forgetpassform">
                        <table border="0" cellpadding="2">            
                            <tbody>
                                <tr>
                                    <td><label for="idnum">ID Number:</label> </td>
                                    <td><input type="text" name="idnum" id="idnum" class="textboxes required" /> </td>
                                </tr>
                                <tr>
                                    <td><label for="email">Email:</label> </td>
                                    <td><input type="email" name="email" id="email" class="textboxes required"/></td>
                                </tr>        
                                <tr>
                                    <td></td>
                                    <td><span class="validation-summary-errors" id="errormsg"></span></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="submit" value="Submit" class="fm-button ui-state-default ui-corner-all fm-button-icon-left" style="float:right; width: 103px; height: 31px; margin-left: 45px;
                                               margin-top: 4px;  cursor:pointer; " title="Submit" id="submitbtn" />
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
            <div id="msgdlg" title="Message" style="width: 100px; height: 100px;">
                <p id="msg"></p>
            </div>
    </body>
</html>
