(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('ReviewDialogController', ReviewDialogController);

    ReviewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Review', 'CommentTarget'];

    function ReviewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Review, CommentTarget) {
        var vm = this;

        vm.review = entity;
        vm.clear = clear;
        vm.save = save;
        vm.feedbacks = CommentTarget.query({filter: 'review-is-null'});
        $q.all([vm.review.$promise, vm.feedbacks.$promise]).then(function() {
            if (!vm.review.feedback || !vm.review.feedback.id) {
                return $q.reject();
            }
            return CommentTarget.get({id : vm.review.feedback.id}).$promise;
        }).then(function(feedback) {
            vm.feedbacks.push(feedback);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.review.id !== null) {
                Review.update(vm.review, onSaveSuccess, onSaveError);
            } else {
                Review.save(vm.review, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('commentEngineApp:reviewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
