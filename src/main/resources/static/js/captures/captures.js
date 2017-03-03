/* Captures javascript*/
$(document).ready(
    function() {
        $("#combobox-solutions").change(function() {
            sendAjaxRequest();
        });
        $("#combobox-devices").change(function() {
            sendAjaxRequest();
        });
        $("#combobox-sensors").change(function() {
            sendAjaxRequest();
        });
    }
);

function sendAjaxRequest(){
    var solutionValue = $("#combobox-solutions").val();
    var deviceValue = $("#combobox-devices").val();
    var sensorValue = $("#combobox-sensors").val();
     location.href = '/sensors/captures?solutionId=' +  solutionValue + '&deviceId=' + deviceValue +
                    'sensorId=' + sensorValue;
};

