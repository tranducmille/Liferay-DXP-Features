angular.module('BookApp.module.bookController', []).controller('BookController', 
	function($scope, serviceFactory, router) {
	    /*$scope.nameFilter = null;
	    $scope.booksList = [];
	    $scope.searchFilter = function (condition) {
	        var re = new RegExp($scope.nameFilter, 'i');
	        return !$scope.nameFilter || re.test(condition.Book.title) || re.test(condition.Book.author);
	    };
	
	    serviceFactory.getBooks().success(function (response) {
	        $scope.booksList = response.data;
	    });*/
		
		
		$scope.booksList = null;

        $scope.showList = function() {
            router.goto('bookListing');
        };		
  });
  
  