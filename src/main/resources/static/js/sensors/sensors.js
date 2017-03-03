/* Sensors javascript logic */
$(document).ready(
    function() {
        $("#combobox-solutions").change(function() {
            sendAjaxRequest();
        });

        $("#combobox-devices").change(function() {
            sendAjaxRequest();
        });

    }
);

function sendAjaxRequest() {
    var solutionValue = $("#combobox-solutions").val();
    var deviceValue = $("#combobox-devices").val();
    location.href = '/devices/sensors?solutionId=' + solutionValue + '&deviceId=' + deviceValue;
};
