/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function (){
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
        width:'auto',
        reloadAfterSubmit: true,           
        modal: true,
        msg: { 
            required:"Field is required", 
            number:"Please enter valid number!", 
            minValue:"value must be greater than or equal to ", 
            maxValue:"value must be less than or equal to"
        } 
    };
    jQuery("#studentlist").jqGrid({     
        url:'/StudentPlacementUI/StudentController?q=1&action=fetchData',
        datatype: "xml",      
        mtype: 'GET',
        colNames:['id','Id Number','First Name','Middle Name','Last Name', 'Name','Sex','disability','region','cgpa','Department','Placement'],
        colModel:[
        {
            name:'id',
            index:'id',
            width: 50,
            editable: true,  
            hidden:true
        },

        {
            name:'idnum',
            index:'idnum',
            align:'center', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },

        {
            name:'fname',
            index:'fname',
            align:'center', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:true
        },

        {
            name:'mname',
            index:'mname',
            align:'center', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:true
        },

        {
            name:'lname',
            index:'lname',
            align:'center', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:true
        },

        {
            name:'name',
            index:'name',
            align:'left',  
            sortable:true, 
            editable: false, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },

        {
            name:'sex',
            index:'sex',
            align:'left', 
            width: 50, 
            sortable:true, 
            editable: true, 
            edittype: 'select',
            editoptions:{
                value:'F:Female;M:Male'
            }, 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },

        {
            name:'disability',
            index:'disability',
            align:'left',
            width: 70,  
            sortable:true, 
            editable: true, 
            edittype: 'select',
            editoptions:{
                value:'None:None;Hearing:Hearing;Vision:Vision;Handycape:Handycape;Other:Other'
            }, 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },

        {
            name:'region',
            index:'region',
            align:'left', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'select',
            editoptions:{
                value:'SOMALI:SOMALI;GAMBELLA:GAMBELLA;BENSHANGUL:BENSHANGUL;AFAR:AFAR;SNNP:SNNP;AMHARA:AMHARA;OROMIA:OROMIA;TIGRI:TIGRI;OTHER:OTHER'
            }, 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },

        
        {
            name:'cgpa',
            index:'cgpa',
            align:'left', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },       
        {
            name:'department',
            index:'department',
            align:'left', 
            width: 70, 
            sortable:true, 
            editable: false, 
            edittype: 'select',
            editoptions:{
                dataUrl:'/StudentPlacementUI/GridDataController?q=editgrid&data=department&action=all'
            },  
            editrules:{
                edithidden: true, 
                required:false
            }, 
            hidden:false
        },

        {
            name:'placement',
            index:'placement',
            align:'left', 
            width: 70, 
            sortable:true, 
            editable: true, 
            edittype: 'select',
            editoptions:{
                dataUrl:'/StudentPlacementUI/GridDataController?q=editgrid&data=placement&action=all'
            },  
            editrules:{
                edithidden: true, 
                required:false
            }, 
            hidden:false
        }
        ],
        pager: $("#studentpager"),                  
        rowNum: 10,
        height: 'auto',  
        width: '100%',
        autowidth: true,
        page: 1,                      
        sortname: 'id',
        sortorder:'asc',
        loadtext: "Loading...",
        rowList:[10,20,30,50,100],
        imgpath: "../css/images",                                          
        caption: "Students",          
        toolbar: [false,'bottom'],
        viewrecords: true,
        rownumbers: true,
        editurl: '/StudentPlacementUI/StudentController?q=1&action=updateData',
        onSelectRow: function(id){
            if(id && id != null){
                    
            }
        },
        subGrid: true,
        subGridRowExpanded: function(subgridId,rowId){
            var subgidtableid = subgridId +"_t";
            jQuery("#"+subgridId).html("<table id=\""+ subgidtableid +"\" class=\"scroll\"></table>");
            jQuery("#"+subgidtableid).jqGrid({ 
                url: '/StudentPlacementUI/ChoiceController?q=1&action=fetchData&studentid='+rowId,
                datatype: "xml",
                colNames: ['id','Department Code','Department Name','Rank'],
                colModel:[
                {
                    name:'id',
                    index:'id',
                    width: 50,
                    editable: true,  
                    hidden:true
                },

                {
                    name:'departmentcode',
                    index:'departmentcode',
                    align:'left', 
                    width: 100, 
                    sortable:true, 
                    editable: true, 
                    edittype: 'text', 
                    editrules:{
                        edithidden: true, 
                        required:true
                    }, 
                    hidden:false
                },

                {
                    name:'departmentname',
                    index:'departmentname',
                    align:'left', 
                    width: 220, 
                    sortable:true, 
                    editable: true, 
                    edittype: 'text', 
                    editrules:{
                        edithidden: true, 
                        required:true
                    }, 
                    hidden:false
                },

                {
                    name:'rank',
                    index:'rank',
                    align:'center', 
                    width: 70, 
                    sortable:true, 
                    editable: true, 
                    edittype: 'text', 
                    editrules:{
                        edithidden: true, 
                        required:true
                    }, 
                    hidden: false
                }                            
                ],
                height: '100%',
                rowNum:20,
                caption: 'Choices',
                autowidth: false,
                rownumbers: true
            })                                        
        }
    }).navGrid("#studentpager", {
        view: false, 
        edit: false, 
        add: false, 
        del: false, 
        search: false, 
        refresh: true
    });
                
    $('#newstudent').click(function(){
        jQuery("#studentlist").editGridRow('new');
    });

    var Ok = function () {
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

    $('#editstudent').click(function(){
        var selectedrow = jQuery("#studentlist").getGridParam('selrow');
        if(selectedrow != null)
            jQuery("#studentlist").editGridRow(selectedrow);
        else{
            $("#msg").html("Please Select a student!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
        
    $('#deletestudent').click(function(){
        var selectedrow = jQuery("#studentlist").getGridParam('selrow');
        if(selectedrow != null)
            jQuery("#studentlist").delGridRow(selectedrow);
        else{
            $("#msg").html("Please Select a student!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
        
    $('#refreshstudent').click(function(){           
        jQuery("#studentlist").trigger("reloadGrid");
    }); 
});
