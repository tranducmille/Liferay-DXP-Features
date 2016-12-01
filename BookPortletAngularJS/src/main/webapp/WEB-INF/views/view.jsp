<%@ include file="init.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Book Listing</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular-route.min.js"></script>
</head>
<body>
 	<div id="BookPortletAngularJS" ng-controller="MainController">
        <router-view>Loading...</router-view>
    </div>
	<script type="application/javascript">
	(function() {
        if (typeof(AUI) !== 'undefined') {
            /* We are within Liferay */
            AUI().ready(function() {
                startAngular();
            });
        } else {
            document.addEventListener("DOMContentLoaded", function(event) {
                startAngular();
            });
        }
        function startAngular() {
        	var portletConfig = {
                    ajaxUrl: "${ajaxURL}",
                    isStandalone: "${standalone}",
                    authenticatedUser: "${authenticatedUser}",
                    portletId: "BookPortletAngularJS",
                    portletAppContextPath: "${portletAppContextPath}"
                };
       		 var app = angular.module('BookApp.module');
       	     app.constant('portletConfig', portletConfig);
       	     var appRootElem = document.getElementById(portletConfig.portletId);
       	     angular.bootstrap(appRootElem, ['BookApp.module']);	
        }
        angular.module('BookApp.module', [
      	   'BookApp.module.mainController',
      	   'BookApp.module.bookController',
      	   'BookApp.module.routerView',
      	   'BookApp.module.router',
      	   'BookApp.module.services',
      	   'ngRoute'])
      	   .config(function(routerProvider, portletConfig) {
      		   routerProvider.config([
      			   { name: "bookListing", url: portletConfig.portletAppContextPath + "book/list.html" }
      		   ]);
      		});
    })();
    </script>
</body>
</html>