(function() {
    'use strict';
    angular
        .module('commentEngineApp')
        .factory('CommentTarget', CommentTarget);

    CommentTarget.$inject = ['$resource'];

    function CommentTarget ($resource) {
        var resourceUrl =  'api/comment-targets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
