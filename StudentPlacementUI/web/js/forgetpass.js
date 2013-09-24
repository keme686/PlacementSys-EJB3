/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function() {

    $('form').submit(function() {
        return false;
    });
    var Ok = function() {
        $("#msgdlg").dialog('close');
        window.location.replace(url);        
    };
    var dial2 = {
        autoOpen: false,
        title: 'Info',
        modal: true,
        buttons: {
            "Ok": Ok
        }
    };

    var url = "";
    $('#submitbtn').click(function() {

        $('#forgetpassform').validate({
            rules: {
                idnum: {
                    required: true
                },
                email: {
                    required: true
                }
            }, messages: {
                idnum: "Please enter ID Number",
                email: "Please enter Email"
            }
        });
        var isValid = $('#forgetpassform').valid();

        if (isValid === true) {
            var idnum = $('#idnum').val();
            var email = $('#email').val();
            var userdata = {};
            userdata.idnum = idnum;
            userdata.email = email;

            $.post("ForgetPasswordController?action=reset", userdata, function(data) {
                if (data) {
                    $('info', data).each(function(i) {
                        var status = $(this).find("status").text();
                        if (status == '-1') {
                            $("#errormsg").html("Invalid id number and/or email!<br/>");
                            $('#idnum').val('');
                            $('#email').val('');
                        } else if (status == '1') {
                            url = $(this).find("url").text();
                            $("#msg").html("Password reset successfully! Please check your email about the details!");
                            $("#msgdlg").dialog(dial2);
                            $("#msgdlg").dialog('open');                           
                        }
                    });
                }
            });
        }
        return false;
    });
});


