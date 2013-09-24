/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {

    var placementId = 0;
    $('#placementcode').ready(function() {
        $.get('/StudentPlacementUI/GridDataController?q=data&data=placement&action=all', {}, function(xml) {
            if (xml) {
                $('#placementcode').empty();
                $('#placementcode').append('<option  label="  -Select-  " value="0">  -Select-  </option>');
            }
            $('placement', xml).each(function(i) {
                var codename = $(this).find("codename").text();
                var id = $(this).find("id").text();
                $('#placementcode').append('<option  label="' + codename + '" value="' + id + '">' + codename + "</option>");
            });
        });
    });

    $('#placementcode').change(function() {
        placementId = $('#placementcode').val();                        
        jQuery("#makeplacementstudentlist").setGridParam({
            url: '/StudentPlacementUI/StudentPlacementController?q=1&action=fetchData&placementId=' + placementId,
            editurl: '/StudentPlacementUI/StudentPlacementController?q=1&action=updateData'
        }).trigger("reloadGrid");
        $("#makeplacementstudentsdiv").show();
    });

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
    /*
     * ***************************************************************************
     *  * ***************************************************************************
     * ******************* STUDENTS ************************************************
     *  * ***************************************************************************
     * ***************************************************************************
     */
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
    var choicegridid = "";
    var studentidname = "";  //used in new and edit dialog
    jQuery("#makeplacementstudentlist").jqGrid({
        url: '/StudentPlacementUI/StudentPlacementController?q=1&action=fetchData&placementId=' + placementId,
        datatype: "xml",
        mtype: 'GET',
        colNames: ['id', 'Id Number', 'First Name', 'Middle Name', 'Last Name', 'Name', 'Gender', 'Disability', 'Region', 'CGPA', 'Department', 'Placement', 'Reason'],
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
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
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
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            },
            {
                name: 'lname',
                index: 'lname',
                align: 'center',
                //width: 70, 
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
                name: 'fullname',
                index: 'fullname',
                align: 'left',
                //width: 80,
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
                name: 'sex',
                index: 'sex',
                align: 'left',
                width: 40,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    value: 'F:F;M:M'
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
                    value: 'SOMALI:SOMALI;GAMBELLA:GAMBELLA;BENSHANGUL:BENSHANGUL;AFAR:AFAR;SNNP:SNNP;AMHARA:AMHARA;OROMIA:OROMIA;TIGRAY:TIGRAY;OTHERS:OTHERS'
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
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=department&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: false
            },
            {
                name: 'placement',
                index: 'placement',
                align: 'left',
                width: 60,
                sortable: true,
                editable: false,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=placement&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: true
            }
            ,
            {
                name: 'reason',
                index: 'reason',
                align: 'left',
                width: 130,
                sortable: true,
                editable: false,
                hidden: false
            }
        ],
        pager: $("#makeplacementstudentpager"),
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
        caption: "Students in ",
        toolbar: [true, "bottom"],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/StudentPlacementController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {
                var selectedsturowVal = jQuery("#makeplacementstudentlist").getRowData(id);
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
                            dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=placementdepartment&action=all&placementId=' + 1
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
    }).navGrid("#makeplacementstudentpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });

    $("#startplacement").click(function() {
        jQuery("#makeplacementstudentlist").setGridParam({
            url: '/StudentPlacementUI/StudentPlacementController?action=start&placementId=' + placementId,
            editurl: '/StudentPlacementUI/StudentPlacementController?q=1&action=updateData'
        }).trigger("reloadGrid");
    });
    $("#unplaceall").click(function (){
        jQuery("#makeplacementstudentlist").setGridParam({
            url: '/StudentPlacementUI/StudentPlacementController?action=unplaceall&placementId=' + placementId,
            editurl: '/StudentPlacementUI/StudentPlacementController?q=1&action=updateData'
        }).trigger("reloadGrid");               
    });
    $("#saveplacementresult").click(function (){
        jQuery("#makeplacementstudentlist").setGridParam({
            url: '/StudentPlacementUI/StudentPlacementController?action=save&placementId=' + placementId,
            editurl: '/StudentPlacementUI/StudentPlacementController?q=1&action=updateData'
        }).trigger("reloadGrid");               
    });
    $("#refreshmakeplacement").click(function(){
         jQuery("#makeplacementstudentlist").trigger("reloadGrid");
    });   
    
       var pdf = function () {
        window.open('/StudentPlacementUI/ReportController?q=1&action=exportData&placementId='+placementId+"&ftype=pdf",
            'list of students',
            "fullscreen=yes,menubar=yes,status=yes,scrollbars,dependent");                         

        $("#msgdlg").dialog('close');      
    };
    var excel = function () {
        window.open('StudentPlacementUI/ReportController?q=1&action=exportData&placementId='+placementId+"&ftype=excel",
            'list of students',
            "fullscreen=yes,menubar=yes,status=yes,scrollbars,dependent");                         

        $("#msgdlg").dialog('close');      
    };
    var cancel = function () {
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
    $('#exportplacementresult').click(function(){              
        $("#msg").html("Please select file format!");
        $("#msgdlg").dialog(exportconfirmdlg);
        $("#msgdlg").dialog('open');     
    });  
});