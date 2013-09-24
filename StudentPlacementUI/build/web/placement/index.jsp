<%-- 
    Document   : index
    Created on : Aug 5, 2013, 7:05:39 PM
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
<script src="<%= getServletContext().getContextPath()%>/js/placement.js" type="text/javascript"></script>
<div id="placementdiv" class="ui-widget ui-state-default ui-corner-all">   
    <h3 align="center">Placements</h3>
    <div id="topMenu" class="ui-widget-header ui-corner-all">    
        <table>
            <tr>
                <td><a id="newplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">New<span class="ui-icon ui-icon-plusthick"></span> </a></td>
                <td><a id="editplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Edit<span class="ui-icon ui-icon-pencil"></span> </a></td>
                <td><a id="deletplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Remove<span class="ui-icon ui-icon-trash"></span> </a></td>
                <td><a id="refreshplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
            </tr>
        </table>
    </div>
    <table id="placementlist" class="scroll " cellpadding="0" cellspacing="0"></table>
    <div id="placementpager" class="scroll" style="text-align:center;"></div>
</div>

<div id="departmentsdiv" class="ui-widget ui-state-default ui-corner-all" style="margin-top: 10px;">
    <h3 id="placementname" align="center"></h3>
    <div id="topMenu" class="ui-widget-header ui-corner-all">    
        <table>
            <tr>
                <td><a id="newpdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">New<span class="ui-icon ui-icon-plusthick"></span> </a></td>
                <td><a id="editpdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Edit<span class="ui-icon ui-icon-pencil"></span> </a></td>
                <td><a id="deletpdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Remove<span class="ui-icon ui-icon-trash"></span> </a></td>
                <td><a id="refreshpdepartment" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
            </tr>
        </table>
    </div>    
</div>
<div id="pdepartmentlistdiv">
    <table id="pdepartmentlist" class="scroll"></table>
    <div id="pdepartmentpager" class="scroll" ></div>
</div>
<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>