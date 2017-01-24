var isAjaxRequestSent = false;

$('#user-ban-form').on('submit', function (e) {
    e.preventDefault();
    if (!isAjaxRequestSent) {
        isAjaxRequestSent = true;
        $.ajax({
            url: 'controller?cmd=async_ban',
            type: 'POST',
            dataType: 'text json',
            data: $('#user-ban-form').serialize(),
            success: function (response) {
                if (response.isResultSuccess) {
                    $('#result-alert-container')
                        .addResultAlert(
                            response.isResultSuccess,
                            STRINGS.success_alert,
                            response.isUserBanned
                                ? STRINGS.profile_ban_success_message
                                : STRINGS.profile_unban_success_message
                        );
                    $('#user-ban-btn').text(
                        response.isUserBanned
                            ? STRINGS.profile_button_unban
                            : STRINGS.profile_button_ban
                    );
                } else {
                    $('#result-alert-container')
                        .addResultAlert(
                            response.isResultSuccess,
                            response.errorTitle,
                            response.errorMessage
                        );
                }
                isAjaxRequestSent = false;
            },
            error: function (xhr, status, error) {
                $('#result-alert-container')
                    .addResultAlert(
                        false,
                        status ? status : STRINGS.error_alert,
                        error ? error : xhr.statusText
                    );
                isAjaxRequestSent = false;
            }
        });
    }
});

jQuery.fn.addResultAlert = function (isSuccess, resultTitle, resultMessage) {
    var alertHTML = '<div id="result-alert" class="alert alert-' +
        (isSuccess ? 'success ' : 'danger ') + 'alert-dismissable fade in">\
    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>\
    <strong>' + resultTitle + '</strong> ' + resultMessage + '\
</div>';
    this.html(alertHTML);
    if (isSuccess) {
        $('#result-alert').delay(1000).fadeOut(3000);
    }
};

$('#profile-image').on('error', function () {
    $(this).attr('src', 'img/default_avatar.png');
});
