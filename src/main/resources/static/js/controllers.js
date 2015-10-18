app.controller("postListController", [
		"$scope",
		"$http",
		function($scope, $http) {
			angular.element(
					document.getElementsByClassName('blog-nav-item active'))
					.removeClass("active");
			angular.element(document.getElementById('navHome')).addClass(
					"active");

			$http.get("/posts").success(function(result) {
				$scope.posts = result.content;
			});
		} ]);

app.controller('postSaveController', [
		'$scope',
		'$http',
		function($scope, $http) {
			angular.element(
					document.getElementsByClassName('blog-nav-item active'))
					.removeClass("active");
			angular.element(document.getElementById('navPostSave')).addClass(
					"active");

			$scope.post = {};

			$scope.writePost = function(post) {
				post.memberId = 2;
				$http.post('/post', post).success(function(result) {
				});
			};

		} ]);
