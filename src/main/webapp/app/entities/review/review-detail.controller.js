(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('ReviewDetailController', ReviewDetailController);

    ReviewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Review', 'CommentTarget'];

    function ReviewDetailController($scope, $rootScope, $stateParams, previousState, entity, Review, CommentTarget) {
        var vm = this;

        vm.review = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('commentEngineApp:reviewUpdate', function(event, result) {
            vm.review = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
