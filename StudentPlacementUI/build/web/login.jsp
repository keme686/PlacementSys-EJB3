<%-- 
    Document   : index
    Created on : Aug 5, 2013, 7:08:26 PM
    Author     : kemele
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="js/jQuery/css/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/Site.css" rel="stylesheet" type="text/css" />     

        <script src="js/jQuery/jquery-latest.js" type="text/javascript"></script>
        <script src="js/jQuery/jquery.validate.js" type="text/javascript"></script>
        <script src="js/jQuery/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>

        <script src="js/login.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            session.invalidate();
        %>

        <div id="banner"  class="ui-widget ui-widget-header ui-corner-all">
            <div style="text-align: center; vertical-align: middle; padding-top: 25px;width: 75%;float: left">                              
                <h1>Jimma University</h1>                               
            </div> 
        </div>
        <div id="main">
            <div style="margin: 30px 0px 0px 30px">          
                <div id="formContainer" class="ui-widget ui-widget-header ui-corner-all">    

                    <form action="" method="POST" id="loginform">
                        <table>                               
                            <tr style="height: 17px">
                                <td>
                                    <label for="username">Username:</label>
                                    <input type="text" name="username" class="textboxes required" id="username" />
                                </td>
                            </tr>
                            <tr style="height: 48px;">
                                <td>
                                    <label for="password">Password:</label>
                                    <input type="password" name="password" class="textboxes required" id="password"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="<%=getServletContext().getContextPath()%>/account/forgetPassword.jsp">Forget Password?</a>
                                </td>
                            </tr>
                            <tr style="height: 37px">
                                <td>
                                    <input type="submit" value="Login" class="fm-button ui-state-default ui-corner-all fm-button-icon-left" style="float:right; width: 103px; height: 31px; margin-left: 45px;
                                           margin-top: 4px;  cursor:pointer; " title="Login" id="loginbtn" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="validation-summary-errors" id="errormsg"></span>                                            
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
