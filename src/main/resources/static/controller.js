var app = angular.module('controller', ['ui.bootstrap']);

//an arrow function here seems to break AngularJS api,
//so we use "function" annotation
app.controller('ScoresController', function ($scope, $http) {
  const dataEndPointUrl = '/scores/';
  const headers = { 'Content-Type': 'application/json' };

  $scope.scores = [];

  $scope.sortScores = () => {
    $scope.scores.sort((s1, s2) => { return s2.score - s1.score });
  }

  $http({ method: 'GET', url: dataEndPointUrl })
    .success((data) => {
      $scope.scores = data;
    })
    .error((data) => {
      handleHttpError('Failed to get scores', data);
    });

  $scope.newScore = {};

  $scope.addNewScore = () => {
    $http({ method: 'POST', url: dataEndPointUrl, headers: headers, data: $scope.newScore })
      .success((data) => {
        $scope.scores.push(data);
        $scope.newScore = {};
      })
      .error((data) => {
        handleHttpError('Failed to add score', data);
      });
  };

  const handleHttpError = (errorMessage, errorData) => {
    if (errorData.message) {
      errorMessage += ': ' + errorData.message;
    }
    if (errorData.errors) {
      errorMessage += ' ' + errorData.errors.map(e => e.defaultMessage).join(', ');
    }

    alert(errorMessage);
  }
});