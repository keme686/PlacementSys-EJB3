/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function() {

    $('form').submit(function() {
        return false;
    });

    $("#infodiv").ready(function() {
        $.get("/StudentPlacementUI/PersonalInfoController?action=fetchData", {}, function(data) {
            if (data) {
                $('student', data).each(function(i) {
                    var firstName = $(this).find("firstName").text();
                    var middleName = $(this).find("middleName").text();
                    var lastName = $(this).find("lastName").text();
                    var classYear = $(this).find("classYear").text();
                    var semester = $(this).find("semester").text();
                    var prepaverage = $(this).find("prepaverage").text();
                    var eheeceaverage = $(this).find("eheeceaverage").text();
                    var eheeceregnum = $(this).find("eheeceregnum").text();
                    var email = $(this).find("email").text();
                    var country = $(this).find("country").text();
                    var zone = $(this).find("zone").text();
                    var woreda = $(this).find("woreda").text();
                    var town = $(this).find("town").text();
                    var mobile = $(this).find("mobile").text();
                    var idnum = $(this).find("idnum").text();
                    var gender = $(this).find("gender").text();
                    var region = $(this).find("region").text();
                    var cgpa = $(this).find("cgpa").text();
                    var disability = $(this).find("disability").text();

                    $('#firstName').val(firstName);
                    $('#middleName').val(middleName);
                    $('#lastName').val(lastName);
                    if (classYear != 'null')
                        $('#calssYear').val(classYear);
                    if (semester != 'null')
                        $('#semester').val(semester);
                    if (prepaverage != 'null')
                        $('#prepaverage').val(prepaverage);
                    if (eheeceaverage != 'null')
                        $('#eheeceaverage').val(eheeceaverage);
                    if (eheeceregnum != 'null')
                        $('#eheeceregnum').val(eheeceregnum);
                    if (email != 'null')
                        $('#email').val(email);
                    if (country != 'null')
                        $('#country').val(country);
                    if (zone != 'null')
                        $('#zone').val(zone);
                    if (woreda != 'null')
                        $('#woreda').val(woreda);
                    if (town != 'null')
                        $('#town').val(town);
                    if (mobile != 'null')
                        $('#mobile').val(mobile);
                    $('#idnum').val(idnum);
                    $('#gender').val(gender);
                    $('#cgpa').val(cgpa);
                    $('#region').val(region);
                    if (disability != 'null')
                        $('#disability').val(disability);
                });
            }
        });

    });
    $('#updateinfobtn').click(function() {
        $('#personalinfoform').validate({
            rules: {
                firstName: {
                    required: true
                },
                middleName: {
                    required: true
                },
                lastName: {
                    required: true
                }
            }, messages: {
                firstName: "Please enter first name",
                middleName: "Please enter middle name",
                lastName: "Please enter last name"
            }
        });
        var isValid = $('#personalinfoform').valid();

        if (isValid === true) {
            var firstName = $('#firstName').val();
            var middleName = $('#middleName').val();
            var lastName = $('#lastName').val();
            var classYear = $('#calssYear').val();
            var semester = $('#semester').val();
            var prepaverage = $('#prepaverage').val();
            var eheeceaverage = $('#eheeceaverage').val();
            var eheeceregnum = $('#eheeceregnum').val();
            var email = $('#email').val();
            var country = $('#country').val();
            var zone = $('#zone').val();
            var woreda = $('#woreda').val();
            var town = $('#town').val();
            var mobile = $('#mobile').val();
            var userdata = {};
            userdata.firstName = firstName;
            userdata.middleName = middleName;
            userdata.lastName = lastName;
            userdata.classYear = classYear;
            userdata.semester = semester;
            userdata.prepaverage = prepaverage;
            userdata.eheeceaverage = eheeceaverage;
            userdata.eheeceregnum = eheeceregnum;
            userdata.email = email;
            userdata.country = country;
            userdata.zone = zone;
            userdata.woreda = woreda;
            userdata.town = town;
            userdata.mobile = mobile;

            $.post("/StudentPlacementUI/PersonalInfoController?action=updateData", userdata, function(data) {
                if (data) {
                    $('info', data).each(function(i) {
                        var status = $(this).find("status").text();
                        if (status == '1') {
                            alert("success");
                        }
                        if (status == '0') {
                            $("#errormsg").html("Invalid information provided!");
                        }
                        else if (status == '-1') {
                            $("#errormsg").html("Something went wrong, please review your information correctly!");
                        }
                    });
                }
            });
        }
        return false;
    });
});
