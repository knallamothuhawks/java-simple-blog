
app.config(function($routeProvider) {
	$routeProvider
	.when("/list", {
		templateUrl: "views/postList.html",
		controller: "postListController"
	}).when("/save", {
		templateUrl: "views/postSave.html",
		controller: "postSaveController"
	}).otherwise("/list");
});