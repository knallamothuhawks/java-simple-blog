
//	routeConfig 로드 후  공통 컨트롤러 선언
var app = angular.module("cc_board", ["ui.grid", "ngRoute", "ui.grid.selection"]);

app.config(function($routeProvider) {
	$routeProvider
	.when("/userList", {
		templateUrl: "views/userList.html",
		controller: "userListBoardCtrl"
	})
	.when("/boardList", {
		templateUrl: "views/boardList.html",
		controller: "boardListBoardCtrl"
	})
	.when("/insert", {
		templateUrl: "views/createUser.html",
		controller: "createUserCtrl"
	})
	.when("/write", {
		templateUrl: "views/writeBoard.html",
		controller: "writeBoardCtrl"
	})
});

app.controller("userListBoardCtrl", ["$scope", "$http", "uiGridConstants", function($scope, $http, uiGridConstants) {
	$scope.gridOptions = {
			enableRowSelection: true, 
			multiSelect : false,
			enableRowHeaderSelection: false,
			columnDef : [
			     {field: "id", displayName : "ID"},
			     {field: "name", displayName : "이름"},
			     {field: "password", displayName : "비밀번호"},
			     {field: "createdDate", displayName : "생성일"},
			     {field: "role", displayName : "권한"},
			],
			onRegisterApi: function (gridApi) {
		        $scope.gridApi = gridApi;
		        gridApi.selection.on.rowSelectionChanged($scope,function(rows){
		            $scope.mySelections = gridApi.selection.getSelectedRows();
		        });
		    },
	}
	
	var reqPromise = $http({
		method : "GET",
		url : "/members",
	});
	
	reqPromise.success(function(data, status, headers, config){
		$scope.gridOptions.data = data.content;
	});
}])

app.controller("boardListBoardCtrl", ["$scope", "$http", "uiGridConstants", function($scope, $http, uiGridConstants) {
	$scope.gridOptions = {
			enableRowSelection: true, 
			multiSelect : false,
			enableRowHeaderSelection: false,
			columnDef : [
			     {field: "id", displayName : "ID"},
			     {field: "title", displayName : "제목"},
			     {field: "content", displayName : "내용"},
			     {field: "createdDate", displayName : "생성일"},
			     {field: "lastModifiedDate", displayName : "최종 수정일"},
			     {field: "member", displayName : "작성자"},
			],
			onRegisterApi: function (gridApi) {
		        $scope.gridApi = gridApi;
		        gridApi.selection.on.rowSelectionChanged($scope,function(rows){
		            $scope.mySelections = gridApi.selection.getSelectedRows();
		        });
		    },
	}
	
	var reqPromise = $http({
		method : "GET",
		url : "/posts",
	});
	
	reqPromise.success(function(data, status, headers, config){
		$scope.gridOptions.data = data.content;
	});
}]);

app.controller("createUserCtrl", ["$scope", "$http", function($scope, $http){
	$scope.userSave = function(user) {
		var reqPromise = $http({
			method : "POST",
			data : user,
			url : "/member",
		});
		
		reqPromise.success(function(data, status, headers, config){
			$scope.check = true;
		});
	}
	
	$scope.reset = function() {
		$scope.user = {};
		$scope.check = false;
	}
}])

app.controller("writeBoardCtrl", ["$scope", "$http", function($scope, $http){
	$scope.boardSave = function(board) {
		var reqPromise = $http({
			method : "POST",
			data : board,
			url : "/post",
		});
		
		reqPromise.success(function(data, status, headers, config){
			$scope.check = true;
		});
	}
	
	$scope.reset = function() {
		$scope.board = {};
		$scope.check = false;
	}
}])
