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
     jQuery("#departmentlist").jqGrid({     
        url:'/StudentPlacementUI/DepartmentController?q=1&action=fetchData&collegeId=0',
        datatype: "xml",   
        mtype: 'GET',
        // async: true,
        colNames:['id', 'Code','Name','Address','Description', 'College','College'],
        colModel:[                
        {
            name:'id',
            index:'id',
            width: 50,  
            hidden:true
        },

        {
            name:'code',
            index:'code',
            align:'left', 
            width: 40, 
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
            name:'name',
            index:'name', 
            align:'left', 
            width: 90,
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:false
        },{
            name:'address',
            index:'address', 
            align:'left', 
            width: 90,
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:false
            }, 
            hidden:false
        },{
            name:'description',
            index:'description', 
            align:'left', 
            width: 90,
            sortable:true, 
            editable: true, 
            edittype: 'text', 
            editrules:{
                edithidden: true, 
                required:false
            }, 
            hidden:false
        },
        {
            name:'college',
            index:'college', 
            align:'lef', 
            width: 90,
            sortable:true, 
            editable: false,          
            hidden:false
        },

        {
            name:'collegeId',
            index:'collegeId', 
            align:'left', 
            width: 90,
            sortable:true, 
            editable: true, 
            edittype: 'select', 
            editoptions: {
                dataUrl:   '/StudentPlacementUI/GridDataController?q=editgrid&data=college&action=all'
            }, 
            editrules:{
                edithidden: true, 
                required:true
            }, 
            hidden:true
        }                                    
        ], 
        pager: $("#departmentpager"),                   
        rowNum: 10,
        height: 'auto',   
        width: '100%',
        autowidth: true,
        page: 1,                      
        sortname: 'code',
        sortorder:'asc',
        loadtext: "Loading...",
        rowList:[5,10,20,30],                                                                      
        toolbar: [false,'bottom'],
        viewrecords: true,     
        rownumbers: true,
        caption: "Departments",         
        editurl: '/StudentPlacementUI/DepartmentController?q=1&action=updateData'
    }).navGrid("#departmentpager", {
        view: false, 
        edit: false, 
        add: false, 
        del: false, 
        search: false, 
        refresh: true
    });
                
    $('#newdepartment').click(function(){        
        jQuery("#departmentlist").editGridRow('new');
    });
        
    $('#editdepartment').click(function(){
        var selectedrow = jQuery("#departmentlist").getGridParam('selrow');
        if(selectedrow != null)
            jQuery("#departmentlist").editGridRow(selectedrow);
        else{
            $("#msg").html("Please Select a department!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });
        
    $('#deletedepartment').click(function(){
        var selectedrow = jQuery("#departmentlist").getGridParam('selrow');
        if(selectedrow != null)
            jQuery("#departmentlist").delGridRow(selectedrow);
        else{
            $("#msg").html("Please Select a department!");
            $("#msgdlg").dialog(dial2);
            $("#msgdlg").dialog('open');
        }
    });        
    $('#refreshdepartment').click(function(){           
        jQuery("#departmentlist").trigger("reloadGrid");
    }); 
});

