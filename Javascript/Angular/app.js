var app = angular.module('app', []);

var phonesDb = 'http://angular.github.io/angular-phonecat/step-5/app/phones/phones.json';

var ctrl = app.controller('ctrl', function ($scope, $http) {
  $http.get(phonesDb).success(function(data) {
    $scope.phones = data;
  });

  $scope.orderProp = 'age';
});