var curl;
(function () {

    curl.config({
        main: 'thingResource',
        packages: {
            // Your application's packages
            thingResource: { location: 'thingResource' },
            // Third-party packages
            curl: { location: 'lib/curl/src/curl' },
            rest: { location: 'lib/rest', main: 'rest' },
            when: { location: 'lib/when', main: 'when' },
            jquery: { location: 'lib/jquery', main: 'jquery' },
            datatables: { location: 'lib/datatables', main: 'datatables' }
        }
    });

}());
