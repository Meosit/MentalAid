$('#login-form').submit(function (e) {
    $.ajax({
        url: 'controller?cmd=async_login',
        type: 'POST',
        dataType: 'text json',
        data: $('#login-form').serialize(),
        success: function (response) {
            if (response.isResultSuccess) {
                window.location.replace(decodeURI(response.redirectUrl));
            } else {
                $('#error-alert').removeClass('hidden');
                $('#error-title').text(response.errorTitle);
                $('#error-message').text(response.errorMessage);
            }
        },
        error: function (request, status, error) {
            $('#error-alert').removeClass('hidden');
            $('#error-title').text(status);
            $('#error-message').text(error);
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
    button.addClass('btn-primary');
    button.removeClass('btn-info');
    button.html(button.html().replace("<span class=\"fa fa-spinner fa-spin\"></span> ", ""));
});