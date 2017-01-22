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
            return elem.value === document.getElementById("old-password").value
        })
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
