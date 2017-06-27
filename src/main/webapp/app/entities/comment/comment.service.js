(function() {
    'use strict';
    angular
        .module('commentEngineApp')
        .factory('Comment', Comment);

    Comment.$inject = ['$resource', 'DateUtils'];

    function Comment ($resource, DateUtils) {
        var resourceUrl =  'api/comments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.submittedDate = DateUtils.convertDateTimeFromServer(data.submittedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
