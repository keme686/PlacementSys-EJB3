/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



jQuery().ready(function () {

    var naviOpt = { fillSapce: true, collapsible: false };
    $("#navigation").accordion(naviOpt);
    
    $("#displayTab").tabs(
                      { cache: true },
                      { closable: true },
                      //{ disabled: [0] },
                       {
                           ajaxOptions: {
                               cache: false,
                               error: function (xhr, status, index, anchor) {
                                   $(anchor.hash).html("Error on loading this page, please close and open it again!");
                               },
                               data: {},
                               success: function (data, textStatus) { }
                           },
                           add: function (event, ui) {
                               $("#displayTab").tabs("select", "#" + ui.panel.id);
                           }
                       });
    var value = false;
    var count = 0;
    
    function addTab(title, uri) {
        $('#displayTab ul li a').each(function (i) {
            count = count + 1;
            if (this.id == title) {
                value = true;
            }
        });

        if (!value) {
            value = false;
            if (count < 9) {
                var newTab = $("#displayTab").tabs("add", uri, title);
                $('#displayTab ul li a').eq(count).attr('id', title);
                count = 0;
            }
        }
        else {
            value = false;
            count = 0;
        }
    }
    function closeTab() {
        var index = getSelectedTabIndex();
        $("#displayTab").tabs("remove", index);
    }

    function getSelectedTabIndex() {
        return $("#displayTab").tabs('option', 'selected');
    }

    function RefershList() {
        $("#fromPeopleList").submit();
    }    
    $("#choices").click(function () {
        addTab('Choice', '/StudentPlacementUI/choice/choice.jsp');
    });
    $("#result").click(function () {
        addTab('Result', '/StudentPlacementUI/choice/result.jsp');
    });
    $("#personalinfoplacement").click(function () {
        addTab('Personal Information', '/StudentPlacementUI/choice/personalinfo.jsp');
    });
     $("#departments").click(function () {
        addTab('Departments', '/StudentPlacementUI/choice/departments.jsp');
    });
    
    $("#changepwd").click(function () {
        addTab('Change Password', '/StudentPlacementUI/account/changepwd.jsp');
    });
    addTab('Home', '/StudentPlacementUI/choice/home.jsp');
});