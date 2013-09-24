/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function() {
    $("#placementstudentsdiv").hide();
    $("#placementstudentlistdiv").hide();
    //$("#padvancedsearch").hide();
    jQuery.jgrid.edit = {
        addCaption: "New",
        editCaption: "Edit",
        bSubmit: "Save",
        bCancel: "Cancel",
        processData: "Processing...",
        closeAfterAdd: true,
        closeAfterEdit: true,
        recreateForm: true,
        position: "center",
        top: '300',
        left: '400',
        width: 'auto',
        reloadAfterSubmit: true,
        modal: true,
        msg: {
            required: "Field is required",
            number: "Please enter valid number!",
            minValue: "value must be greater than or equal to ",
            maxValue: "value must be less than or equal to"
        }
    };
    var Ok = function() {
        $("#msgdlg").dialog('close');
    };
    var dial2 = {
        autoOpen: false,
        title: 'Warning',
        modal: true,
        buttons: {
            "Ok": Ok
        }
    };
    var waitdlg = {
        autoOpen: false,
        title: 'Processing....',
        modal: true
    };
    var ready = 0;
    var total = 0;
    var placed = 0;
    var choicemade = 0;
    var packagerange = 0;
    var cutpoint = 0.0;

    /*
     * ***************************************************************************
     * ******************* PLACEMENTS *******************************************
     * ***************************************************************************
     */

    var placement = "";
    var placementId = 0;

    jQuery("#placementlists").jqGrid({
        url: '/StudentPlacementUI/PlacementController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',        
        colNames: ['id', 'Code', 'Name', 'Ac. Year', 'College', 'Placement Rule ', 'Rule'],
        colModel: [
            {
                name: 'id',
                index: 'id',
                width: 50,
                hidden: true
            },
            {
                name: 'code',
                index: 'code',
                align: 'center',
                width: 30,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'name',
                index: 'name',
                align: 'left',
                width: 60,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'acyear',
                index: 'acyear',
                align: 'center',
                width: 20,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'college',
                index: 'college',
                align: 'center',
                width: 25,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=college&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'ruleinfo',
                index: 'ruleinfo',
                align: 'left',
                width: 100,
                sortable: true,
                editable: false,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'ruleId',
                index: 'ruleId',
                align: 'left',
                width: 100,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=rule&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            }
        ],
        pager: $("#placementpagers"),
        rowNum: 10,
        height: 'auto',
        width: '100%',
        autowidth: true,
        page: 1,
        sortname: 'id',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [10, 20, 30, 50],
        imgpath: "../css/images",
        caption: "Placements",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/PlacementController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id != null) {
                var selectedrowVal = jQuery("#placementlists").getRowData(id);
                placement = selectedrowVal['code'].toString() + " - " + selectedrowVal['name'].toString();
                $("#placementnames").html("Students for " + placement);
                placementId = id;
                jQuery("#placementstudentlist").setGridParam({
                    url: '/StudentPlacementUI/PlacementStudentController?q=1&action=fetchData&placementId=' + id,
                    editurl: '/StudentPlacementUI/PlacementStudentController?q=1&action=updateData'
                }).trigger("reloadGrid");
                $.get('/StudentPlacementUI/GridDataController?q=data&data=summary&action=start&placementId=' + placementId, {}, function(xml) {
                    if (xml) {
                        $('placement', xml).each(function(i) {
                            var codename = $(this).find("codename").text();
                            var id = $(this).find("id").text();
                            total = $(this).find("total").text();
                            placed = $(this).find("placed").text();
                            choicemade = $(this).find("choicemade").text();
                            var placementrule = $(this).find("rule").text();
                            packagerange = $(this).find("packagerange").text();
                            cutpoint = $(this).find("cutpoint").text();

                            $("#placementnames").html(codename);
                            $("#htotalstudents").html(total);
                            $("#hnumofplaced").html(placed);
                            $("#hnumofchooser").html(choicemade);
                            $("#placementrule").html(placementrule);
                            ready = choicemade - placed;
                            $("#hreadytobeplaced").html(ready);
                            $("#packagerange").html(packagerange);
                            $("#cutpoint").html(cutpoint);
                            $("#range").html("");
                        });
                    }
                });
                //////////////////////////////////////////////////////
                ////// FILLS DEPARTMENT DROPDOWN FOR ADVANCED SEARCH///
                //////////////////////////////////////////////////////
                $.get('/StudentPlacementUI/GridDataController?q=data&data=departmentsforplacement&action=all&placementId=' + placementId, {}, function(xml) {
                    if (xml) {
                        $('#departmentsrch').empty();

                        $('#departmentsrch').append('<option  label="-All-" value="">-All-</option>');
                        $('#departmentsrch').append('<option  label="-Not Placed-" value="0">-Not Placed-</option>');


                        $('department', xml).each(function(i) {
                            var codename = $(this).find("codename").text();
                            var id = $(this).find("id").text();
                            $('#departmentsrch').append('<option  label="' + codename + '" value="' + id + '">' + codename + '</option>');
                        });
                    }
                });
                $("#placementstudentsdiv").show();
                $("#placementstudentlistdiv").show();
                $("#topMenu").show();
            }
            else {
                placementId = 0;
                $("#placementstudentsdiv").hide();
                $("#placementstudentlistdiv").hide();
                $("#topMenu").hide();
            }
        }
    }).navGrid("#placementpagers", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: false
    });
    
    /*
     * ***************************************************************************
     * ******************* PLACEMENTS > Refresh btn *******************************************
     * ***************************************************************************
     */
    $('#refreshplacements').click(function() {
        placementId = 0;
        $("#placementstudentsdiv").hide();
        $("#placementstudentlistdiv").hide();
        $("#topMenu").hide();
        jQuery("#placementlists").trigger("reloadGrid");
        jQuery("#placementstudentlist").setGridParam({
                    url: '',
                    editurl: '/StudentPlacementUI/PlacementStudentController?q=1&action=updateData'
                }).trigger("reloadGrid");
    });

    /*
     * ***************************************************************************
     *  * ***************************************************************************
     * ******************* STUDENTS ************************************************
     *  * ***************************************************************************
     * ***************************************************************************
     */
    var choicegridid = "";
    var studentidname = "";  //used in new and edit dialog
    jQuery("#placementstudentlist").jqGrid({
        url: '/StudentPlacementUI/PlacementStudentController?q=1&action=fetchData&placementId=' + placementId,
        datatype: "xml",
        mtype: 'GET',
        colNames: ['id', 'Id Number', 'First Name', 'Middle Name', 'Last Name', 'Name', 'Gender', 'Disability', 'Region', 'CGPA','deptid', 'Department', 'Placement', 'Reason'],
        colModel: [
            {
                name: 'id',
                index: 'id',
                // width: 50,
                editable: true,
                hidden: true
            },
            {
                name: 'idnum',
                index: 'idnum',
                align: 'left',
                width: 50,
                sortable: true,
                editable: false,                
                hidden: false
            },
            {
                name: 'fname',
                index: 'fname',
                align: 'center',
                // width: 70, 
                sortable: true,
                editable: false,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            },
            {
                name: 'mname',
                index: 'mname',
                align: 'center',
                //width: 70, 
                sortable: true,
                editable: false,                
                hidden: true
            },
            {
                name: 'lname',
                index: 'lname',
                align: 'center',
                //width: 70, 
                sortable: true,
                editable: false,                
                hidden: true
            },
            {
                name: 'fullname',
                index: 'fullname',
                align: 'left',
                //width: 80,
                sortable: true,
                editable: false,                
                hidden: false
            },
            {
                name: 'sex',
                index: 'sex',
                align: 'left',
                width: 40,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    value: 'F:Female;M:Male'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'disability',
                index: 'disability',
                align: 'left',
                width: 50,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    value: 'NONE:NONE;HEARING:HEARING;VISION:VISION;HANDYCAPE:HANDYCAPE;OTHER:OTHER'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
            {
                name: 'region',
                index: 'region',
                align: 'left',
                width: 50,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    value: 'SOMALI:SOMALI;GAMBELLA:GAMBELLA;BENSHANGUL:BENSHANGUL;AFAR:AFAR;SNNP:SNNP;AMHARA:AMHARA;OROMIA:OROMIA;TIGRI:TIGRI;OTHER:OTHER'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: false
            },
           
            {
                name: 'cgpa',
                index: 'cgpa',
                align: 'right',
                width: 30,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0,
                    maxValue: 4
                },
                hidden: false
            },            
            {
                name: 'department',
                index: 'department',
                align: 'left',
                width: 60,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=department&action=plcall&placementId=' + placementId
                },
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: true
            },
            {
                name: 'departmentCode',
                index: 'departmentCode',
                align: 'left',
                width: 60,
                sortable: true,
                editable: false,                
                hidden: false
            },
            {
                name: 'placement',
                index: 'placement',
                align: 'left',
                width: 60,
                sortable: true,
                editable: false,                
                hidden: true
            }
            ,
            {
                name: 'reason',
                index: 'reason',
                align: 'left',
                width: 130,
                sortable: true,
                editable: true,
                edittype: 'text',
                hidden: false
            }
        ],
        pager: $("#placementstudentpager"),
        rowNum: 10,
        height: 'auto',
        width: '100%',
        autowidth: true,
        page: 1,
        sortname: 'id',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [10, 20, 30, 50, 100, 200, 300, 500],
        imgpath: "../css/images",
        caption: "Students in " + placement,
        toolbar: [true, "bottom"],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/PlacementStudentController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {
                var selectedsturowVal = jQuery("#placementstudentlist").getRowData(id);
                studentidname = selectedsturowVal['idnum'].toString() + " - " + selectedsturowVal['name'].toString();
            }
        },
        subGrid: true,
        subGridRowExpanded: function(subgridId, rowId) {
            var subgidtableid = subgridId + "_t";
            jQuery("#" + subgridId).html("<div id=\"topMenu\" class=\"ui-widget-header ui-corner-all\"> <table><tr>" +
                    "<td><a id=\"" + subgridId + "_newchoice\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\">New<span class=\"ui-icon ui-icon-plusthick\"></span> </a></td>" +
                    "<td><a id=\"" + subgridId + "_editchoice\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\">Edit<span class=\"ui-icon ui-icon-pencil\"></span> </a></td>" +
                    "<td><a id=\"" + subgridId + "_deletechoice\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\">Remove<span class=\"ui-icon ui-icon-trash\"></span> </a></td>" +
                    "<td><a id=\"" + subgridId + "_refreshchoice\" class=\"fm-button ui-state-default ui-corner-all fm-button-icon-left\">Refresh<span class=\"ui-icon ui-icon-refresh\"></span> </a></td>" +
                    "</tr></table></div><table id=\"" + subgidtableid + "\" class=\"scroll\"></table>");
            $("#" + subgridId + "_newchoice").click(function() {
                jQuery("#" + subgidtableid).editGridRow('new');
            });
            $("#" + subgridId + "_editchoice").click(function() {
                var selectedrow = jQuery("#" + subgidtableid).getGridParam('selrow');
                if (selectedrow != null)
                    jQuery("#" + subgidtableid).editGridRow(selectedrow);
                else {
                    $("#msg").html("Please Select a student choice!");
                    $("#msgdlg").dialog(dial2);
                    $("#msgdlg").dialog('open');
                }
            });
            $("#" + subgridId + "_deletechoice").click(function() {
                var selectedrow = jQuery("#" + subgidtableid).getGridParam('selrow');
                if (selectedrow != null)
                    jQuery("#" + subgidtableid).delGridRow(selectedrow);
                else {
                    $("#msg").html("Please Select a student choice!");
                    $("#msgdlg").dialog(dial2);
                    $("#msgdlg").dialog('open');
                }
            });
            $("#" + subgridId + "_refreshchoice").click(function() {
                jQuery("#" + subgidtableid).trigger("reloadGrid");
            });
            jQuery("#" + subgidtableid).jqGrid({
                url: '/StudentPlacementUI/ChoiceController?q=1&action=fetchData&studentid=' + rowId + '&placementId=' + placementId,
                datatype: "xml",
                colNames: ['id', 'Department Code', 'Department Name', 'Rank'],
                colModel: [
                    {
                        name: 'id',
                        index: 'id',
                        width: 50,
                        editable: true,
                        hidden: true
                    },
                    {
                        name: 'departmentcode',
                        index: 'departmentcode',
                        align: 'left',
                        width: 100,
                        sortable: true,
                        editable: false,
                        edittype: 'text',
                        editrules: {
                            edithidden: true,
                            required: true
                        },
                        hidden: false
                    },
                    {
                        name: 'departmentname',
                        index: 'departmentname',
                        align: 'left',
                        width: 220,
                        sortable: true,
                        editable: true,
                        edittype: 'select',
                        editoptions: {
                            dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=placementdepartment&action=all&placementId=' + placementId
                        },
                        editrules: {
                            edithidden: true,
                            required: true
                        },
                        hidden: false
                    },
                    {
                        name: 'rank',
                        index: 'rank',
                        align: 'center',
                        width: 70,
                        sortable: true,
                        editable: true,
                        edittype: 'text',
                        editrules: {
                            edithidden: true,
                            required: true
                        },
                        hidden: false
                    }
                ],
                height: '100%',
                width: '100%',
                rowNum: 20,
                caption: 'Choices',
                editurl: '/StudentPlacementUI/ChoiceController?q=1&action=updateData&studentid=' + rowId + '&placementId=' + placementId,
                autowidth: true,
                rownumbers: true
            });
        }
    }).navGrid("#placementstudentpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });

    //     $('#newstud').click(function(){
    //        jQuery("#pstudentlist").editGridRow('new');
    //    });
    $('#editplacementstudent').click(function() {
        var selectedrow = jQuery("#placementstudentlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#placementstudentlist").editGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a student!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#delplacementstudent').click(function() {
        var selectedrow = jQuery("#placementstudentlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#placementstudentlist").delGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a student!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
    $('#refreshplacementstudent').click(function() {
        jQuery("#placementstudentlist").trigger("reloadGrid");
    });
    $('#togglesearchplacementstudent').click(function() {
        if ($('#togglesearchplacementstudent').html() === "Show Search<span class=\"ui-icon ui-icon-search\"></span>") {
            $('#togglesearchplacementstudent').html("Hide Search<span class=\"ui-icon ui-icon-search\"></span>");
        } else {
            $('#togglesearchplacementstudent').html("Show Search<span class=\"ui-icon ui-icon-search\"></span>");
        }
        $('#padvancedsearch').toggle();
    });

    /*
     * ***************************************************************************
     * ******************* STUDENTS > Search by Id btn **********************
     * ***************************************************************************
     */
    $("#searchid").click(function() {
        var idnum = $("#idnumbersrch").val();
        if (!idnum) {
            $("#msg").html("Please Enter Id Number to search!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');

            jQuery("#placementstudentlist").setGridParam({
                url: '/StudentPlacementUI/PlacementStudentController?q=1&action=fetchData&placementId=' + placementId
            }).trigger("reloadGrid");
        }
        else {
            jQuery("#placementstudentlist").setGridParam({
                url: '/StudentPlacementUI/PlacementStudentController?q=search&action=fetchData&placementId=' + placementId + "&idnumbersrch=" + idnum
            }).trigger("reloadGrid");
        }
    });

    /*
     * ***************************************************************************
     * ******************* STUDENTS > Advanced Search btn **********************
     * ***************************************************************************
     */
    $("#searchadv").click(function() {
        var departmentsrch = $("#departmentsrch").val();
        var disabilitysrch = $("#disabilitysrch").val();
        var staffchildsrch = $("#staffchildsrch").val();
        var gendersrch = $("#gendersrch").val();
        var regionsrch = $("#regionsrch").val();
        var opsrch = $("#opsrch").val();
        var cgpasrch = $("#cgpasrch").val();
        if (departmentsrch == null && disabilitysrch == null && staffchildsrch == null
                && gendersrch == null && regionsrch == null && opsrch == null && cgpasrch == null) {
            $("#msg").html("Please Enter at least one information to search!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
        else {
            jQuery("#placementstudentlist").setGridParam({
                url: '/StudentPlacementUI/PlacementStudentController?q=search&action=fetchData&placementId=' + placementId
                        + "&req=adv"
                        + "&deptsrch=" + departmentsrch
                        + "&dissrch=" + disabilitysrch
                        + "&staffsrch=" + staffchildsrch
                        + "&gendersrch=" + gendersrch
                        + "&regionsrch=" + regionsrch
                        + "&opsrch=" + opsrch
                        + "&cgpasrch=" + cgpasrch
            }).trigger("reloadGrid");
        }
    });
    /*
     * ***************************************************************************
     * ******************* STUDENTS > Export to PDF button **********************
     * ***************************************************************************
     */
    var pdf = function() {
        window.open('../Jasper?q=1&action=exportData&placementId=' + placementId + "&ftype=pdf",
                'list of students',
                "fullscreen=yes,menubar=yes,status=yes,scrollbars,dependent");

        $("#msgdlg").dialog('close');
    };
    var excel = function() {
        window.open('../Jasper?q=1&action=exportData&placementId=' + placementId + "&ftype=excel",
                'list of students',
                "fullscreen=yes,menubar=yes,status=yes,scrollbars,dependent");
        $("#msgdlg").dialog('close');
    };
    var cancel = function() {
        $("#msgdlg").dialog('close');
    };
    var exportconfirmdlg = {
        autoOpen: false,
        title: 'File type',
        modal: true,
        buttons: {
            "PDF": pdf,
            "Excel": excel,
            "Cancel": cancel
        }
    };
    $('#exportplacementstudents').click(function() {
        $("#msg").html("Please select file format!");
        $("#msgdlg").dialog(exportconfirmdlg);
        $("#msgdlg").dialog('open');
    });


    var stepbystep = function() {

        $("#confirmmsgdlg").dialog('close');
    };
    var palceall = function() {

        $("#confirmmsgdlg").dialog('close');
    };
    var startingoptdlg = {
        autoOpen: false,
        title: 'Procedure type',
        modal: true,
        buttons: {
            "Step by Step": stepbystep,
            "Place Once": palceall,
            "Cancel": cancel
        }
    };
    $('#startplacementstudents').click(function() {
        $("#confirmmsg").html("Please select placement procedure!");
        $("#confirmmsgdlg").dialog(startingoptdlg);
        $("#confirmmsgdlg").dialog('open');
    });
});
