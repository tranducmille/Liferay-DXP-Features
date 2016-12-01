angular.module('BookApp.module.services', [])
  .factory('serviceFactory', function($http) {

    var serviceAPI = {};

    serviceAPI.getBooks = function() {
      return $http({
        method: 'JSONP', 
        url: '/delegate/xsfapp/books?callback=JSON_CALLBACK'
      });
    }

    serviceAPI.getBookDetails = function(id) {
      return $http({
        method: 'JSONP', 
        url: '/books/xsfapp/'+ id +'?callback=JSON_CALLBACK'
      });
    }
    return serviceAPI;
});