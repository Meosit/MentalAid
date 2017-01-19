var isAjaxRequestSent = false;

$("#username").focusout(function () {
    validateInputByRegex(this,
        STRINGS.register_username_constraints,
        /^[a-zA-Z_0-9]{3,45}$/
    );
});

$("#password").focusout(function () {
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

$("#password-confirm").focusout(function () {
    var elem = this;
    validateInputByCondition(this,
        STRINGS.register_password_not_match,
        function () {
            return elem.value === document.getElementById("password").value
        })
});

$("#email").focusout(function () {
    validateInputByRegex(this,
        STRINGS.register_email_invalid,
        /^[a-zA-Z_0-9]+@[a-zA-Z_0-9]+\.[a-zA-Z_0-9]+$/
    );
});

document.getElementById("registration-form").addEventListener("submit", function (e) {
    if (document.getElementById("error-div").childElementCount <= 1) {
        if (!isAjaxRequestSent) {
            isAjaxRequestSent = true;
            $.ajax({
                url: 'controller?cmd=async_register',
                type: 'POST',
                dataType: 'text json',
                data: $('#registration-form').serialize(),
                success: function (response) {
                    if (response.isResultSuccess) {
                        window.location.replace(decodeURI(response.redirectUrl));
                    } else {
                        $('#error-alert').removeClass('hidden');
                        $('#error-title').text(response.errorTitle);
                        $('#error-message').text(response.errorMessage);
                    }
                    isAjaxRequestSent = false;
                },
                error: function (xhr, status, error) {
                    $('#error-alert').removeClass('hidden');
                    $('#error-title').text(status ? status : STRINGS.error_alert);
                    $('#error-message').text(error ? error : xhr.statusText);
                    isAjaxRequestSent = false;
                }
            });
        }
    }
    e.preventDefault();
    return false;
});

$(document).ajaxSend(function () {
    var button = $('#register-button');
    button.attr('type', 'button');
    button.removeClass('btn-primary');
    button.addClass('btn-info');
    button.html("<span class=\"fa fa-spinner fa-spin\"></span> " + button.html());
});

$(document).ajaxComplete(function () {
    var button = $('#register-button');
    button.attr('type', 'submit');
    button.addClass('btn-primary');
    button.removeClass('btn-info');
    button.html(button.html().replace("<span class=\"fa fa-spinner fa-spin\"></span> ", ""));
});

function validateInputByRegex(inputElement, errorMessage, regex) {
    if (regex.test(inputElement.value.trim()) || inputElement.value.trim() === "") {
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

function addErrorMessageDiv(id, message) {
    var node = document.createElement("div");
    node.id = id;
    node.className = "alert alert-danger";
    node.innerHTML = "<strong>" + STRINGS.error_alert + "</strong> " + message;
    document.getElementById("error-div").appendChild(node);
}

function removeErrorMessageDiv(id) {
    var elem = document.getElementById(id);
    if (elem != null) {
        elem.remove();
    }
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