var isAjaxRequestSent = false;

$('#change-password-form').on('submit', function (e) {
    e.preventDefault();
    var elem = $('#new-password-confirm').get();
    validateInputByCondition(elem,
        STRINGS.register_password_not_match,
        function () {
            return elem.value === document.getElementById("new-password").value
        });
    if (document.getElementById("change-password-error-div").childElementCount == 0) {
        if (!isAjaxRequestSent) {
            isAjaxRequestSent = true;
            $.ajax({
                url: 'controller?cmd=async_change_password',
                type: 'POST',
                dataType: 'text json',
                data: $('#change-password-form').serialize(),
                success: function (response) {
                    if (response.isResultSuccess) {
                        $('#change-password-result-div').addResultAlert(
                            response.isResultSuccess,
                            STRINGS.success_alert,
                            STRINGS.change_password_success_message
                        );
                    } else {
                        $('#change-password-result-div').addResultAlert(
                            response.isResultSuccess,
                            response.errorTitle,
                            response.errorMessage
                        );
                    }
                    isAjaxRequestSent = false;
                },
                error: function (xhr, status, error) {
                    $('#change-password-result-div').addResultAlert(
                        false,
                        status ? status : STRINGS.error_alert,
                        error ? error : xhr.statusText
                    );
                    isAjaxRequestSent = false;
                }
            });
        }
    }
});

$('#profile-update-form').on('submit', function (e) {
    e.preventDefault();
    if (!isAjaxRequestSent) {
        isAjaxRequestSent = true;
        $.ajax({
            url: 'controller?cmd=async_update_profile',
            type: 'POST',
            dataType: 'text json',
            data: $('#profile-update-form').serialize(),
            success: function (response) {
                if (response.isResultSuccess) {
                    $('#profile-update-result-div').addResultAlert(
                        response.isResultSuccess,
                        STRINGS.success_alert,
                        STRINGS.profile_update_success_message
                    );
                } else {
                    $('#profile-update-result-div').addResultAlert(
                        response.isResultSuccess,
                        response.errorTitle,
                        response.errorMessage
                    );
                }
                isAjaxRequestSent = false;
            },
            error: function (xhr, status, error) {
                $('#profile-update-result-div').addResultAlert(
                    false,
                    status ? status : STRINGS.error_alert,
                    error ? error : xhr.statusText
                );
                isAjaxRequestSent = false;
            }
        });
    }
});


$("#new-password").focusout(function () {
    var elem = this;
    validateInputByCondition(this,
        STRINGS.register_password_constraints,
        function () {
            return elem.value.length >= 6
                && elem.value.length <= 60
                && hasUppercaseChar(elem.value)
                && hasLowercaseChar(elem.value)
                && hasNumber(elem.value)
        })
});

$("#new-password-confirm").focusout(function () {
    var elem = this;
    validateInputByCondition(this,
        STRINGS.register_password_not_match,
        function () {
            return elem.value === document.getElementById("new-password").value
        })
});

$('#image-url').focusout(function () {
    if ($(this).val() !== '') {
        $(this).val(getAbsoluteUrl($(this).val()));
        $('#avatar-test-image').attr('src', $(this).val());
    }
}).focusin(function () {
    $(this).select()
});

$('#website-url').focusout(function () {
    $(this).val(getAbsoluteUrl($(this).val()));
}).focusin(function () {
    $(this).select()
});

$('#avatar-test-image').on('error', function () {
    $(this).attr('src', 'img/default_avatar.png');
});

function validateInputByCondition(inputElement, errorMessage, conditionFunction) {
    if (conditionFunction() || inputElement.value.trim() === "") {
        if (inputElement.value.trim() === "") {
            inputElement.style.removeProperty("background-color");
        } else {
            inputElement.style.backgroundColor = "#dff0d8";
        }
        if (document.getElementById(inputElement.id + "-error-div") != null) {
            removeErrorMessageDiv(inputElement.id + "-error-div");
        }
    } else {
        inputElement.style.backgroundColor = "#f2dede";
        if (document.getElementById(inputElement.id + "-error-div") == null) {
            addErrorMessageDiv(inputElement.id + "-error-div", errorMessage);
        }
    }
}

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


function addErrorMessageDiv(id, message) {
    var node = document.createElement("div");
    node.id = id;
    node.className = "alert alert-danger";
    node.innerHTML = "<strong>" + STRINGS.error_alert + "</strong> " + message;
    document.getElementById("change-password-error-div").appendChild(node);
}

function removeErrorMessageDiv(id) {
    var elem = document.getElementById(id);
    if (elem != null) {
        elem.remove();
    }
}

function getAbsoluteUrl(url) {
    url = url.trim();
    if (url.length == 0) {
        return "";
    }
    if (url.search(/^http[s]?\:\/\//) == -1) {
        url = 'http://' + url;
    }
    var a;
    if (!a) a = document.createElement('a');
    a.href = url;
    return a.href;
}

Element.prototype.remove = function () {
    this.parentElement.removeChild(this);
};

function hasUppercaseChar(str) {
    return str.toLowerCase() != str.value;
}

function hasLowercaseChar(str) {
    return str.toLowerCase() != str.value;
}

function hasNumber(str) {
    return /\d/.test(str);
}
