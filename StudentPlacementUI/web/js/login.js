/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function() {

    $('form').submit(function() {
        return false;
    });
    
    $('#loginbtn').click(function() {
                
        $('#loginform').validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            }, messages: {
                username: "Please enter username",
                password: "Please enter password"
            }
        });
        var isValid = $('#loginform').valid();

        if (isValid === true) {
            var username = $('#username').val();
            var passwd = $('#password').val();
            var userdata = {};
            userdata.username = username;
            userdata.password = passwd;

            $.post("LoginController?action=login", userdata, function(data) {
                if (data) {

                    $('info', data).each(function(i) {
                        var status = $(this).find("status").text();
                        if (status == '-1') {
                            $("#errormsg").html("Invalid username or password!<br/> (OR Your account is not active! please make sure placement is active for students)");
                            $('#username').val('');
                            $('#password').val('');
                        } else if (status == '1') {
                            var url = $(this).find("url").text();
                            window.location.replace(url);
                        } else if (status == '-5') {
                            $("#errormsg").html("Number of login attempts expired! please try later!");
                            $('#username').val('');
                            $('#password').val('');
                        }
                    });
                }
            });
        }
        return false;
    });
});
