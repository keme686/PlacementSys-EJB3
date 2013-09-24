/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery().ready(function() {

    $('form').submit(function() {
        return false;
    });

    $('#changepwdbtn').click(function() {
        $('#changepwdform').validate({
            rules: {
                currentpwd: {
                    required: true
                },
                newpwd: {
                    required: true
                },
                repeatpwd: {
                    required: true
                }
            }, messages: {
                currentpwd: "Please enter current password",
                newpwd: "Please enter middle name",
                repeatpwd: "Please enter last name"
            }
        });
        var isValid = $('#changepwdform').valid();

        if (isValid === true) {
            $("#errormsg").html("");
            var current = $("#currentpwd").val();
            var newpass = $("#newpwd").val();
            var repeat = $("#repeatpwd").val();
            if (newpass.trim() != repeat.trim()) {
                $("#errormsg").html("new password and repeated password didnt match!");
            } else {                
                var userdata = {};
                userdata.currentpwd = current;
                userdata.newpwd = newpass;
                 $.post("/StudentPlacementUI/LoginController?action=updateData", userdata, function(data) {
                if (data) {
                    $('info', data).each(function(i) {
                        var status = $(this).find("status").text();
                        if (status == '1') {
                            alert("Success!");
                        }else if (status == '0') {
                            $("#errormsg").html("Invalid password information provided!");
                        }
                        else if (status == '-1') {
                            $("#errormsg").html("Something went wrong, please review your information correctly!");
                        }
                    });
                }
            });            
            }
            $("#currentpwd").val("");
            $("#newpwd").val("");
            $("#repeatpwd").val("");
        }
    });

});