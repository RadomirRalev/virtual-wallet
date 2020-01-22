$(function(){

    $('#txt_username').blur(function () {
        var username = $(this).val();
        $.ajax({
            url: "checkusername.php",
            method: "POST",
            data:{username:username},
            dataType:"text",
            success:function (html) {
                $('#availability').html(html)
            }
        });
    })
});