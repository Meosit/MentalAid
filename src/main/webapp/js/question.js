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

$('#question-delete-btn').on('click', function (e) {
    e.preventDefault();
    bootbox.confirm({
        message: STRINGS.question_delete_warning_message,
        title: STRINGS.question_delete_warning_title,
        buttons: {
            confirm: {
                label: STRINGS.question_delete_button_confirm,
                className: 'btn-danger'
            },
            cancel: {
                label: STRINGS.question_delete_button_cancel,
                className: 'btn-default'
            }
        },
        className: 'v-center',
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: "controller?async_question_delete",
                    type: 'POST',
                    dataType: 'text json',
                    data: 'question_id=' + $('#question-id').val(),
                    success: function (response) {
                        if (response.isResultSuccess) {
                            window.location.replace(decodeURI(response.redirectUrl));
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
                })
            }
        }
    });
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


$('.answer-delete-btn').on('click', function (e) {
    e.preventDefault();
    var answerId = extractAnswerId(this);
    bootbox.confirm({
        message: STRINGS.answer_delete_warning_message,
        title: STRINGS.answer_delete_warning_title,
        buttons: {
            confirm: {
                label: STRINGS.answer_delete_button_confirm,
                className: 'btn-danger'
            },
            cancel: {
                label: STRINGS.answer_delete_button_cancel,
                className: 'btn-default'
            }
        },
        className: 'v-center',
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: "controller?async_question_delete",
                    type: 'POST',
                    dataType: 'text json',
                    data: 'answer_id=' + answerId,
                    success: function (response) {
                        if (response.isResultSuccess) {
                            var answerCount = $('#answer-count');
                            answerCount.text(answerCount.text() - 1);
                            $('#answer-div-' + answerId).remove();
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
                })
            }
        }
    });
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

$('#new-answer-text').focusout(function () {
    if (isBlank(this.val())) {
        isValidInput = false;
        $('#new-answer-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.answer_wrong_input_message
            );
    } else {
        $('#new-answer-alert-container').html('');
        isValidInput = true;
    }
});


$('#new-answer-form').on('submit', function (e) {
    e.preventDefault();
    if (isValidInput) {
        $.ajax({
            url: 'controller?cmd=async_answer_add',
            type: 'POST',
            dataType: 'text json',
            data: $('#new-answer-form').serialize(),
            success: function (response) {
                if (response.isResultSuccess) {
                    $('#new-answer-create-date').text(response.createdAt);
                    $('#new-answer-username-link').text(response.creatorUsername);

                    $('#answer-text-dummy')
                        .text(response.answerText)
                        .attr('id', 'answer-text-' + response.answerId);
                    $('#answer-edit-date-container-dummy').attr('id', 'answer-edit-date-container-' + response.answerId);
                    $('#answer-edit-date-dummy')
                        .text(response.createdAt)
                        .attr('id', 'answer-edit-date-' + response.answerId);
                    $('#answer-id-dummy').val(response.answerId).attr('id', 'answer-id-' + response.answerId);
                    $('#answer-text-edit-container-dummy').attr('id', 'answer-text-edit-container-' + response.answerId);
                    $('#answer-text-edit-form-dummy').attr('id', 'answer-text-edit-form-' + response.answerId);
                    $('#answer-text-edit-dummy').attr('id', 'answer-text-edit-' + response.answerId);
                    $('#answer-result-alert-container-dummy').attr('id', 'answer-result-alert-container-' + response.answerId);
                    $('#answer-apply-dummy').attr('id', 'answer-apply-' + response.answerId);
                    $('#answer-edit-dummy').attr('id', 'answer-edit-' + response.answerId);
                    $('#answer-cancel-dummy').attr('id', 'answer-cancel-' + response.answerId);
                    $('#answer-delete-dummy').attr('id', 'answer-delete-' + response.answerId);
                    $('#answer-div-dummy').show().attr('id', 'answer-div-' + response.answerId);
                } else {
                    $('#new-answer-alert-container')
                        .addResultAlert(
                            response.isResultSuccess,
                            response.errorTitle,
                            response.errorMessage
                        );
                }
            },
            error: function (xhr, status, error) {
                $('#new-answer-alert-container')
                    .addResultAlert(
                        false,
                        status ? status : STRINGS.error_alert,
                        error ? error : xhr.statusText
                    );
            }
        });
        isValidInput = true;
    }
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
