(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Product', 'CommentTarget'];

    function ProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Product, CommentTarget) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.save = save;
        vm.questiontargets = CommentTarget.query({filter: 'product-is-null'});
        $q.all([vm.product.$promise, vm.questiontargets.$promise]).then(function() {
            if (!vm.product.questionTarget || !vm.product.questionTarget.id) {
                return $q.reject();
            }
            return CommentTarget.get({id : vm.product.questionTarget.id}).$promise;
        }).then(function(questionTarget) {
            vm.questiontargets.push(questionTarget);
        });
        vm.reviewtargets = CommentTarget.query({filter: 'product-is-null'});
        $q.all([vm.product.$promise, vm.reviewtargets.$promise]).then(function() {
            if (!vm.product.reviewTarget || !vm.product.reviewTarget.id) {
                return $q.reject();
            }
            return CommentTarget.get({id : vm.product.reviewTarget.id}).$promise;
        }).then(function(reviewTarget) {
            vm.reviewtargets.push(reviewTarget);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('commentEngineApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
