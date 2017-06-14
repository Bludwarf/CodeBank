/// <reference path="jquery-1.4.1-vsdoc.js" />
$(document).ready(function () {
    $("select:[data-autopostback=true]").change(function () {
        $(this).closest("form").submit();
    });
});