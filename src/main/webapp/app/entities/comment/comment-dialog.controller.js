(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentDialogController', CommentDialogController);

    CommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Comment', 'CommentTarget'];

    function CommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Comment, CommentTarget) {
        var vm = this;

        vm.comment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.targets = CommentTarget.query({filter: 'comment-is-null'});
        $q.all([vm.comment.$promise, vm.targets.$promise]).then(function() {
            if (!vm.comment.target || !vm.comment.target.id) {
                return $q.reject();
            }
            return CommentTarget.get({id : vm.comment.target.id}).$promise;
        }).then(function(target) {
            vm.targets.push(target);
        });
        vm.commenttargets = CommentTarget.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comment.id !== null) {
                Comment.update(vm.comment, onSaveSuccess, onSaveError);
            } else {
                Comment.save(vm.comment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('commentEngineApp:commentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.submittedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
