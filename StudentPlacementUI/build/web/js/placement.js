/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {

$.extend($.jgrid.ajaxOptions, { async: true });

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
//    $("#departmentsdiv").hide();
//    $("#pdepartmentlistdiv").hide();
    /*
     * ***************************************************************************
     * ******************* PLACEMENTS *******************************************
     * ***************************************************************************
     */
    var placement = "";
    var placementId = 0;

    jQuery("#placementlist").jqGrid({
        url: '/StudentPlacementUI/PlacementController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',
      //  ajaxRowOptions: { async: true },
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
        pager: $("#placementpager"),
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
                var selectedrowVal = jQuery("#placementlist").getRowData(id);
                placement = selectedrowVal['code'].toString() + " - " + selectedrowVal['name'].toString();
                $("#placementname").html("Departments for " + placement);
                placementId = id;
                jQuery("#pdepartmentlist").setGridParam({
                    url: '/StudentPlacementUI/DepartmentToPlacementController?q=1&action=fetchData&placementId=' + id,
                    editurl: "/StudentPlacementUI/DepartmentToPlacementController?q=1&action=updateData&placementId=" + id
                }).trigger("reloadGrid");
//                $("#departmentsdiv").show();
//                $("#pdepartmentlistdiv").show();
            }
            else {
                placementId = 0;
                jQuery("#pdepartmentlist").setGridParam({
                    url: '/StudentPlacementUI/DepartmentToPlacementController?q=1&action=fetchData&placementId=' + 0,
                    editurl: "/StudentPlacementUI/DepartmentToPlacementController?q=1&action=updateData&placementId=" + 0
                }).trigger("reloadGrid");
//                $("#departmentsdiv").hide();
//                $("#pdepartmentlistdiv").hide();
            }
        }
    }).navGrid("#placementpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: false
    });


    /*
     * ***************************************************************************
     * ******************* PLACEMENTS > New btn *******************************************
     * ***************************************************************************
     */
    $('#newplacement').click(function() {
        jQuery("#placementlist").editGridRow('new');
    });

    /*
     * ***************************************************************************
     * ******************* PLACEMENTS > Edit btn *******************************************
     * ***************************************************************************
     */
    $('#editplacement').click(function() {
        var selectedrow = jQuery("#placementlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#placementlist").editGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a placement!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    /*
     * ***************************************************************************
     * ******************* PLACEMENTS > Remove btn *******************************************
     * ***************************************************************************
     */
    $('#deleteplacement').click(function() {
        var selectedrow = jQuery("#placementlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#placementlist").delGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a placement!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
    /*
     * ***************************************************************************
     * ******************* PLACEMENTS > Refresh btn *******************************************
     * ***************************************************************************
     */
    $('#refreshplacement').click(function() {
//        $("#departmentsdiv").hide();
//        $("#pdepartmentlistdiv").hide();
        placementId = 0;
        jQuery("#placementlist").trigger("reloadGrid");
        jQuery("#pdepartmentlist").setGridParam({
            url: '/StudentPlacementUI/DepartmentToPlacementController?q=1&action=fetchData&placementId=' + 0,
            editurl: "/StudentPlacementUI/DepartmentToPlacementController?q=1&action=updateData&placementId=" + 0
        }).trigger("reloadGrid");
    });



    /*
     * ***************************************************************************
     *  * ***************************************************************************
     * ******************* DEPARTMENTS *******************************************
     * ***************************************************************************
     *  * ***************************************************************************
     */
    jQuery("#pdepartmentlist").jqGrid({
        datatype: "xml",
        url: '/StudentPlacementUI/DepartmentToPlacementController?q=1&action=fetchData&placementId=' + placementId,
        mtype: 'GET',
      //  ajaxRowOptions: { async: true },
        colNames: ['id', 'DepartmentId', 'Department Code', 'Department Name', 'Capacity', 'Max Capacity Violation'],
        colModel: [
            {
                name: 'id',
                index: 'id',
                width: 50,
                hidden: true
            },
            {
                name: 'deptid',
                index: 'deptid',
                editable: true,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=department&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            },
            {
                name: 'deptcode',
                index: 'deptcode',
                align: 'center',
                width: 50,
                sortable: true,
                editable: false,
                hidden: false
            },
            {
                name: 'deptname',
                index: 'deptname',
                align: 'left',
                width: 130,
                sortable: true,
                editable: false,
                hidden: false
            },
            {
                name: 'capacitys',
                index: 'capacitys',
                align: 'center',
                width: 50,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0
                },
                hidden: false
            },
            {
                name: 'maxviolation',
                index: 'maxviolation',
                align: 'center',
                width: 50,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0
                },
                hidden: false
            }
        ],
        pager: $("#pdepartmentpager"),
        rowNum: 10,
        height: 'auto',
        width: '100%',
        autowidth: true,
        page: 1,
        sortname: 'id',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [10, 20, 30, 50, 100],
        imgpath: "../css/images",
        caption: "Placement Departments",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        //editurl: '/StudentPlacementUI/DepartmentToPlacementController?q=1&action=updateData&placementId='+placementId,
        onSelectRow: function(id) {
            if (id && id != null) {

            }
        }
    }).navGrid("#pdepartmentpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });

    var opts = {
        beforeShowForm: function(id) {
            // $("#ConcernedBody").combobox();
            //  $("#tr_ConcernedBody").children('td').last().children('input').first().css("height", "20px");
            // autocompletebutton
            // $("#td_autocompletebutton").css("font-size", "px");
            //$("#ConcernedBody").attr("height", "110px");
            $("#maxviolation").after("<em>If tie occurs</em>")
            if ($("#maxviolation").val() == null)
                $("#maxviolation").val(10);
        },
        addCaption: "New Department to " + placement,
        editCaption: "Edit Department of " + placement,
        bSubmit: "Save",
        bCancel: "Cancel",
        processData: "Processing...",
        closeAfterAdd: true,
        closeAfterEdit: true,
        recreateForm: true,
        position: "center",
        width: '400',
        reloadAfterSubmit: true,
        modal: true,
        msg: {
            required: "Field is required",
            number: "Please enter valid number!",
            minValue: "value must be greater than or equal to ",
            maxValue: "value must be less than or equal to "
        }
    };
    /*
     * *************************************************************************** 
     * ******************* DEPARTMENTS > New btn*******************************************
     * ***************************************************************************
     */
    $('#newpdepartment').click(function() {
        jQuery("#pdepartmentlist").editGridRow('new', opts);
    });
    /*
     * *************************************************************************** 
     * ******************* DEPARTMENTS > Edit btn*******************************************
     * ***************************************************************************
     */
    $('#editpdepartment').click(function() {
        var selectedrow = jQuery("#pdepartmentlist").getGridParam('selrow');
        placementId = jQuery("#placementlist").getGridParam('selrow');

        if (selectedrow != null) {

            jQuery("#pdepartmentlist").editGridRow(selectedrow, opts);
        }
        else {
            $("#msg").html("Please Select a department!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
    /*
     * *************************************************************************** 
     * ******************* DEPARTMENTS > Remove btn*******************************************
     * ***************************************************************************
     */
    $('#deletepdepartment').click(function() {
        var selectedrow = jQuery("#pdepartmentlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#pdepartmentlist").delGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a department!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
    /*
     * *************************************************************************** 
     * ******************* DEPARTMENTS > Refresh btn*******************************************
     * ***************************************************************************
     */
    $('#refreshpdepartment').click(function() {
        jQuery("#pdepartmentlist").trigger("reloadGrid");
    });

});