'use strict';

describe('Controller: ListCtrl', function () {

  var createController,$controller,productService,$scope,products,categoryService,categories,$routeParams;

  beforeEach(function(){
    module('letusgo');
    inject(function ($injector) {
      $scope = $injector.get('$rootScope').$new();
      $controller = $injector.get('$controller');
      $routeParams = $injector.get('$routeParams');
      categoryService = $injector.get('CategoryService');
      productService = $injector.get('ProductService');
    });

    createController = function(){
      return $controller('ProductManageCtrl', {
        $scope: $scope,
        CategoryService: categoryService,
        ProductService: productService,
        $routeParams: $routeParams
      });
    };
     products = [
        {id:1, name : 'Instant_noodles', unit : 'bag', category : '1', price : 1},
        {id:2, name : 'apple', unit : 'kg', category : '1', price : 2.5}
      ];
    spyOn($scope,'$emit');

    spyOn(productService,'loadAllProducts').and.callFake(function(pageNow,callback){
      callback(products);
    });

    categories = [
      {id : 1, name: 'grocery'},
      {id : 2, name: 'device'}
    ];

    spyOn(categoryService,'loadAllCategories').and.callFake(function(callback){
      callback(categories);
    });
  });

  it('should init success', function () {

    spyOn(categoryService,'getCategoryById').and.callFake(function(id,callback){
      callback({id:2,name:'grocery'});
    });

    $routeParams.name = 'test';
    createController();

    productService.loadAllProducts(null,function(data){
      $scope.products = data;
      expect($scope.products.length).toBe(2);
      expect($scope.product).toBe(undefined);
      expect($scope.products[1].name).toBe('apple');
      expect($scope.$emit.calls.count()).toBe(1);
    });
  });

  it('should init success when product is not empty', function () {

    spyOn(categoryService,'getCategoryById').and.callFake(function(id,callback){
      callback({id:2,name:'grocery'});
    });

    $routeParams.name = 'apple';
    createController();

    productService.loadAllProducts(null,function(data){
      $scope.products = data;
      expect($scope.products.length).toBe(2);
      expect($scope.products[1].name).toBe('apple');
      expect($scope.$emit.calls.count()).toBe(1);
      expect(categoryService.getCategoryById).toHaveBeenCalled();
    });
  });

  it('should initCategories success', function () {

    createController();

    categoryService.loadAllCategories(function(data){
      $scope.categories = data;
      expect($scope.categories.length).toBe(2);
    });
  });


  it('should remove() work', function () {

    spyOn(productService,'delete');

    createController();
    $scope.remove(1);

    expect($scope.products.length).toBe(1);
    expect(productService.delete.calls.count()).toBe(1);
  });

  it('should add() work when product equal {}', function () {

    var product = {name : 'Instant', unit : 'bag', category : '1', price : 1};

    spyOn(productService,'insert').and.callFake(function(item,callback){
      callback(product);
    });

    createController();
    $scope.product = product;
    $scope.add();

    expect($scope.products.length).toBe(3);
  });

  it('should updateProduct() work', function () {

    var product = {id:1,name : 'Instant', unit : 'bag', category : '1', price : 1};
    spyOn(productService,'updateProduct');

    createController();
    $scope.product = product;
    $scope.updateProduct();

    expect($scope.products[0].name).toBe('Instant');
    expect(productService.updateProduct.calls.count()).toBe(1);
  });

});
