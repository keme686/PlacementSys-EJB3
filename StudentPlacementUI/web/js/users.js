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
    jQuery("#userslist").jqGrid({
        url: '/StudentPlacementUI/UsersController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',
        //async: true,
        colNames: ['id', 'Name', 'Id Number/username','Role', 'Status', "Last Online"],
        colModel: [
            {
                name: 'id',
                index: 'id',
                width: 50,
                hidden: true
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
                name: 'idnum',
                index: 'idnum',
                align: 'left',
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
                name: 'role',
                index: 'role',
                align: 'left',
                width: 90,
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
                name: 'accountstatus',
                index: 'accountstatus',
                align: 'left',
                width: 90,
                sortable: true,
                editable: false,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: true
            },
            {
                name: 'lastactive',
                index: 'lastactive',
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
        pager: $("#userspager"),
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
        editurl: '/StudentPlacementUI/UsersController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {  
            }
        }
    }).navGrid("#userspager", {
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
    var reset = function() {
        //post value here
        //include pass and selectedrow
        var selectedrow = jQuery("#userslist").getGridParam('selrow');
        var userdata = {};
        userdata.username = selectedrow;
        $.post("/StudentPlacementUI/UsersController?action=updateData&oper=resetpwd", userdata, function(data) {
            if (data) {

                $('info', data).each(function(i) {
                    var status = $(this).find("status").text();
                    if (status == '1') {
                        $("#msg").html("User password has been generated and sent to the user, if user has email info!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                    else if (status == '0') {
                        $("#msg").html("The system cannot change users' password, please click refresh button and try again. OR check placement information!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                    else if (status == '-1') {
                        $("#msg").html("Something went wrong, please review your information correctly!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                });

            }
        });
        jQuery("#userslist").trigger("reloadGrid");
        $("#resetpwdformdiv").dialog('close');

    };
    var cancel = function() {
        $("#resetpwdformdiv").dialog('close');
    };
    var chgsts = function() {
        var selectedrow = jQuery("#userslist").getGridParam('selrow');
        var userdata = {};
        userdata.username = selectedrow;
        $.post("/StudentPlacementUI/UsersController?action=updateData&oper=chstatus", userdata, function(data) {
            if (data) {

                $('info', data).each(function(i) {
                    var status = $(this).find("status").text();
                    if (status == '1') {
                        $("#msg").html("User status has been changed!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                    else if (status == '0') {
                        $("#msg").html("The system cannot change status of the user, please click refresh button and try again. OR check placement information!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                    else if (status == '-1') {
                        $("#msg").html("Something went wrong, please review your information correctly!");
                        $("#msgdlg").dialog(dial2);
                        $("#msgdlg").dialog('open');
                    }
                });
            }
        });
        jQuery("#userslist").trigger("reloadGrid");
        $("#resetpwdformdiv").dialog('close');
    };
    var dial1 = {
        autoOpen: false,
        title: 'Warning',
        modal: true,
        buttons: {
            "Yes": reset,
            "Cancel": cancel
        }
    };
    var dial2 = {
        autoOpen: false,
        title: 'Warning',
        modal: true,
        buttons: {
            "Ok": Ok
        }
    };
    var dial3 = {
        autoOpen: false,
        title: 'Warning',
        modal: true,
        buttons: {
            "Yes": chgsts,
            "Cancel": cancel
        }
    };
    $('#newuser').click(function() {
        jQuery("#userslist").editGridRow('new');
    });

    $('#resetpwd').click(function() {
        var selectedrow = jQuery("#userslist").getGridParam('selrow');
        if (selectedrow == null) {
            $("#msg").html("Please Select a user!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
        else {
            var selectedrow = jQuery("#userslist").getGridParam('selrow');
            $('#rstmsg').html("Are you sure to change " + selectedrow + "'s password?<br /><em> This will generate new password and email the generated password for the selected user!</em>");
            $("#resetpwdformdiv").dialog(dial1);
            $("#resetpwdformdiv").dialog('open');
        }
    });
    $('#chstatus').click(function() {
        var selectedrow = jQuery("#userslist").getGridParam('selrow');
        if (selectedrow != null) {
            //post selected user
            var selectedrow = jQuery("#userslist").getGridParam('selrow');
            $('#rstmsg').html("Are you sure to change " + selectedrow + "'s status?");
            $("#resetpwdformdiv").dialog(dial3);
            $("#resetpwdformdiv").dialog('open');
        }
        else {
            $("#msg").html("Please Select a user!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });

    $('#refreshusers').click(function() {
        jQuery("#userslist").trigger("reloadGrid");
    });
});



