$('#login-form').submit(function (e) {
    $.ajax({
        url: 'controller?cmd=login',
        type: 'POST',
        dataType: 'text json',
        data: $('#login-form').serialize(),
        success: function (response) {
            response = JSON.parse(response);
            if (response.status == "fail") {
                $('#error-alert').removeClass('hidden');
                $('#error-title').text(response.errorTitle);
                $('#error-message').text(response.errorMessage);
            } else {
                window.location.replace(response.redirectUrl);
            }
        }
    });
    e.preventDefault();
    return false;
});