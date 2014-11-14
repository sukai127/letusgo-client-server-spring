'use strict';

angular.module('letusgo')
  .controller('ListCtrl', function ($scope,CartService,ProductService,$routeParams) {

    $scope.pageNow = parseInt($routeParams.pageNow);

    ProductService.loadAllProducts($scope.pageNow,function(data){
      $scope.products = data;
    });

    CartService.get(function(data){
       $scope.cart = data;
    });

    $scope.$emit('highLightActive','list');

    $scope.addToCart = function(product){
        ProductService.addToCart(product);
        $scope.$emit('addCount');
    };

    ProductService.getPageTotal(function(data){
      $scope.pageTotal = data;
      $scope.previous = ProductService.getPrevious($scope.pageNow);
      $scope.next = ProductService.getNext($scope.pageNow,data);
    });
  });
