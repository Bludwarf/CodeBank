'use strict';

var app = angular.module('app', ['ngRoute', 'ui.bootstrap']);
app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/', {
      templateUrl: '/templates/template.html',
      controller: 'Ctrl'
    }).otherwise({
      redirectTo: '/',
      caseInsensitiveMatch: true
    })
  }]);

app.controller('Ctrl', ['$scope', '$rootScope', 'Service', function($scope, $rootScope, Service) {
  $scope.formData = {};
  $scope.todos = [];

  Service.getTodos().then(function(response) {
    $scope.todos = response;
  });

  $scope.addTodo = function() {
    Service.addTodo($scope.formData).then(function(response) {
      $scope.todos.push($scope.formData)
      $scope.formData = {};
    });
  }

  $scope.removeTodo = function(todo) {
    Service.removeTodo(todo).then(function(response) {
      $scope.todos.splice($scope.todos.indexOf(todo), 1)
    });
  }
}]);