'use strict';

angular.module('letusgo')
  .controller('ResultCtrl', function ($scope,CartService) {
    CartService.get(function(data){
      $scope.cartItems = data.cartItems;
    });
    $scope.getSubtotal = function(cartitem){
        return CartService.getSubtotal(cartitem);
    };
    $scope.clearData = function() {
        CartService.remove(function(){
          $scope.$emit('clear');
        });
      $scope.$emit('clear');
    };
  });
