document.getElementById("username").addEventListener("blur", function () {
    validateInputByRegex(this,
        "Username can consists only latin letters, digits and underscore, length 5-45 chars.",
        /^[a-zA-Z_0-9]{5,45}$/
    );
});

document.getElementById("password").addEventListener("blur", function () {
    var elem = this;
    validateInputByCondition(this,
        "Password must contain at least one upper-case and lower-case letter, at least one digit, length 6-60 chars.",
        function () {
            return elem.value.length >= 6
                && elem.value.length <= 60
                && hasUppercaseChar(elem.value)
                && hasLowercaseChar(elem.value)
                && hasNumber(elem.value)
        })
});

document.getElementById("password-confirm").addEventListener("blur", function () {
    var elem = this;
    validateInputByCondition(this,
        "Passwords not match.",
        function () {
            return elem.value === document.getElementById("password").value
        })
});

document.getElementById("email").addEventListener("blur", function () {
    validateInputByRegex(this,
        "Invalid email format.",
        /^[a-zA-Z_0-9]+@[a-zA-Z_0-9]+\.[a-zA-Z_0-9]+$/
    );
});

document.getElementById("registration-form").addEventListener("submit", function (e) {
    if (document.getElementById("error-div").innerHTML !== "") {
        e.preventDefault();
        return false;
    }
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
    node.innerHTML = "<strong>Error!</strong> " + message;
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