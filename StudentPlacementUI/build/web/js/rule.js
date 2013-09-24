/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {
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

    jQuery("#rulelist").jqGrid({
        url: '/StudentPlacementUI/RuleController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',
        colNames: ['id', 'CGPA ', 'Gender ', 'disability ', 'Region ', 'Cut Point'],
        colModel: [
            {
                name: 'id',
                index: 'id',
                width: 50,
                hidden: true
            },
            {
                name: 'cgpa',
                index: 'cgpa',
                align: 'center',
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0,
                    maxValue: 100
                },
                hidden: false
            },
            {
                name: 'sex',
                index: 'sex',
                align: 'center',
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0,
                    maxValue: 100
                },
                hidden: false
            },
            {
                name: 'disability',
                index: 'disability',
                align: 'center',
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0,
                    maxValue: 100
                },
                hidden: false
            },
            {
                name: 'region',
                index: 'region',
                align: 'center',
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true,
                    number: true,
                    minValue: 0,
                    maxValue: 100
                },
                hidden: false
            },
            {
                name: 'cutpoint',
                index: 'cutpoint',
                align: 'center',
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
            }
        ],
        pager: $("#rulepager"),
        rowNum: 5,
        height: 'auto',
        //minHeight:'200px',
        width: '100%',
        autowidth: true,
        page: 1,
        sortname: 'code',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [5, 10, 20, 30],
        imgpath: "../js/jqGrid/css/images",
        caption: "Placement Rules",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/RuleController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {
            }
        }
    }).navGrid("#rulepager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });
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
    var opts = {
        beforeShowForm: function(id) {
            if ($("#cutpoint").val() == null)
                $("#cutpoint").val('0');
            if ($("#disability").val() == null)
                $("#disability").val('100');
        },
        bSubmit: "Save",
        bCancel: "Cancel",
        processData: "Processing...",
        closeAfterAdd: true,
        closeAfterEdit: true,
        recreateForm: true,
        position: "center",
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
    $('#newcrule').click(function() {
        jQuery("#rulelist").editGridRow('new', opts);
    });

    var props = {
        beforeShowForm: function(id) {
            $("#disability").val('100');
        },
        bSubmit: "Update",
        bCancel: "Cancel",
        processData: "Processing...",
        closeAfterAdd: true,
        closeAfterEdit: true,
        recreateForm: true,
        position: "center",
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
    $('#editrule').click(function() {
        var selectedrow = jQuery("#rulelist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#rulelist").editGridRow(selectedrow,props);
        else {
            $("#msg").html("Please Select a rule!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#deleterule').click(function() {
        var selectedrow = jQuery("#rulelist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#rulelist").delGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a rule!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#refreshrule').click(function() {
        jQuery("#rulelist").trigger("reloadGrid");
    });
});