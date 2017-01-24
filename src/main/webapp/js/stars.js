var isAjaxRequestSent = false;

$(document)
    .on('mouseenter', '.cfi.cfi--star.stars__out.can-vote', function () {
        $(this).siblings().each(function () {
            $(this).find('.stars__in').width("0%");
        });
        $(this).find('.stars__in').width("100%");
    })
    .on('mouseleave', '.cfi.cfi--star.stars__out.can-vote', function () {
        $(this).siblings().each(function () {
            var initVal = $(this).attr('data-init-value');
            $(this).find('.stars__in').width(initVal + "%");
        });
        $(this).find('.stars__in').width($(this).attr('data-init-value') + "%");
    })
    .on('click', '.cfi.cfi--star.stars__out.can-vote', function () {
        if (!isAjaxRequestSent) {
            isAjaxRequestSent = true;
            var thisHandler = this;
            var value = $(this).attr('data-index');
            var answerId = extractAnswerId($(this).closest('.answer'));
            var element = $(this).closest('.stars').find('.cfi.status');
            $.ajax({
                url: 'controller?cmd=async_mark_add',
                type: 'POST',
                dataType: 'text json',
                data: 'answer_id=' + answerId + '&value=' + value,
                success: function (response) {
                    if (response.resultStatus == 'ok') {
                        var starsDiv = $(thisHandler).closest('.stars');
                        var markCount = parseInt(starsDiv.attr('data-mark-count'));
                        var currentMark = parseFloat(starsDiv.find('.cfi.value').text());
                        var newValue = markCount * currentMark;
                        if (response.markDelta == 6) {
                            markCount += 1;
                            newValue += parseInt(value);
                        } else {
                            newValue -= response.markDelta
                        }
                        newValue /= markCount;
                        starsDiv.find('.cfi.value').text(newValue.toFixed(1));
                        $(thisHandler).prevAll().each(function () {
                            $(this).attr('data-init-value', '100');
                        });
                        $(thisHandler).nextAll().each(function () {
                            $(this).attr('data-init-value', '0');
                        });
                        $(thisHandler).attr('data-init-value', '100');
                        $(thisHandler).siblings().each(function () {
                            var initVal = $(this).attr('data-init-value');
                            $(this).find('.stars__in').width(initVal + "%");
                        });
                        $(thisHandler).find('.stars__in').width($(this).attr('data-init-value') + "%");
                    }
                    showAddMarkResult(element, response.resultStatus);
                    isAjaxRequestSent = false;
                },
                error: function () {
                    showAddMarkResult(element, 'error');
                    isAjaxRequestSent = false;
                }
            });
        }
    });

function showAddMarkResult(statusElement, result) {
    statusElement.removeClass('cfi-status-success cfi-status-failed ' +
        'glyphicon-ok-circle glyphicon-ban-circle glyphicon-remove-circle');
    switch (result) {
        case 'ok':
            statusElement
                .fadeIn()
                .addClass('cfi-status-spark')
                .delay(300)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-success glyphicon-ok-circle')
                .delay(1000)
                .fadeOut(2000);
            break;
        case 'denied':
            statusElement
                .fadeIn()
                .addClass('cfi-status-spark')
                .delay(300)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-failed glyphicon-ban-circle')
                .delay(1000)
                .fadeOut(2000);
            break;
        case 'error':
            statusElement
                .fadeIn()
                .addClass('cfi-status-spark')
                .delay(300)
                .removeClass('cfi-status-spark')
                .addClass('cfi-status-failed glyphicon-remove-circle')
                .delay(1000)
                .fadeOut(2000);
            break;
    }
}