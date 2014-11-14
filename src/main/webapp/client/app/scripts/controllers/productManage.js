'use strict';

angular.module('letusgo')
  .controller('ProductManageCtrl', function ($scope,ProductService,CategoryService,$routeParams,$location) {

    ProductService.loadAllProducts(null,function(data){

      $scope.products = data;

      $scope.product = _.find($scope.products,function(product){

        var id = $routeParams.id || 0;
        return product.id.toString() === id.toString();
      });

    });

    CategoryService.loadAllCategories(function(data){
      $scope.categories = data;
    });

    $scope.remove = function(index){
      ProductService.delete($scope.products[index].id);
      $scope.products.splice(index,1);
    };

    $scope.updateProduct = function(){

          $scope.product.category = _.find($scope.categories,function(category){
            return category.id.toString() === $scope.product.category.id.toString();
          });

          ProductService.updateProduct($scope.product);

          $location.path("/productManage");
    };

    $scope.$emit('highLightActive','product');

    $scope.add = function(){

      ProductService.insert($scope.product,function(data){

        $scope.products.push(data);
        $scope.product = null;
      });
    };
  });
