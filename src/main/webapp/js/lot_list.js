$(document).ready(function () {
    $('#list').click(function (event) {
        event.preventDefault();
        $('#products').find('.item').addClass('list-group-item');
    }).click();
    $('#grid').click(function (event) {
        event.preventDefault();
        $('#products').find('.item')
            .removeClass('list-group-item')
            .addClass('grid-group-item');
    });
});