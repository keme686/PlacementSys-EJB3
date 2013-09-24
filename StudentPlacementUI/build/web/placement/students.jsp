<%-- 
    Document   : students
    Created on : Aug 5, 2013, 7:05:48 PM
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
<script src="<%= getServletContext().getContextPath()%>/js/placementstudents.js" type="text/javascript"></script>
<div id="placementdiv" class="ui-widget ui-state-default ui-corner-all">   
    <h3 align="center">Placements</h3>
    <div id="plctopMenu" class="ui-widget-header ui-corner-all">    
        <table>
            <tr>                
                <td><a id="refreshplacements" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
            </tr>
        </table>
    </div>
    <table id="placementlists" class="scroll " cellpadding="0" cellspacing="0"></table>
    <div id="placementpagers" class="scroll" style="text-align:center;"></div>
</div>


<div id="placementstudentsdiv" class="ui-widget ui-state-default ui-corner-all" style="display: none;">   
    <h3 align="center">Placement Students</h3>
    <!-- Search  panel  -->
    <div id="padvancedsearch" class="ui-widget ui-state-default ui-corner-all" style="float: left;display: none;width: 100%;">
        <div class="ui-widget ui-state-default ui-corner-all" style="padding: 5px; margin: 5px; float: left;">     
            <table border="0" style="float: left;">               
                <tbody>
                    <tr>
                        <td>Department:</td>
                        <td>
                            <select name="departmentsrch" id ="departmentsrch" class="largeselect">
                                <option value=""> -- All -- </option>
                            </select>
                        </td>
                        <td style="width: 10px;"></td>                        
                        <td>Disability:</td>                        
                        <td>
                            <select name="sdisability" id="disabilitysrch"  class="largeselect">                                    
                                <option value=""> -- All -- </option>
                                <option value="HEARING">Hearing</option>
                                <option value="VISION">Vision</option>
                                <option value="HANDYCAPE">Handy cape</option>
                                <option value="OTHER">other</option>
                                <option value="NONE">None</option>
                            </select>
                        </td>                        
                    </tr>
                    <tr>
                        <td>Staff Relation:</td>
                        <td>
                            <select name="staffchild" id="staffchildsrch" class="largeselect">
                                <option value=""> -- All -- </option>
                                <option value="0">Staff Child</option>
                                <option value="1">None</option>
                            </select>
                        </td>
                        <td style="width: 10px;"></td>  
                        <td>Gender:</td>
                        <td>
                            <select name="sgender" id="gendersrch" class="smallselect">
                                <option value=""> -- All -- </option>
                                <option>F</option>
                                <option>M</option>
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td>Region:</td>
                        <td>
                            <select name="sregion" id="regionsrch"  class="largeselect">
                                <option value=""> -- All -- </option>
                                <option value="AFAR">AFAR</option>
                                <option value="BENSHANGUL">BENSHANGUL</option>
                                <option value="GAMBELLA">GAMBELLA</option>
                                <option value="SOMALI">SOMALI</option>
                                <option value="0">OTHERS</option>                                      
                            </select>
                        </td>
                        <td style="width: 10px;"></td>                        
                        <td>CGPA:</td>
                        <td> 
                            <select name="op" id="opsrch" style="font-weight: bolder; font-size: 1.5em;">
                                <option value="0"> == </option>
                                <option value="1"> &gt; </option>
                                <option value="2"> &gt;= </option>
                                <option value="3"> &lt; </option>
                                <option value="4"> &lt;= </option>
                                <option value="5"> != </option>
                            </select>
                            <input type="text" name="scgpa" id="cgpasrch" value="" size="15" />
                        </td>                                                 
                    </tr>  
                    <tr>
                        <td></td>
                        <td></td>                           
                        <td></td>
                        <td></td>                        
                        <td></td>                        
                    </tr>     
                    <tr>
                        <td></td>
                        <td></td>  
                        <td></td>    
                        <td></td>                                            
                        <td align="right" style="vertical-align: bottom;"><a href="#" id="searchadv" class="fm-button ui-state-default ui-corner-all fm-button-icon-left"><span class="ui-icon ui-icon-zoomin"></span>Search</a></td>                                
                    </tr>                                      
                </tbody>
            </table>   
        </div>
        <div id="psearchbyid" class="ui-widget ui-state-default ui-corner-all" style="padding: 5px;padding-bottom: 25px; padding-top: 25px; margin: 5px; float: left;">
            <table border="0">               
                <tbody>
                    <tr>
                        <td>ID number:</td>
                        <td><input type="text" name="idnumbersrch" id ="idnumbersrch" value="" size="25" /></td>                        
                    </tr>                                   
                    <tr>
                        <td></td>
                        <td colspan="2" align="right"><a href="#" id="searchid" class="fm-button ui-state-default ui-corner-all fm-button-icon-left"><span class="ui-icon ui-icon-zoomin"></span>Search</a></td>
                    </tr>
                </tbody>
            </table>          
        </div>
    </div>  
</div>

<div id="topMenu" class="ui-widget-header ui-corner-all">    
    <table>
        <tr>            
            <td><a id="editplacementstudent" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Edit<span class="ui-icon ui-icon-pencil"></span> </a></td>
            <td><a id="delplacementstudent" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Remove<span class="ui-icon ui-icon-trash"></span> </a></td>
            <td><a id="refreshplacementstudent" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
            <td><a id="togglesearchplacementstudent" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Show Search<span class="ui-icon ui-icon-search"></span></a></td>            
            <td><a id="exportplacementstudents" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Export<span class="ui-icon ui-button-icon-primary ui-icon-zoomin"></span> </a></td>                            
        </tr>
    </table>
</div>    
<table id="placementstudentlist" class="scroll " cellpadding="0" cellspacing="0"></table>
<div id="placementstudentpager" class="scroll" style="text-align:center;"></div>
<div id="msgdlg" title="Message" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>
<div id="confirmmsgdlg"  style="width: 500px; height: 100px;">
    <p id="confirmmsg"></p>
</div>