'use strict';

describe('Controller: ListCtrl', function () {

  var createController,$controller,$scope,categories,categoryService,products,$routeParams;

  beforeEach(function(){
    module('letusgo');
    inject(function ($injector) {
      $scope = $injector.get('$rootScope').$new();
      $controller = $injector.get('$controller');
      categoryService = $injector.get('CategoryService');
      $routeParams = $injector.get('$routeParams');
    });

    createController = function(){
      return $controller('CategoryManageCtrl', {
        $scope: $scope,
        CategoryService: categoryService,
        $routeParams: $routeParams
      });
    };
    categories = [
      {id : 1, name: 'grocery'},
      {id : 2, name: 'device'}
    ];
    products = [
      {name: 'Instant_noodles', unit: 'bag', categoryId: '1', price: 1},
      {name: 'apple', unit: 'kg', categoryId: '1', price: 2.5}
    ];
    spyOn($scope,'$emit');
    spyOn(categoryService,'loadAllCategories').and.callFake(function(callback){
      callback(categories);
    });
  });

  it('should init success', function () {
    $routeParams.id = 1;
    createController();
    categoryService.loadAllCategories(function(data){
      $scope.categories = data;
      expect($scope.categories.length).toBe(2);
      expect($scope.category.name).toBe('grocery');
      expect($scope.categories[1].id).toBe(2);
      expect($scope.$emit.calls.count()).toBe(1);
    });
  });

  it('should add() work', function () {
    spyOn(categoryService,'insert').and.callFake(function(name,callback){
      callback({id:1,name:name});
    });
    createController();
    $scope.add();
    expect(categoryService.insert.calls.count()).toBe(1);
  });

  it('should remove() work', function () {
    spyOn(categoryService,'delete');
    createController();
    $scope.remove(1);
    expect(categoryService.delete.calls.count()).toBe(1);
    expect($scope.categories.length).toBe(1);
  });

  it('should updateCategory() work', function () {
    var category = {id : 2, name: 'device'};
    spyOn(categoryService,'updateCategory');
    createController();
    $scope.updateCategory(category);
    expect(categoryService.updateCategory.calls.count()).toBe(1);
  });
});
