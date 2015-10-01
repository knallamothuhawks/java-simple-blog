
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
		templateUrl: "views/inputContents.html",
		controller: "inputContentsCtrl"
	})
	.when("/write", {
		templateUrl: "views/writeBoard.html",
		controller: "writeBoardCtrl"
	})
});

app.controller("userListBoardCtrl", ["$scope", "$http", "uiGridConstants", function($scope, $http, uiGridConstants) {
	var myData = [
	              {id: "1", name : "김대용", password : "1234", createdDate : "2015-09-20", role : "ADMIN"}
    ];

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
			data : myData
	}
	
	console.log($scope.gridOptions)

}])

app.controller("boardListBoardCtrl", ["$scope", "$http", "uiGridConstants", function($scope, $http, uiGridConstants) {
	var myData = [
	              {id: "1", title : "게시물 1", contents : "contents", createdDate : "2015-09-20", lastModifiedDate : "2015-09-20", member : "김대용"}
    ];

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
			data : myData
	}

}]);

app.controller("inputContentsCtrl", ["$scope", "$http", function($scope, $http){
	var date = new Date();
	var yearsList = [];
	var monthList = [];
	var dayList = [];
	
	for ( var i = 1950; i < date.getFullYear() + 1; i++) {
		yearsList.push(i);
	}
	
	for ( var i = 1; i < 13; i++) {
		monthList.push(i);
	}
	
	for ( var i = 1; i < 32; i++) {
		dayList.push(i);
	}
	
	$scope.yearsList = yearsList;
	$scope.monthList = monthList;
	$scope.dayList = dayList;
	
	$scope.userSave = function(user) {
		var reqPromise = $http({
			mehtod : "POST",
			params : user,
			url : "/userSave",
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
			mehtod : "POST",
			params : board,
			url : "/boardSave",
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
