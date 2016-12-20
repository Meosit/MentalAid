$('#login-form').submit(function (e) {
    $.ajax({
        url: 'controller?cmd=login',
        type: 'POST',
        dataType: 'text json',
        data: $('#login-form').serialize(),
        success: function (response) {
            if (response.status == "fail") {
                $('#error-alert').removeClass('hidden');
                $('#error-title').text(response.errorTitle);
                $('#error-message').text(response.errorMessage);
            } else {
                window.location.replace("index.jsp");
            }
        }
    });
    e.preventDefault();
    return false;
});

$(document).ajaxSend(function () {
    var button = $('#login-button');
    button.attr('type', 'button');
    button.removeClass('btn-primary');
    button.addClass('btn-info');
    button.html("<span class=\"fa fa-spinner fa-spin\"></span> " + button.html());
});

$(document).ajaxComplete(function () {
    var button = $('#login-button');
    button.attr('type', 'submit');
    button.removeClass('btn-primary');
    button.addClass('btn-info');
    button.html(button.html().replace("<span class=\"fa fa-spinner fa-spin\"></span> ", ""));
});