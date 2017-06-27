(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentTargetDialogController', CommentTargetDialogController);

    CommentTargetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CommentTarget', 'Comment'];

    function CommentTargetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CommentTarget, Comment) {
        var vm = this;

        vm.commentTarget = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comments = Comment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commentTarget.id !== null) {
                CommentTarget.update(vm.commentTarget, onSaveSuccess, onSaveError);
            } else {
                CommentTarget.save(vm.commentTarget, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('commentEngineApp:commentTargetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
