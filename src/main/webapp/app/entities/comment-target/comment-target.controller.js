(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .controller('CommentTargetController', CommentTargetController);

    CommentTargetController.$inject = ['CommentTarget'];

    function CommentTargetController(CommentTarget) {

        var vm = this;

        vm.commentTargets = [];

        loadAll();

        function loadAll() {
            CommentTarget.query(function(result) {
                vm.commentTargets = result;
                vm.searchQuery = null;
            });
        }
    }
})();
