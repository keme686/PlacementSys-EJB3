/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery().ready(function() {
    $("#resultsummary").ready(function() {
        $.get("/StudentPlacementUI/PersonalInfoController?action=resultData", {}, function(data) {
            if (data) {
                $('student', data).each(function(i) {
                    var name = $(this).find("name").text();
                    var department = $(this).find("department").text();
                    var reason = $(this).find("reason").text();
                    var idnum = $(this).find("idnum").text();
                    var gender = $(this).find("gender").text();
                    var region = $(this).find("region").text();
                    var cgpa = $(this).find("cgpa").text();
                    var disability = $(this).find("disability").text();

                    $('#fullname').val(name);
                    if (department != "null")
                        $('#departmentplaced').val(department);
                    if (reason != "null")
                        $('#placedreason').val(reason);
                    $('#ridnum').val(idnum);
                    $('#rgender').val(gender);
                    $('#rcgpa').val(cgpa);
                    $('#rregion').val(region);
                    if (disability != 'null')
                        $('#rdisability').val(disability);
                    var choices = $(this).find("choices");
                    $('choice', choices).each(function(j) {
                        var rank = $(this).find("rank").text();
                        var departmentchoice = $(this).find("departmentchoice").text();
                        $("#choiceslist").append("<li>"+ departmentchoice + ", Rank:  " + rank + "</li>");
                    });
                });
            }
        });

    });
});
