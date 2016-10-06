define(function (require) {

    var rest = require('rest');
    var mime = require('rest/interceptor/mime');
    var entity = require('rest/interceptor/entity');
    var render = require('./render');

    var endpointUrl, name, client;

    endpointUrl = 'http://localhost:8080/thing/57f40b5b7943ab2eb4ae2d76';
    name = document.location.search.slice(1);

    client = rest
        .chain(mime, { mime: 'application/json' })
        .chain(entity);

    client({ path: endpointUrl, params: { name:  name } })
        .then(render);

});
