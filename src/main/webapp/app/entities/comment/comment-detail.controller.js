(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentDetailController', CommentDetailController);

    CommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comment', 'CommentTarget'];

    function CommentDetailController($scope, $rootScope, $stateParams, previousState, entity, Comment, CommentTarget) {
        var vm = this;

        vm.comment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('commentEngineApp:commentUpdate', function(event, result) {
            vm.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
