/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



jQuery().ready(function () {

//$.extend($.jgrid.ajaxOptions, { async: true });
    var naviOpt = { fillSapce: true, collapsible: true };
    $("#navigation").accordion(naviOpt);

    $("#displayTab").tabs(
                      { cache: false },
                      { closable: true },
                      { async: true },
                      { disabled: [0] },
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

    $("#rule").click(function () {
        addTab('Rules', '/StudentPlacementUI/placement/rule.jsp');
    });
    $("#pstudents").click(function () {
        addTab('Placement Students', '/StudentPlacementUI/placement/students.jsp');
    });
     $("#placement").click(function () {
        addTab('Placements', '/StudentPlacementUI/placement/index.jsp');
    });
    $("#pschoices").click(function () {
        addTab('Choices', '/StudentPlacementUI/placement/choice.jsp');
    });
     $("#makePlacement").click(function () {
        addTab('Placement Process', '/StudentPlacementUI/placement/makeplacement.jsp');
    });
    
    $("#college").click(function () {
        addTab('Colleges', '/StudentPlacementUI/settings/college.jsp');
    });    
    $("#department").click(function () {
        addTab('Departments', '/StudentPlacementUI/settings/department.jsp');
    });
    $("#usermgt").click(function () {
        addTab('User Mgt', '/StudentPlacementUI/settings/users.jsp');
    });
    
    $("#student").click(function () {
        addTab('Students', '/StudentPlacementUI/student/index.jsp');
    });
    $("#upload").click(function () {
        addTab('Upload Students', '/StudentPlacementUI/student/uploadstudents.jsp');
    });
    $("#secretcode").click(function () {
        addTab('Generate Password', '/StudentPlacementUI/student/secretcode.jsp');
    });
    
    $("#changepwd").click(function () {
        addTab('Change Password', '/StudentPlacementUI/account/changepwd.jsp');
    });
    
     addTab('Home', '/StudentPlacementUI/home.jsp');
});