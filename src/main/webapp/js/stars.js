$(document)
    .on('mouseenter', '.cfi.cfi--star.stars__out.can-vote', function () {
        $(this).find('.stars__in').width('100%');
        $(this).prevAll().each(function () {
            $(this).find('.stars__in').width("100%");
        });
        $(this).nextAll().each(function () {
            $(this).find('.stars__in').width("0%");
        });
    })
    .on('mouseleave', '.cfi.cfi--star.stars__out.can-vote', function () {
        $(this).siblings().each(function () {
            var initVal = $(this).attr('data-init-value');
            $(this).find('.stars__in').width(initVal + "%");
        });
        $(this).find('.stars__in').width($(this).attr('data-init-value') + "%");
    })
    .on('click', '.cfi.cfi--star.stars__out.can-vote', function () {
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
                    $(thisHandler).attr('data-init-value', '100');
                    $(thisHandler).prevAll().each(function () {
                        $(thisHandler).attr('data-init-value', '100');
                    });
                    $(thisHandler).nextAll().each(function () {
                        $(thisHandler).attr('data-init-value', '0');
                    });
                }

                showAddMarkResult(element, response.resultStatus);

            },
            error: function () {
                showAddMarkResult(element, 'error');
            }
        });
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