<%-- 
    Document   : index
    Created on : Aug 5, 2013, 1:10:57 AM
    Author     : kemele
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/errorpg.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Placement System</title>
        
         <link href="<%= getServletContext().getContextPath() %>/js/jQuery/css/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
        <link href="<%= getServletContext().getContextPath() %>/js/jqGrid/css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
        <link href="<%= getServletContext().getContextPath() %>/css/Site.css" rel="stylesheet" type="text/css" />             

        <script src="<%= getServletContext().getContextPath() %>/js/jQuery/jquery-latest.js" type="text/javascript"></script>
        <script src="<%= getServletContext().getContextPath() %>/js/jQuery/jquery.validate.js" type="text/javascript"></script>
        <script src="<%= getServletContext().getContextPath() %>/js/jQuery/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>
        <script src="<%= getServletContext().getContextPath() %>/js/jqGrid/grid.locale-en.js" type="text/javascript"></script>
        <script src="<%= getServletContext().getContextPath() %>/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="<%= getServletContext().getContextPath() %>/js/jQuery/ui.tabs.closable.min.js" type="text/javascript"></script>

        <script src="<%= getServletContext().getContextPath() %>/js/admin.js" type="text/javascript"></script>

    </head>
    <body>
        <%
            if(session.isNew() || session.getAttribute("user") == null){                
                response.sendRedirect(getServletContext().getContextPath()+"/login.jsp");
                return;
            }
            %>
        <div id="all_binder">
            <div id="banner"  class="ui-widget ui-widget-header ui-corner-all">
            <div style="text-align: center; vertical-align: middle; padding-top: 25px;width: 75%;float: left">                              
                <h1>Jimma University</h1>                               
            </div>    
                <div style="float:left;font-size: small" >Welcome <%= session.getAttribute("user") %>!</div>   
                <div style="float: right"><a style="text-decoration: none; color: #e17009" href="<%= getServletContext().getContextPath() %>/LogoutController">Logout</a></div>
            </div>            
            <div id="main">  
                <div id="cont1">
                    <div id="navigation">
                        <h2><a href="#">Placement Management</a></h2>
                        <div>                                                            
                            <a href="#" id="placement">Placements</a>                             
                            <a href="#" id="pstudents">Students</a>                                                            
                            <a href="#" id="rule">Rule</a>                              
                            <a href="#" id="makePlacement">Make Placement</a> 
                        </div>  
                        <h2><a href="#">Student Management</a></h2>
                        <div>
                            <a href="#" id="student">Students</a>   
                            <a href="#" id = "upload">Upload Record</a>                                                                                                 
                        </div>    
                        <h2><a href="#">Settings</a></h2>
                        <div>                                
                            <a href="#" id="college">Colleges</a>
                            <a href="#" id="department">Departments</a>                                          
                            <a href="#" id="usermgt">User Administration</a>                               
                        </div>  
                        <h2><a href="#">Security</a></h2>
                        <div>                                
                            <a href="#" id="changepwd">Change Password</a>  
                        </div>
                    </div>
                </div>
                <div id="cont2">
                    <div id="displayTab">                        
                        <ul>
                            <li></li>
                        </ul>
                        <div id="tabContent"></div>
                    </div>
                </div>                
            </div>            
        </div>
        <div id="footer">
            Jimma University  &copy;2013, Developed by <a href="#"> Kemele Muhammed Endris</a>
        </div>
    </div>
</body>
</html>
