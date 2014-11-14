'use strict';

angular.module('letusgo')
  .controller('MainCtrl', function ($scope) {
    $scope.$emit('highLightActive','index');
  });
