var isValidInput = true;

$('#question-edit-btn').on('click', function (e) {
    e.preventDefault();
    $('#question-title-edit').val($('#question-title').text());
    $('#question-description-edit').val($('#question-description').text());
    $('#question-edit-btn, #question-delete-btn, #question-div').hide();
    $('#question-apply-btn, #question-cancel-btn, #question-edit-div').show();
});

$('#question-cancel-btn').on('click', function (e) {
    e.preventDefault();
    $('#question-edit-btn, #question-delete-btn, #question-div').show();
    $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hide();
});

$('#question-apply-btn').on('click', function (e) {
    e.preventDefault();
    if (isValidInput) {
        if ($('#question-title').text() !== $('#question-title-edit').val() ||
            $('#question-description').text() !== $('#question-description-edit').val()) {
            $.ajax({
                url: 'controller?cmd=async_question_edit',
                type: 'POST',
                dataType: 'text json',
                data: $('#question-edit-form').serialize(),
                success: function (response) {
                    if (response.isResultSuccess) {
                        $('#question-title').text($('#question-title-edit').val());
                        $('#question-description')
                            .text($('#question-description-edit').val())
                            .collapseTextNewLinesAndTrim();
                        $('#question-edit-date-container').show();
                        $('#question-edit-date').text(response.modifiedAt);
                        $('#question-result-alert-container')
                            .addResultAlert(
                                response.isResultSuccess,
                                STRINGS.success_alert,
                                STRINGS.question_edit_success_message
                            );
                        $('#question-edit-btn, #question-delete-btn, #question-div').show();
                        $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hide();
                    } else {
                        $('#question-result-alert-container')
                            .addResultAlert(
                                response.isResultSuccess,
                                response.errorTitle,
                                response.errorMessage
                            );
                    }
                },
                error: function (xhr, status, error) {
                    $('#question-result-alert-container')
                        .addResultAlert(
                            false,
                            status ? status : STRINGS.error_alert,
                            error ? error : xhr.statusText
                        );
                }
            });
        } else {
            $('#question-edit-btn, #question-delete-btn, #question-div').show();
            $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hide();
        }
        isValidInput = true;
    }
});


$('#question-description-edit').focusout(validateQuestionFields);
$('#question-title-edit').focusout(validateQuestionFields);
function validateQuestionFields() {
    if (isBlank(this.val())) {
        isValidInput = false;
        $('#question-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.question_wrong_input_message
            );
    } else {
        $('#question-result-alert-container').html('');
        isValidInput = true;
    }
}

$('.answer-edit-btn').on('click', function (e) {
    e.preventDefault();
    var answerId = extractAnswerId(this);
    $('#answer-text-edit-' + answerId).val($('#answer-text-' + answerId).text());
    $('#answer-edit-' + answerId + ', #answer-delete-' + answerId + ', #answer-text-' + answerId).hide();
    $('#answer-apply-' + answerId + ', #answer-cancel-' + answerId + ', #answer-text-edit-container-' + answerId).show();
});

$('.answer-cancel-btn').on('click', function (e) {
    e.preventDefault();
    var answerId = extractAnswerId(this);
    $('#answer-edit-' + answerId + ', #answer-delete-' + answerId + ', #answer-text-' + answerId).show();
    $('#answer-apply-' + answerId + ', #answer-cancel-' + answerId + ', #answer-text-edit-container-' + answerId).hide();
});


$('.answer-apply-btn').on('click', function (e) {
    e.preventDefault();
    var answerId = extractAnswerId(this);
    if (isValidInput) {
        if ($('#answer-text-' + answerId).text() !== $('#answer-text-edit-' + answerId).val()) {
            $.ajax({
                url: 'controller?cmd=async_answer_edit',
                type: 'POST',
                dataType: 'text json',
                data: $('#answer-text-edit-form-' + answerId).serialize(),
                success: function (response) {
                    if (response.isResultSuccess) {
                        $('#answer-text-' + answerId)
                            .text($('#answer-text-edit-' + answerId).val())
                            .collapseTextNewLinesAndTrim();
                        $('#answer-edit-date-container-' + answerId).show();
                        $('#answer-edit-date-' + answerId).text(response.modifiedAt);
                        $('#answer-result-alert-container' + answerId)
                            .addResultAlert(
                                response.isResultSuccess,
                                STRINGS.success_alert,
                                STRINGS.answer_edit_success_message
                            );
                        $('#answer-edit-' + answerId + ', #answer-delete-' + answerId + ', #answer-text-' + answerId).show();
                        $('#answer-apply-' + answerId + ', #answer-cancel-' + answerId + ', #answer-text-edit-container-' + answerId).hide();
                    } else {
                        $('#answer-result-alert-container' + answerId)
                            .addResultAlert(
                                response.isResultSuccess,
                                response.errorTitle,
                                response.errorMessage
                            );
                    }
                },
                error: function (xhr, status, error) {
                    $('#answer-result-alert-container' + answerId)
                        .addResultAlert(
                            false,
                            status ? status : STRINGS.error_alert,
                            error ? error : xhr.statusText
                        );
                }
            });
        } else {
            $('#answer-edit-' + answerId + ', #answer-delete-' + answerId + ', #answer-text-' + answerId).show();
            $('#answer-apply-' + answerId + ', #answer-cancel-' + answerId + ', #answer-text-edit-container-' + answerId).hide();
        }
        isValidInput = true;
    }
});

$('.answer-text-edit').focusout(function () {
    var answerId = extractAnswerId(this);
    if (isBlank(this.val())) {
        isValidInput = false;
        $('#answer-result-alert-container-' + answerId)
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.answer_wrong_input_message
            );
    } else {
        $('#answer-result-alert-container-' + answerId).html('');
        isValidInput = true;
    }
});

$('#new-answer-form').on('submit', function (e) {
    e.preventDefault();
});

jQuery.fn.addResultAlert = function (isSuccess, resultTitle, resultMessage) {
    var alertHTML = '<div id="question-result-alert" class="alert alert-' +
        (isSuccess ? 'success ' : 'danger ') + 'alert-dismissable fade in">\
    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>\
    <strong>' + resultTitle + '</strong> ' + resultMessage + '\
</div>';
    this.html(alertHTML);
    if (isSuccess) {
        $('#question-result-alert').delay(1000).fadeOut(3000);
    }
};

jQuery.fn.hide = function () {
    this.addClass('hidden');
};

jQuery.fn.show = function () {
    this.removeClass('hidden');
};

jQuery.fn.collapseTextNewLinesAndTrim = function () {
    this.text(this.text().trim().replace(/(\n){2,}/gm, '\n\n'))
};

function extractAnswerId(element) {
    return element.id.replace(/^\D+/g, '');
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}
