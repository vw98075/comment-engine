(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'CommentTarget'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, CommentTarget) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('commentEngineApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
