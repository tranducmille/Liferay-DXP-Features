angular.module('BookApp.module.routerView', [])
    .directive('routerView', function(router) {
        return {
            restrict: 'E',
            controller: function($scope) {
                $scope.getTemplateUrl = function() {
                    return router.getTemplateUrl();
                }
            },
            template: ' <div ng-include="getTemplateUrl()"></div>'
        }
    });