var isValidInput = true;
const MAX_QUESTION_TITLE_LENGTH = 200;
const MAX_QUESTION_DESCRIPTION_LENGTH = 2000;
const MAX_ANSWER_TEXT_LENGTH = 2000;

$('#question-edit-btn').on('click', function (e) {
    e.preventDefault();
    $('#question-title-edit').val($('#question-title').text());
    $('#question-description-edit').val($('#question-description').text());
    $('#question-edit-btn, #question-delete-btn, #question-div').hideElem();
    $('#question-apply-btn, #question-cancel-btn, #question-edit-div').showElem();
});

$('#question-cancel-btn').on('click', function (e) {
    e.preventDefault();
    $('#question-edit-btn, #question-delete-btn, #question-div').showElem();
    $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hideElem();
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
                        $('#question-edit-date-container').showElem();
                        $('#question-edit-date').text(response.modifiedAt);
                        $('#question-result-alert-container')
                            .addResultAlert(
                                response.isResultSuccess,
                                STRINGS.success_alert,
                                STRINGS.question_edit_success_message
                            );
                        $('#question-edit-btn, #question-delete-btn, #question-div').showElem();
                        $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hideElem();
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
            $('#question-edit-btn, #question-delete-btn, #question-div').showElem();
            $('#question-apply-btn, #question-cancel-btn, #question-edit-div').hideElem();
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
                    url: "controller?cmd=async_question_delete",
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

$('#question-description-edit').focusout(function () {
    if (isBlank($(this).val())) {
        isValidInput = false;
        $('#question-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.question_description_empty
            );
    } else if ($(this).val().length > MAX_QUESTION_DESCRIPTION_LENGTH) {
        isValidInput = false;
        $('#question-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.question_description_too_long
            );
    } else {
        $('#question-result-alert-container').html('');
        isValidInput = true;
    }
});

$('#question-title-edit').focusout(function () {
    if (isBlank($(this).val())) {
        isValidInput = false;
        $('#question-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.question_title_empty
            );
    } else if ($(this).val().length > MAX_QUESTION_TITLE_LENGTH) {
        isValidInput = false;
        $('#question-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.question_title_too_long
            );
    } else {
        $('#question-result-alert-container').html('');
        isValidInput = true;
    }
});

$(document).on('click', '.answer-edit-btn', function (e) {
    e.preventDefault();
    var answerRoot = $(this).closest('.answer');
    answerRoot.find('.answer-text-input').val(answerRoot.find('.answer-text').text());
    switchAnswerMode(answerRoot, true);
});

$(document).on('click', '.answer-cancel-btn', function (e) {
    e.preventDefault();
    var answerRoot = $(this).closest('.answer');
    switchAnswerMode(answerRoot, false);
});


$(document).on('click', '.answer-apply-btn', function (e) {
    e.preventDefault();
    var answerRoot = $(this).closest('.answer');
    if (isValidInput) {
        if (answerRoot.find('.answer-text').text() !== answerRoot.find('.answer-text-input').val()) {
            $.ajax({
                url: 'controller?cmd=async_answer_edit',
                type: 'POST',
                dataType: 'text json',
                data: answerRoot.find('.answer-edit-form').serialize(),
                success: function (response) {
                    if (response.isResultSuccess) {
                        answerRoot.find('.answer-text')
                            .text(answerRoot.find('.answer-text-input').val())
                            .collapseTextNewLinesAndTrim();
                        answerRoot.find('.answer-modified-date-container').showElem();
                        answerRoot.find('.answer-modified-date').text(response.modifiedAt);
                        answerRoot.find('.answer-result-alert-container')
                            .addResultAlert(
                                response.isResultSuccess,
                                STRINGS.success_alert,
                                STRINGS.answer_edit_success_message
                            );
                        switchAnswerMode(answerRoot, false);
                    } else {
                        answerRoot.find('.answer-result-alert-container')
                            .addResultAlert(
                                response.isResultSuccess,
                                response.errorTitle,
                                response.errorMessage
                            );
                    }
                },
                error: function (xhr, status, error) {
                    answerRoot.find('.answer-result-alert-container')
                        .addResultAlert(
                            false,
                            status ? status : STRINGS.error_alert,
                            error ? error : xhr.statusText
                        );
                }
            });
        } else {
            switchAnswerMode(answerRoot, false);
        }
        isValidInput = true;
    }
});


$(document).on('click', '.answer-delete-btn', function (e) {
    e.preventDefault();
    var answerRoot = $(this).closest('.answer');
    var answerId = extractAnswerId(answerRoot);
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
                    url: "controller?cmd=async_answer_delete",
                    type: 'POST',
                    dataType: 'text json',
                    data: 'answer_id=' + answerId,
                    success: function (response) {
                        if (response.isResultSuccess) {
                            updateAnswerCountLabel(-1);
                            answerRoot.remove();
                            switchNewAnswerMode('can-add')
                        } else {
                            answerRoot.find('.answer-result-alert-container')
                                .addResultAlert(
                                    response.isResultSuccess,
                                    response.errorTitle,
                                    response.errorMessage
                                );
                        }
                    },
                    error: function (xhr, status, error) {
                        answerRoot.find('.answer-result-alert-container')
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

$(document).on('focusout', '.answer-text-input', function () {
    var answerRoot = $(this).closest('.answer');
    if (isBlank($(this).val())) {
        isValidInput = false;
        answerRoot.find('.answer-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.answer_text_empty
            );
    } else if ($(this).val().length > MAX_ANSWER_TEXT_LENGTH) {
        isValidInput = false;
        answerRoot.find('.answer-result-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.answer_text_too_long
            );
    } else {
        answerRoot.find('.answer-result-alert-container').html('');
        isValidInput = true;
    }
});

$('#new-answer-text-input').focusout(function () {
    if (isBlank($(this).val())) {
        isValidInput = false;
        $('#new-answer-alert-container')
            .addResultAlert(
                false,
                STRINGS.error_alert,
                STRINGS.answer_text_empty
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
                    var newAnswerElement = $('#new-answer-template').find('.answer').clone();
                    newAnswerElement.attr('id', 'answer-' + response.answerId);
                    newAnswerElement.find('.answer-creator-username').text(response.creatorUsername);
                    newAnswerElement.find('.answer-created-date').text(response.createdAt);
                    newAnswerElement.find('.answer-modified-date').text(response.createdAt);
                    newAnswerElement.find('.answer-id-input').val(response.answerId);
                    newAnswerElement.find('.answer-text')
                        .text($('#new-answer-text-input').val())
                        .collapseTextNewLinesAndTrim();
                    newAnswerElement.appendTo('.answers');
                    switchNewAnswerMode('answer-exists');
                    updateAnswerCountLabel(1)
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

jQuery.fn.hideElem = function () {
    this.addClass('hidden');
};

jQuery.fn.showElem = function () {
    this.removeClass('hidden');
};

jQuery.fn.collapseTextNewLinesAndTrim = function () {
    this.text(this.text().trim().replace(/(\n){2,}/gm, '\n\n'))
};

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function extractAnswerId(element) {
    return element.attr('id').replace(/^\D+/g, '');
}

function switchAnswerMode(answerRoot, isEditMode) {
    if (isEditMode) {
        answerRoot.find('.answer-edit-btn').hideElem();
        answerRoot.find('.answer-delete-btn').hideElem();
        answerRoot.find('.answer-text').hideElem();
        answerRoot.find('.answer-apply-btn').showElem();
        answerRoot.find('.answer-cancel-btn').showElem();
        answerRoot.find('.answer-edit-container').showElem();
    } else {
        answerRoot.find('.answer-edit-btn').showElem();
        answerRoot.find('.answer-delete-btn').showElem();
        answerRoot.find('.answer-text').showElem();
        answerRoot.find('.answer-apply-btn').hideElem();
        answerRoot.find('.answer-cancel-btn').hideElem();
        answerRoot.find('.answer-edit-container').hideElem();
    }
}

function switchNewAnswerMode(mode) {
    var newAnswerDiv = $('#new-answer-form-div');
    var questionOwnerMessage = $('#question-owner-message');
    var answerExistsMessage = $('#answer-exists-message');
    var userBannedMessage = $('#user-banned-message');
    var guestMessage = $('#guest-message');
    switch (mode) {
        case 'can-add':
            newAnswerDiv.showElem();
            questionOwnerMessage.hideElem();
            answerExistsMessage.hideElem();
            userBannedMessage.hideElem();
            guestMessage.hideElem();
            break;
        case 'answer-exists':
            newAnswerDiv.hideElem();
            questionOwnerMessage.hideElem();
            answerExistsMessage.showElem();
            userBannedMessage.hideElem();
            guestMessage.hideElem();
            break;
        case 'question-owner':
            newAnswerDiv.hideElem();
            questionOwnerMessage.showElem();
            answerExistsMessage.hideElem();
            userBannedMessage.hideElem();
            guestMessage.hideElem();
            break;
        case 'user-banned':
            newAnswerDiv.hideElem();
            questionOwnerMessage.hideElem();
            answerExistsMessage.hideElem();
            userBannedMessage.showElem();
            guestMessage.hideElem();
            break;
        case 'quest':
            newAnswerDiv.hideElem();
            questionOwnerMessage.hideElem();
            answerExistsMessage.hideElem();
            userBannedMessage.hideElem();
            guestMessage.showElem();
            break;
    }
}

function updateAnswerCountLabel(delta) {
    var answerCount = $('#answer-count-label');
    var count = parseInt(answerCount.text().replace(/^\D+/g, '')) + delta;
    if (count == 1) {
        answerCount.text(count + " " + STRINGS.answer_count_single);
    } else if (count % 10 == 1) {
        if (LOCALE === 'ru') {
            answerCount.text(count + " " + STRINGS.answer_count_single);
        } else {
            answerCount.text(count + " " + STRINGS.answer_count_multiple);
        }
    } else if (count < 5 && count != 0) {
        answerCount.text(count + " " + STRINGS.answer_count_alter);
    } else {
        answerCount.text(count + " " + STRINGS.answer_count_multiple);
    }

}
if ($('meta[name=authorized]').attr('content') == 'true') {
    $(document)
        .on('mouseenter', '.cfi.cfi--star.stars__out', function () {
            $(this).find('.stars__in').width('100%');
            $(this).prevAll().each(function () {
                $(this).find('.stars__in').width("100%");
            });
            $(this).nextAll().each(function () {
                $(this).find('.stars__in').width("0%");
            });
        })
        .on('mouseleave', '.cfi.cfi--star.stars__out', function () {
            $(this).siblings().each(function () {
                var initVal = $(this).attr('data-init-value');
                $(this).find('.stars__in').width(initVal + "%");
            });
            $(this).find('.stars__in').width($(this).attr('data-init-value') + "%");
        })
        .on('click', '.cfi.cfi--star.stars__out', function () {
            $(this).attr('data-init-value', '100');
            $(this).prevAll().each(function () {
                $(this).attr('data-init-value', '100');
            });
            $(this).nextAll().each(function () {
                $(this).attr('data-init-value', '0');
            });
            var value = $(this).attr('data-index');
            var answerId = extractAnswerId($(this).closest('.answer'));
            var element = $(this).closest('.stars').find('.cfi.status');
            $.ajax({
                url: 'controller?cmd=async_mark_add',
                type: 'POST',
                dataType: 'text json',
                data: 'answer_id=' + answerId + '&value=' + value,
                success: function (response) {
                    showAddMarkResult(element, response.resultStatus);
                },
                error: function () {
                    showAddMarkResult(element, 'error');
                }
            });
        });
}

function showAddMarkResult(statusElement, result) {
    statusElement.removeClass('cfi-status-success cfi-status-failed ' +
        'glyphicon-ok-circle glyphicon-ban-circle glyphicon-remove-circle');
    switch (result) {
        case 'ok':
            statusElement
                .addClass('cfi-status-spark')
                .delay(100)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-success glyphicon-ok-circle');
            break;
        case 'denied':
            statusElement
                .addClass('cfi-status-spark')
                .delay(100)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-failed glyphicon-ban-circle');
            break;
        case 'error':
            statusElement
                .addClass('cfi-status-spark')
                .delay(100)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-failed glyphicon-remove-circle');
            break;
    }
}