'use strict';

angular.module('letusgo')
  .controller('CategoryManageCtrl', function ($scope,CategoryService,$routeParams) {

    CategoryService.loadAllCategories(function(categories){

        $scope.categories = categories;

        $scope.category = _.find($scope.categories,function(category){
          var id = $routeParams.id || 0;
          return category.id.toString() === id.toString();
        });

      });

      $scope.add = function(){
        CategoryService.insert($scope.name,function(data){
          $scope.categories.push(data);
        });
      };

      $scope.$emit('highLightActive','category');

      $scope.remove = function(index){
        CategoryService.delete($scope.categories[index].id);
        $scope.categories.splice(index,1);
      };

      $scope.updateCategory = function(){
        CategoryService.updateCategory($scope.category);
      };
  });
