<%-- 
    Document   : choice
    Created on : Aug 21, 2013, 10:34:22 AM
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
<style>
    em{
        color: red;
    }
    #deptstochooselist {  margin: 0;  width: 60%; padding: 20px; margin-left: 10px;}
    #deptstochooselist li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
    #deptstochooselist li span { position: absolute; margin-left: -1.3em; }
</style>
<script src="<%= getServletContext().getContextPath() %>/js/choice.js" type="text/javascript"></script>
<div class="ui-widget ui-widget-content ui-corner-all">
    <h1>Please rearange departments. </h1>
    <em>Note: The top name is the first rank</em>
    <ol id="deptstochooselist">

    </ol>
    <input type="submit" value="Update" class="fm-button ui-state-default ui-corner-all fm-button-icon-left" style="width: 103px; height: 31px; margin-left: 45px;
           margin-top: 4px;  cursor:pointer; " title="Update" id="updatechoice" />
</div>

<div id="msgdlg" title="Dialog" style="width: 100px; height: 100px;">
    <p id="msg"></p>
</div>




<!--

<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 1</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 2</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 3</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 4</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 5</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 6</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 7</li>


<style>
    h1 { padding: .2em; margin: 0; }
    #products { float:left; width: 500px; margin-right: 2em; }
    #cart { width: 200px; float: left; margin-top: 1em; }
    /* style the list to maximize the droppable hitarea */
    #cart ol { margin: 0; padding: 1em 0 1em 3em; }
</style>
<script>
    $(function() {
        //$("#catalog").accordion();
        $("#catalog li").draggable({
            appendTo: "body",
            helper:"clone"
        });
        $("#cart ol").droppable({
            activeClass: "ui-state-default",
            hoverClass: "ui-state-hover",
            accept: ":not(.ui-sortable-helper)",
            drop: function(event, ui) {
                $(this).find(".placeholder").remove();
                $("<li></li>").text(ui.draggable.text()).appendTo(this);
            }
        }).sortable({
            items: "li:not(.placeholder)",
            sort: function() {
// gets added unintentionally by droppable interacting with sortable
// using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
                $(this).removeClass("ui-state-default");
            }
        });
    });
</script>

<div id="products">
    <h1 class="ui-widget-header">Products</h1>
    <div id="catalog">
        <h2><a href="#">T-Shirts</a></h2>
        <div>
            <ul>
                <li>Lolcat Shirt</li>
                <li>Cheezeburger Shirt</li>
                <li>Buckit Shirt</li>
            </ul>
        </div>
        <h2><a href="#">Bags</a></h2>
        <div>
            <ul>
                <li>Zebra Striped</li>
                <li>Black Leather</li>
                <li>Alligator Leather</li>
            </ul>
        </div>
        <h2><a href="#">Gadgets</a></h2>
        <div>
            <ul>
                <li>iPhone</li>
                <li>iPod</li>
                <li>iPad</li>
            </ul>
        </div>
    </div>
</div>
<div id="cart" style="float: right;">
    <h1 class="ui-widget-header">Shopping Cart</h1>
    <div class="ui-widget-content">
        <ol>
            <li class="placeholder">Add your items here</li>
        </ol>
    </div>
</div>
-->
