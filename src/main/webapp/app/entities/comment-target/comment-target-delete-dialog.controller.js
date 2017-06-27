(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentTargetDeleteController',CommentTargetDeleteController);

    CommentTargetDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommentTarget'];

    function CommentTargetDeleteController($uibModalInstance, entity, CommentTarget) {
        var vm = this;

        vm.commentTarget = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommentTarget.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
