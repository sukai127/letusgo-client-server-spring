'use strict';

describe('Controller: CartCtrl', function () {

  var createController,$controller,cart,cartService,$scope;

  beforeEach(function(){
    module('letusgo');
    inject(function ($injector) {
      $scope = $injector.get('$rootScope').$new();
      $controller = $injector.get('$controller');
      cartService = $injector.get('CartService');
    });

    createController = function(){
      return $controller('CartCtrl', {
        $scope: $scope,
        CartService: cartService
      });
    };
    cart = {
      cartItems: [
        {
          product: {name : 'Instant_noodles', unit : 'bag', category : 'grocery', price : 1},
          count : 4
        },
        {
          product: {name : 'coca_cola', unit : 'bottle', category : 'grocery', price : 0.5},
          count : 3
        },
        {
          product: {name : 'kettle', unit : 'piece', category : 'device', price : 43.5},
          count : 1
        }
      ],
      len : 8
    };
    spyOn(cartService,'get').and.callFake(function(callback){
      callback(cart);
    });
  });

  it('should init success', function () {

    createController();

    cartService.get(function(data){
      $scope.cart = data;
      expect($scope.cart.cartItems.length).toBe(3);
      expect($scope.isCartEmpty).toEqual(false);
      expect($scope.totalMoney).toBe(49);
    });
  });

  it('should getSubtotal() work', function () {
      createController();
      spyOn(cartService,'getSubtotal').and.returnValue(43.5);
      var result = $scope.getSubtotal(cart.cartItems[2]);
      expect(result).toBe(43.5);
  });

  describe('CartCtrl: update', function () {

    beforeEach(function(){
      spyOn(cartService,'getTotalMoney');
      spyOn($scope,'$emit');
    });

    it('should #watch() work when cart is empty', function () {

      createController();

      $scope.cart = {cartItems: [
        {
        product: {name : 'Instant_noodles', unit : 'bag', category : 'grocery', price : 1},
        count : -1
        },
        {
          product: {name : 'coca_cola', unit : 'bottle', category : 'grocery', price : 0.5},
          count : 3
        }
      ], len: 3};

      $scope.$apply();
      expect(cartService.getTotalMoney.calls.count()).toBe(2);
      expect($scope.$emit).toHaveBeenCalled();
    });

    it('should #watch() work when cart is not empty', function () {

      createController();

      $scope.cart = null;
      $scope.$apply();
      expect(cartService.getTotalMoney.calls.count()).toBe(1);
      expect($scope.$emit.calls.count()).toBe(1);
    });

    it('should deleteItem() work', function () {

      createController();

      var count = $scope.cart.cartItems.length;
      count = count < 1 ? 1: count;
      $scope.deleteItem(1);

      var currentCount = $scope.cart.cartItems.length;
      expect(count-1).toBe(currentCount);

    });

    it('should updateItem() work when count > 0', function () {

      spyOn(cartService,'update');
      createController();

      $scope.updateItem(1);

      expect(cartService.update).toHaveBeenCalled();

    });

    it('should updateItem() work when count < 0', function () {

      createController();
      spyOn($scope,'deleteItem');

      $scope.cart.cartItems[1].count = -1;
      $scope.updateItem(1);

      expect($scope.deleteItem.calls.count()).toBe(1);

    });
  });
});
