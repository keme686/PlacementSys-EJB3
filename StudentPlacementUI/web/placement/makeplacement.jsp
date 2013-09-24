<%-- 
    Document   : makeplacement
    Created on : Aug 5, 2013, 7:19:02 PM
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
<script src="<%= getServletContext().getContextPath()%>/js/makeplacement.js" type="text/javascript"></script>
<div id="selectplacement" class="ui-widget ui-state-default ui-corner-all">
    <table>
        <tr>
            <td><label for="placementcode">Select Placement: </label></td>
            <td>
                <select id="placementcode" name="placementcode" style="width: 200px;">
                    <option value="">-Select-</option>   
                </select>
            </td>
        </tr>            
    </table>
</div>
<div id="makeplacementstudentsdiv" class="ui-widget ui-state-default ui-corner-all" style="display: none;">   
    <h3 align="center">Make Placement</h3>

    <div id="topMenu" class="ui-widget-header ui-corner-all">    
        <table>
            <tr>
                <td><a id="startplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Process<span class="ui-icon ui-icon-plusthick"></span> </a></td>
                <td><a id="saveplacementresult" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Save<span class="ui-icon ui-icon-pencil"></span> </a></td>
                <td><a id="unplaceall" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Unplace All<span class="ui-icon ui-icon-trash"></span> </a></td>
                <td><a id="refreshmakeplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Refresh<span class="ui-icon ui-icon-refresh"></span> </a></td>            
                <td><a id="togglesearchmakeplacement" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Show Search<span class="ui-icon ui-icon-search"></span></a></td>            
                <td><a id="exportplacementresult" class="fm-button ui-state-default ui-corner-all fm-button-icon-left">Export<span class="ui-icon ui-button-icon-primary ui-icon-zoomin"></span> </a></td>                            
            </tr>
        </table>
    </div>   
</div>
<table id="makeplacementstudentlist" class="scroll " cellpadding="0" cellspacing="0"></table>
<div id="makeplacementstudentpager" class="scroll" style="text-align:center;"></div>
<div id="msgdlg" title="Message" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>