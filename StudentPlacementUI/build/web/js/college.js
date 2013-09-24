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
    jQuery("#collegelist").jqGrid({
        url: '/StudentPlacementUI/CollegeController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',
        //async: true,
        colNames: ['id', 'College Code', 'College Name', 'Address', "Description"],
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
                width: 40,
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
                width: 90,
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
                name: 'address',
                index: 'address',
                align: 'left',
                width: 90,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: false
            },
            {
                name: 'description',
                index: 'description',
                align: 'left',
                width: 90,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: false
            }
        ],
        pager: $("#collegepager"),
        rowNum: 10,
        height: 'auto',
        width: '100%',
        autowidth: true,
        page: 1,
        sortname: 'code',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [5, 10, 20, 30],
        imgpath: "../js/jqGrid/css/images",
        caption: "Colleges",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/CollegeController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {
//                jQuery("#deptlist").setGridParam({
//                    url: '../JQDepartmentServlet?q=1&action=fetchData&collegeId=' +  id
//                }).trigger("reloadGrid");     
            }
        }
    }).navGrid("#collegepager", {
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
    $('#newcollege').click(function() {
        jQuery("#collegelist").editGridRow('new');
    });

    $('#editcollege').click(function() {
        var selectedrow = jQuery("#collegelist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#collegelist").editGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a college!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#deletecollege').click(function() {
        var selectedrow = jQuery("#collegelist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#collegelist").delGridRow(selectedrow);
        else {
            $("#msg").html("Please Select a college!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#refreshcollege').click(function() {
        jQuery("#collegelist").trigger("reloadGrid");
    });
});

