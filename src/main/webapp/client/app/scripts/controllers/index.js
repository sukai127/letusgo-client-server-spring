'use strict';

angular
    .module('letusgo')
    .controller('IndexCtrl',function($scope,CartService){
        CartService.get(function(data){
          $scope.cart = data;
        });

        $scope.$on('addCount',function(){
            var count = $scope.cart.count || 0;
            $scope.cart.count = count + 1;
        });

        $scope.indexActive = true;
        $scope.listActive = false;
        $scope.cartActive = false;
        $scope.categoryActive = false;
        $scope.productActive = false;

        $scope.highLight = function(highLightItem){
            var allItems = ['indexActive','listActive','cartActive','categoryActive','productActive'];
            _.forEach(allItems,function(item){
               if(highLightItem === item){
                    $scope[item] = true;
               } else{
                    $scope[item] = false;
               }
            });
        };

        $scope.$on('highLightActive',function(event,active){
          $scope.highLight(active + 'Active');
        });

        $scope.$on('updateCount',function(event,cart){
            cart.count = CartService.getTotalCount(cart.cartItems);
            $scope.cart.count = cart.count;
        });

        $scope.$on('clear',function(){
          $scope.cart = {cartItems: [],count:0};
        });
    });
