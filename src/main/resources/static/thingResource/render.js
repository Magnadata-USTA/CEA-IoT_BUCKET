define(function (require) {

    var ready = require('curl/domReady');

    return render;

    function render (entity) {
        ready(function () {
            var idElement, nameElement;

            idElement = document.querySelector('[data-name="id"]');
            nameElement = document.querySelector('[data-name="content"]');

            idElement.textContent += entity.id;
            nameElement.textContent += entity.deviceId;

        });



    }

});
