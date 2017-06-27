(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentTargetDetailController', CommentTargetDetailController);

    CommentTargetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommentTarget', 'Comment'];

    function CommentTargetDetailController($scope, $rootScope, $stateParams, previousState, entity, CommentTarget, Comment) {
        var vm = this;

        vm.commentTarget = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('commentEngineApp:commentTargetUpdate', function(event, result) {
            vm.commentTarget = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
