(function() {
    'use strict';

    angular
        .module('commentEngineApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comment-target', {
            parent: 'entity',
            url: '/comment-target',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommentTargets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comment-target/comment-targets.html',
                    controller: 'CommentTargetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('comment-target-detail', {
            parent: 'comment-target',
            url: '/comment-target/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommentTarget'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comment-target/comment-target-detail.html',
                    controller: 'CommentTargetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CommentTarget', function($stateParams, CommentTarget) {
                    return CommentTarget.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comment-target',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comment-target-detail.edit', {
            parent: 'comment-target-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comment-target/comment-target-dialog.html',
                    controller: 'CommentTargetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommentTarget', function(CommentTarget) {
                            return CommentTarget.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comment-target.new', {
            parent: 'comment-target',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comment-target/comment-target-dialog.html',
                    controller: 'CommentTargetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                targetEntityName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comment-target', null, { reload: 'comment-target' });
                }, function() {
                    $state.go('comment-target');
                });
            }]
        })
        .state('comment-target.edit', {
            parent: 'comment-target',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comment-target/comment-target-dialog.html',
                    controller: 'CommentTargetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommentTarget', function(CommentTarget) {
                            return CommentTarget.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comment-target', null, { reload: 'comment-target' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comment-target.delete', {
            parent: 'comment-target',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comment-target/comment-target-delete-dialog.html',
                    controller: 'CommentTargetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommentTarget', function(CommentTarget) {
                            return CommentTarget.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comment-target', null, { reload: 'comment-target' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
