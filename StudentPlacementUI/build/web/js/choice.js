/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery().ready(function() {
    var departments = new Array();
    var Ok = function() {
        $("#msgdlg").dialog('close');
    };
    var dial = {
        autoOpen: false,
        title: 'Warning',
        modal: true,
        buttons: {
            "Ok": Ok
        }
    };

    $("#deptstochooselist").ready(function() {
        $.get("/StudentPlacementUI/StudentChoiceController?action=fetchData", {}, function(data) {
            if (data) {
                var j = 0;
                $('department', data).each(function(i) {
                    var name = $(this).find("name").text();
                    var id = $(this).find("id").text();
                    var code = $(this).find("code").text();
                    var department = {};
                    department.id = id;
                    department.name = name;
                    department.code = code;
                    departments[j++] = department;
                    $("#deptstochooselist").append("<li class=\"ui-state-default\"><span class=\"ui-icon ui-icon-arrowthick-2-n-s\"></span>" + code + " </li>");
                });
            }
        });
    });
    $("#deptstochooselist").sortable();
    $("#deptstochooselist").disableSelection();
    $("#updatechoice").click(function() {
        var k = 0;
        $("#deptstochooselist li").each(function(i) {
            var code = $(this).text();
            for (var i = 0; i < departments.length; i++) {
                var department = departments[i];
                if (department.code.trim() == code.trim()) {
                    department.rank = ++k;
                    departments[i] = department;
                }
            }
        });
        for (var i = 0; i < departments.length; i++) {
            var department = departments[i];
            $.post("/StudentPlacementUI/StudentChoiceController?action=updateData", department, function(data) {
                if (data) {
                    $('info', data).each(function(i) {
                        var status = $(this).find("status").text();
                        if (status == '-1') {
                            $("#msg").html("You cannot make choice after placement is made!");
                            $("#msgdlg").dialog(dial);
                            $("#msgdlg").dialog('open');
                        }else if(status =='1'){
                            $("#msg").html("Your choice have been submited!");
                            $("#msgdlg").dialog(dial);
                            $("#msgdlg").dialog('open');
                        }else{
                            $("#msg").html("Your choice cannot saved due to some reason! Please report this error for admin!");
                            $("#msgdlg").dialog(dial);
                            $("#msgdlg").dialog('open');
                        }

                    });
                }
            });
        } //end of for loop for posting choice        
    });


});

