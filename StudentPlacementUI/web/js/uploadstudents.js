/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {
    jQuery("#uploadpreview").hide();

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
        jQuery("#msgdlg").dialog('close');
    };
    var dial2 = {
        autoOpen: false,
        title: 'Processing data',
        modal: true
    };
    jQuery('#placementId').ready(function() {
        jQuery.get('/StudentPlacementUI/GridDataController?q=data&data=placement&action=all', {}, function(xml) {
            if (xml) {
                jQuery('#placementId').empty();
                jQuery('#placementId').append('<option  label="  -Select-  " value="0">  -Select-  </option>');
            }
            jQuery('placement', xml).each(function(i) {
                var codename = $(this).find("codename").text();
                var id = jQuery(this).find("id").text();
                jQuery('#placementId').append('<option  label="' + codename + '" value="' + id + '">' + codename + "</option>");
            });
        });
    });
    jQuery('#placementId').change(function() {
        jQuery.get('/StudentPlacementUI/GridDataController?q=data&data=departments&action=uploadheader&placementId=' + jQuery('#placementId').val(), {}, function(xml) {
            if (xml) {
                jQuery('#columns').empty();
                jQuery('#columns').append('<option  label="  -Select-" value="-1">  -Select-  </option>');
                jQuery('fileheading', xml).each(function(i) {
                    var heading = jQuery(this).find("heading").text();
                    var type = jQuery(this).find("type").text();
                    jQuery('#columns').append('<option  label="' + heading + '" value="' + type + '">' + heading + "</option>");
                });
            }
        });
    });
    jQuery('#columns').change(function() {
    });

    jQuery("#previewlist").jqGrid({
        url: '/StudentPlacementUI/UploadController?q=1&action=fetchData',
        datatype: "xml",
        mtype: 'GET',
        colNames: ['id', 'Id Number', 'First Name', 'Middle Name', 'Last Name', 'Name', 'Sex', 'disability', 'region', 'staff relation', 'cgpa', 'College', 'Department', 'Placement'],
        colModel: [
            {
                name: 'id',
                index: 'id',
                width: 50,
                editable: true,
                hidden: true
            },
            {
                name: 'idnum',
                index: 'idnum',
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
            },
            {
                name: 'fname',
                index: 'fname',
                align: 'center',
                width: 70,
                sortable: true,
                editable: true,
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
                width: 70,
                sortable: true,
                editable: true,
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
                width: 70,
                sortable: true,
                editable: true,
                edittype: 'text',
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            },
            {
                name: 'name',
                index: 'name',
                align: 'left',
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
                width: 50,
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
                width: 70,
                sortable: true,
                editable: true,
                edittype: 'select',
                editoptions: {
                    value: 'None:None;Hearing:Hearing;Vision:Vision;Handycape:Handycape;Other:Other'
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
                width: 70,
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
                name: 'staff',
                index: 'staff',
                align: 'left',
                width: 70,
                sortable: true,
                editable: false,
                edittype: 'select',
                editoptions: {
                    value: 'None:None;Related:Related'
                },
                editrules: {
                    edithidden: true,
                    required: true
                },
                hidden: true
            },
            {
                name: 'cgpa',
                index: 'cgpa',
                align: 'left',
                width: 70,
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
                align: 'left',
                width: 70,
                sortable: true,
                editable: false,
                edittype: 'select',
                editoptions: {
                    dataUrl: '/StudentPlacementUI/GridDataController?q=editgrid&data=college&action=all'
                },
                editrules: {
                    edithidden: true,
                    required: false
                },
                hidden: true
            },
            {
                name: 'department',
                index: 'department',
                align: 'left',
                width: 70,
                sortable: true,
                editable: false,
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
                width: 70,
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
                hidden: false
            }
        ],
        pager: $("#previewpager"),
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
        caption: "Students",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/UploadController?q=1&action=updateData',
        onSelectRow: function(id) {
            if (id && id != null) {
            }
        },
        subGrid: true,
        subGridRowExpanded: function(subgridId, rowId) {
            var subgidtableid = subgridId + "_t";
            jQuery("#" + subgridId).html("<table id=\"" + subgidtableid + "\" class=\"scroll\"></table>");
            jQuery("#" + subgidtableid).jqGrid({
                url: '/StudentPlacementUI/UploadController?q=1&action=fetchChoice&idnum=' + rowId,
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
                        editable: true,
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
                        edittype: 'text',
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
                rowNum: 20,
                caption: 'Choices',
                autowidth: false,
                rownumbers: true
            })
        }
    }).navGrid("#previewpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });
    
    jQuery("#cancelu").click(function() {
        jQuery("#previewlist").clearGridData();
        jQuery("#uploadpreview").hide();
        jQuery("#uploadform").show();
    });
    jQuery("#saveu").click(function() {
        jQuery("#previewlist").setGridParam({
            url: '../JQUploadStudent?q=1&action=save',
            mtype: 'GET'
        }).trigger("reloadGrid");

    });
    // pre-submit callback 
    function showRequest(formData, jqForm, options) {
        // formData is an array; here we use $.param to convert it to a string to display it 
        // but the form plugin does this for you automatically when it submits the data 
        var queryString = $.param(formData);

        // jqForm is a jQuery object encapsulating the form element.  To access the 
        // DOM element for the form do this: 
        // var formElement = jqForm[0]; 


        jQuery("#msg").html("Please wait.....!<br /> This may take several minutes!");
        jQuery("#msgdlg").dialog(dial2);
        jQuery("#msgdlg").dialog('open');
        // here we could return false to prevent the form from being submitted; 
        // returning anything other than false will allow the form submit to continue 
        return true;
    }
//    var showRequest = function() {
//        $("#msg").html("Please wait.....!<br /> This may take several minutes!");
//        $("#msgdlg").dialog(dial2);
//        $("#msgdlg").dialog('open');
//    };

//    var showResponse = function() {
//        $("#msgdlg").dialog('close');
//        $("#uploadform").hide();
//        $("#uploadpreview").show();
//        jQuery("#previewlist").trigger("reloadGrid");
//    };
    // post-submit callback 
    function showResponse(responseText, statusText, xhr, $form) {
        // for normal html responses, the first argument to the success callback 
        // is the XMLHttpRequest object's responseText property 

        // if the ajaxForm method was passed an Options Object with the dataType 
        // property set to 'xml' then the first argument to the success callback 
        // is the XMLHttpRequest object's responseXML property 

        // if the ajaxForm method was passed an Options Object with the dataType 
        // property set to 'json' then the first argument to the success callback 
        // is the json data object returned by the server 

        jQuery("#msgdlg").dialog('close');
        jQuery("#uploadform").hide();
        jQuery("#uploadpreview").show();
        jQuery("#previewlist").trigger("reloadGrid");
    }
    jQuery('#uploadForm').submit(function() {
        // submit the form 
        var options = {
            //target: '#uploadpreview', // target element(s) to be updated with server response 
            beforeSubmit: showRequest, // pre-submit callback 
            success: showResponse // post-submit callback 
                    //dataType: 'xml'
                    // other available options: 
                    //url:       url         // override for form's 'action' attribute 
                    //type:      type        // 'get' or 'post', override for form's 'method' attribute 
                    //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
                    //clearForm: true        // clear all form fields after successful submit 
                    //resetForm: true        // reset the form after successful submit 

                    // $.ajax options can be used here too, for example: 
                    //timeout:   3000 
        };
        jQuery(this).ajaxSubmit(options);
//        $("#uploadpreview").show();
//        jQuery("#previewlist").trigger("reloadGrid");
        // return false to prevent normal browser submit and page navigation 
        return false;
    });
    
    jQuery('#newstud').click(function() {
        jQuery("#previewlist").editGridRow('new');
    });
    
    jQuery('#editstud').click(function() {
        var selectedrow = jQuery("#previewlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#previewlist").editGridRow(selectedrow);
        else {
            jQuery("#msg").html("Please Select a student!");
            jQuery("#msgdlg").dialog(dial2);
            jQuery("#msgdlg").dialog('open');
        }
    });

    jQuery('#removestud').click(function() {
        var selectedrow = jQuery("#previewlist").getGridParam('selrow');
        if (selectedrow != null)
            jQuery("#previewlist").delGridRow(selectedrow);
        else {
            jQuery("#msg").html("Please Select a student!");
            jQuery("#msgdlg").dialog(dial2);
            jQuery("#msgdlg").dialog('open');
        }
    });

    jQuery('#refreshstud').click(function() {
        jQuery("#previewlist").trigger("reloadGrid");
    });
});

