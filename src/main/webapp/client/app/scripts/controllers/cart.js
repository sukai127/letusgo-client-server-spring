'use strict';

angular.module('letusgo')
    .controller('CartCtrl', function ($scope,CartService) {

        CartService.get(function(data){

          $scope.cart = data;
          $scope.isCartEmpty = $scope.cart.cartItems.length === 0;
          $scope.totalMoney = CartService.getTotalMoney($scope.cart.cartItems);

        });

        $scope.$emit('highLightActive','cart');

        $scope.getSubtotal = function(cartitem){
            return CartService.getSubtotal(cartitem);
        };

        $scope.deleteItem = function(index){
            CartService.delete($scope.cart.cartItems[index]);
            $scope.cart.cartItems.splice(index,1);
        };

        $scope.updateItem  = function(index){

          if($scope.cart.cartItems[index].count <= 0){
            $scope.deleteItem(index);
          }else{
            CartService.update($scope.cart.cartItems[index]);
          }
        };

        $scope.$watch('cart',function(){

          if($scope.cart){
            $scope.totalMoney = CartService.getTotalMoney($scope.cart.cartItems);
            $scope.$emit('updateCount',$scope.cart);
          }

        },true);
    });


