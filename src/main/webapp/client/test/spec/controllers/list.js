'use strict';

describe('Controller: ListCtrl', function () {

  var createController,$controller,cart,cartService,productService,$scope,products,categoryService,$routeParams;

  beforeEach(function(){
    module('letusgo');
    inject(function ($injector) {
      $scope = $injector.get('$rootScope').$new();
      $controller = $injector.get('$controller');
      cartService = $injector.get('CartService');
      categoryService = $injector.get('CategoryService');
      productService = $injector.get('ProductService');
      $routeParams = $injector.get('$routeParams');
    });

    createController = function(){
      return $controller('ListCtrl', {
        $scope: $scope,
        CartService: cartService,
        CategoryService: categoryService,
        ProductService: productService,
        $routeParams : $routeParams
      });
    };
     products = [
      {name : 'Instant_noodles', unit : 'bag', category : '1', price : 1},
      {name : 'apple', unit : 'kg', category : '1', price : 2.5}
      ];
    cart = {
      cartItems: [
        {
          product: {name : 'Instant_noodles', unit : 'bag', category : '1', price : 1},
          count : 4
        },
        {
          product: {name : 'coca_cola', unit : 'bottle', category : '1', price : 0.5},
          count : 3
        },
        {
          product: {name : 'kettle', unit : 'piece', category : '2', price : 43.5},
          count : 1
        }
      ],
      len : 8
    };

    spyOn(productService,'loadAllProducts').and.callFake(function(items,callback){
      callback(products);
    });

    spyOn(cartService,'get').and.callFake(function(callback){
      callback(cart);
    });

    spyOn($scope,'$emit');
  });

  it('should init success', function () {

    createController();

    productService.loadAllProducts(1,function(data){
      $scope.products = data;
      expect($scope.products.length).toBe(2);
    });

    cartService.get(function(data){
      $scope.cart = data;
      expect($scope.cart.cartItems.length).toBe(3);
    });

    expect($scope.$emit).toHaveBeenCalled();
  });

  it('should page init success', function () {

    spyOn(productService,'getPageTotal').and.callFake(function(callback){
      callback([1,2,3]);
    });

    $routeParams.pageNow = 3;
    createController();

    productService.getPageTotal(function(data){
      $scope.pageTotal = data;
      expect($scope.pageTotal.length).toBe(3);
      expect($scope.previous).toBe(2);
      expect($scope.next).toBe(3);
    });
  });

  it('should page init success', function () {

    spyOn(productService,'getPageTotal').and.callFake(function(callback){
      callback([1,2,3]);
    });

    $routeParams.pageNow = 1;
    createController();

    productService.getPageTotal(function(data){
      $scope.pageTotal = data;
      expect($scope.pageTotal.length).toBe(3);
      expect($scope.previous).toBe(1);
      expect($scope.next).toBe(2);
    });
  });

  it('should addToCart work', function () {

      spyOn(productService,'addToCart');

      createController();
      $scope.addToCart(products[0]);

      expect($scope.$emit).toHaveBeenCalled();
      expect(productService.addToCart.calls.count()).toBe(1);
      expect($scope.cart.cartItems.length).toBe(3);
  });
});
