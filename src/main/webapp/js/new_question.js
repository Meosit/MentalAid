const MAX_QUESTION_TITLE_LENGTH = 200;
const MAX_QUESTION_DESCRIPTION_LENGTH = 2000;

var isValidInput = true;
var isAjaxRequestSent = false;

$('#new-question-form').submit(function (e) {
    e.preventDefault();
    if ($('.alert-ui').length == 0 && !isAjaxRequestSent) {
        isAjaxRequestSent = true;
        $.ajax({
            url: "controller?cmd=async_question_add",
            type: 'POST',
            dataType: 'text json',
            data: $('#new-question-form').serialize(),
            success: function (response) {
                if (response.isResultSuccess) {
                    window.location.replace(decodeURI(response.redirectUrl));
                } else {
                    $('#error-div')
                        .addErrorAlert(
                            response.errorTitle,
                            response.errorMessage
                        );
                }
                isAjaxRequestSent = false;
            },
            error: function (xhr, status, error) {
                $('#error-div')
                    .addErrorAlert(
                        status ? status : STRINGS.error_alert,
                        error ? error : xhr.statusText
                    );
                isAjaxRequestSent = false;
            }
        });
        isValidInput = true;
    }
});

$('#question-title-input').focusout(function () {
    var inputVal = $(this).val();
    validateInputByCondition(this,
        function () {
            if (isBlank(inputVal)) {
                return STRINGS.question_title_empty;
            } else if (inputVal.length > MAX_QUESTION_TITLE_LENGTH) {
                return STRINGS.question_title_too_long + inputVal.length;
            } else {
                return null;
            }
        });
});

$('#question-description-input').focusout(function () {
    var inputVal = $(this).val();
    validateInputByCondition(this,
        function () {
            if (isBlank(inputVal)) {
                return STRINGS.question_description_empty;
            } else if (inputVal.length > MAX_QUESTION_DESCRIPTION_LENGTH) {
                return STRINGS.question_description_too_long + inputVal.length;
            } else {
                return null;
            }
        });
});

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

jQuery.fn.addErrorAlert = function (resultTitle, resultMessage) {
    var alertHTML = '<div id="question-result-alert" class="alert alert-danger alert-dismissable fade in">\
    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>\
    <strong>' + resultTitle + '</strong> ' + resultMessage + '\
</div>';
    this.append(alertHTML);
};

jQuery.fn.hideElem = function () {
    this.addClass('hidden');
};

jQuery.fn.showElem = function () {
    this.removeClass('hidden');
};

function validateInputByCondition(inputElement, getErrorStringFunction) {
    var errorMessageString = getErrorStringFunction();
    if (errorMessageString == null) {
        inputElement.style.backgroundColor = "#dff0d8";
        if (document.getElementById(inputElement.id + "-error-div") != null) {
            removeErrorMessageDiv(inputElement.id + "-error-div");
        }
    } else {
        inputElement.style.backgroundColor = "#f2dede";
        if (document.getElementById(inputElement.id + "-error-div") == null) {
            addErrorMessageDiv(inputElement.id + "-error-div", errorMessageString);
        } else {
            removeErrorMessageDiv(inputElement.id + "-error-div");
            addErrorMessageDiv(inputElement.id + "-error-div", errorMessageString);
        }
    }
}

function addErrorMessageDiv(id, message) {
    var node = document.createElement("div");
    node.id = id;
    node.className = "alert-ui alert alert-danger";
    node.innerHTML = "<strong>" + STRINGS.error_alert + "</strong> " + message;
    document.getElementById("error-div").appendChild(node);
}

function removeErrorMessageDiv(id) {
    var elem = document.getElementById(id);
    if (elem != null) {
        elem.remove();
    }
}

$(document).ajaxSend(function () {
    var button = $('#create-question-button');
    button.attr('type', 'button');
    button.removeClass('btn-primary');
    button.addClass('btn-info');
    button.html("<span class=\"fa fa-spinner fa-spin\"></span> " + button.html());
});

$(document).ajaxComplete(function () {
    var button = $('#create-question-button');
    button.attr('type', 'submit');
    button.addClass('btn-primary');
    button.removeClass('btn-info');
    button.html(button.html().replace("<span class=\"fa fa-spinner fa-spin\"></span> ", ""));
});

