angular.module('BookApp.module.mainController', []).controller('MainController', 
		function ($scope, router, portletConfig) {
			$scope.authenticatedUser = portletConfig.authenticatedUser;
});

/*angular.module('BookApp',
		[ 'BookApp.services', 'BookApp.controllers', 'ngRoute' ])
		.config([ '$routeProvider', function($routeProvider) {
			alert(urlContext)
			$routeProvider.when("/books", {
				templateUrl : urlContext + "/book/list.jsp",
				controller : "booksController"
			}).when("/book/:bookId", {
				templateUrl :  urlContext + "/book/details.jsp",
				controller : "booksController"
			}).otherwise({	
				redirectTo : '/books'
			});
	} ]);*/