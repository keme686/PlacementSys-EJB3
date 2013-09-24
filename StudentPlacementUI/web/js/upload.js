/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {
    $("#uploadpreview").hide();

    var Ok = function() {
        $("#msgdlg").dialog('close');
    };
    var dial2 = {
        autoOpen: false,
        title: 'Processing data',
        modal: true
    };
    $('#placementId').ready(function() {
        $.get('/StudentPlacementUI/GridDataController?q=data&data=placement&action=all', {}, function(xml) {
            if (xml) {
                $('#placementId').empty();
                $('#placementId').append('<option  label="  -Select-  " value="0">  -Select-  </option>');
            }
            $('placement', xml).each(function(i) {
                var codename = $(this).find("codename").text();
                var id = $(this).find("id").text();
                $('#placementId').append('<option  label="' + codename + '" value="' + id + '">' + codename + "</option>");
            });
        });
    });
    $('#placementId').change(function() {
        $.get('/StudentPlacementUI/GridDataController?q=data&data=departments&action=uploadheader&placementId=' + $('#placementId').val(), {}, function(xml) {
            if (xml) {
                $('#columns').empty();
                $('#columns').append('<option  label="  -Select-" value="-1">  -Select-  </option>');
                $('fileheading', xml).each(function(i) {
                    var heading = $(this).find("heading").text();
                    var type = $(this).find("type").text();
                    $('#columns').append('<option  label="' + heading + '" value="' + type + '">' + heading + "</option>");
                });
            }
        });
    });
    $('#columns').change(function() {
    });

    jQuery("#previewlist").jqGrid({
        url: '../JQUploadStudent?q=1&action=preview',
        datatype: "xml",
        mtype: 'GET',
        colNames: ['IdNumber', 'fName', 'mname', 'lname', 'sex', 'dissability', 'region', 'staff', 'cgpa'],
        colModel: [
            {
                name: 'idnum',
                index: 'idnum',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'fname',
                index: 'fname',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'mname',
                index: 'mname',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'lname',
                index: 'lname',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'sex',
                index: 'sex',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'diss',
                index: 'diss',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'region',
                index: 'region',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'staff',
                index: 'staff',
                sortable: true,
                editable: true,
                hidden: false
            },
            {
                name: 'cgpa',
                index: 'cgpa',
                sortable: true,
                editable: true,
                hidden: false
            }
        ],
        rowNum: 20,
        height: 'auto',
        width: 950,
        page: 1,
        sortname: 'idnum',
        sortorder: 'asc',
        loadtext: "Loading...",
        rowList: [20, 30, 50, 100],
        imgpath: "../css/images",
        toolbar: [false, 'bottom'],
        viewrecords: true,
        rownumbers: true,
        pager: $("#previewpager"),
        caption: "Students Preview"
    }).navGrid("#studentpager", {
        view: false,
        edit: false,
        add: false,
        del: false,
        search: false,
        refresh: true
    });
    //$("#uploadform").fileUpload(function(){});
    $("#preview").click(function() {
        $("#uploadpreview").show();
        $("#uploadform").show();

        var options = {
            target: '#previewlist',
            beforeSubmit: function(data, set, options) {
                $('#dataDisplay').html($.toSource(data));

                return true;
            }
        };
        $('#uploadForm').ajaxSubmit(options);
        $('#uploadForm').bind('form.pre.serialize', function(type, $form, options, veto) {
            //if ($('#semanticControl').fieldValue().length != 0) options.semantic = true;
        });
        return false;
    });

    $("#cancelu").click(function() {
        $("#uploadpreview").hide();
        $("#uploadform").show();
    });
    $("#backu").click(function() {
        $("#uploadpreview").hide();
        $("#uploadform").show();
    });
    $("#saveu").click(function() {
        $("#msg").html("Please wait.....!<br /> This may take several minutes!");
        $("#msgdlg").dialog(dial2);
        $("#msgdlg").dialog('open');
        
        jQuery("#previewlist").setGridParam({
            url:'../JQUploadStudent?q=1&action=save',
            mtype:'GET'
        }).trigger("reloadGrid");        
        return false;
    });

//    $('#uploadForm').submit(function() {
//        // submit the form 
//        $(this).ajaxSubmit();
//        jQuery("#previewlist").trigger("reloadGrid");
//        $("#uploadpreview").show();
//        // return false to prevent normal browser submit and page navigation 
//        return false;
//    });
});
