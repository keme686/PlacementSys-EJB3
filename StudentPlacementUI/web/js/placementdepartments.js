/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery().ready(function (){
  
     jQuery("#departmentlist").jqGrid({     
        url:'/StudentPlacementUI/PlacementDepartmentController?q=1&action=fetchData&collegeId=0',
        datatype: "xml",   
        mtype: 'GET',
        // async: true,
        colNames:['id', 'Code','Name','Capacity','Address','Description'],
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
        },
        {
            name:'capacity',
            index:'capacity', 
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
        },
        {
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
        caption: "Departments"
       // editurl: '/StudentPlacementUI/PlacementDepartmentController?q=1&action=updateData'
    }).navGrid("#departmentpager", {
        view: false, 
        edit: false, 
        add: false, 
        del: false, 
        search: false, 
        refresh: true
    });   
});

