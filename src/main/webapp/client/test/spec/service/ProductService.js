'use strict';

describe('Service: ProductService', function () {

  var $httpBackend, productService, cartService,products,$http,categories,categoryService;

  beforeEach(function () {

    module('letusgo');

    inject(function ($injector) {
      $httpBackend = $injector.get('$httpBackend');
      $http = $injector.get('$http');
      productService = $injector.get('ProductService');
      cartService = $injector.get('CartService');
      categoryService = $injector.get('CategoryService');
    });

    products = [
      {id : 1, name: 'Instant_noodles', unit: 'bag', categoryId: 1, price: 1},
      {id : 2, name: 'apple', unit: 'kg', categoryId: 2, price: 2.5},
      {id : 3, name: 'banana', unit: 'kg', categoryId: 2, price: 3.5}
    ];

    categories = [
      {id : 1, name: 'grocery'},
      {id : 2, name: 'device'}
    ];

    spyOn(categoryService,'getCategoryById').and.callFake(function(id,callback){
      callback(categories[id-1]);
    });
  });

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
  });

  it('should loadAllProducts() work  when pageNow not null', function () {
    $httpBackend.expectGET('/api/products').respond(200,products);

    productService.loadAllProducts(1,function(data){
      expect(data.length).toBe(2);
      expect(data[1].category.name).toBe('device');
    });
    $httpBackend.flush();
  });

  it('should loadAllProducts() work  when pageNow is null', function () {
    $httpBackend.expectGET('/api/products').respond(200,products);

    productService.loadAllProducts(null,function(data){
      expect(data.length).toBe(3);
      expect(data[1].category.name).toBe('device');
    });
    $httpBackend.flush();
  });

  it('should getPageTotal() work when product.length % 2 == 0', function () {
    spyOn(productService,'loadAllProducts').and.callFake(function(pageNow,callback){
      callback(products);
    });
    productService.getPageTotal(function(data){
      expect(data.length).toBe(2);
    });
  });

  it('should getPageTotal() work when product.length % 2 != 0', function () {
    products[3] = {id:4, name: 'banana', unit: 'kg', categoryId: 2, price: 5.5};
    spyOn(productService,'loadAllProducts').and.callFake(function(pageNow,callback){
      callback(products);
    });
    productService.getPageTotal(function(data){
      expect(data.length).toBe(2);
    });
  });

  it('should getPrevious worked', function () {
    var pageNow = 1;
    var result = productService.getPrevious(pageNow);
    expect(result).toBe(1);
    pageNow = 3;
    result = productService.getPrevious(pageNow);
    expect(result).toBe(2);
  });

  it('should getNext worked', function () {
    var pageNow = 1;
    var pageTotal = [1,2,3];
    var result = productService.getNext(pageNow,pageTotal);
    expect(result).toBe(2);
    pageNow = 3;
    result = productService.getNext(pageNow,pageTotal);
    expect(result).toBe(3);
  });

  it('should delete() worked', function () {
    spyOn($http,'delete');
    productService.delete(products[1]);
    expect($http.delete.calls.count()).toBe(1);
  });

  it('should update() worked', function () {
    spyOn($http,'put');
    productService.updateProduct(products[1]);
    expect($http.put.calls.count()).toBe(1);
  });

  it('should insert() worked when product is null', function () {
    spyOn($http,'post');
    productService.insert(null,function(){
    });
    expect($http.post.calls.count()).toBe(0);
  });

  it('should insert() worked when product is not null', function () {

    $httpBackend.expectPOST('/api/products').respond(200,products);

    var product = {name: 'banana', unit: 'kg', categoryId: 2, price: 3.5};

    productService.insert(product,function(data){
      expect(data.name).toBe('banana');
      expect(data.category.name).toBe('device');
    });
    $httpBackend.flush();
  });

  it('should addToCart worked when it exist', function () {

    var product = {id: 1,name: 'fan', unit: 'piece', categoryId: 1, price: 30};
    var cartItems = [{id: 1,productId:1 , count : 2}];

    $httpBackend.expectGET('/api/cartItems').respond(200,cartItems);
    $httpBackend.expectPUT('/api/cartItems/1').respond(200,cartItems);
    spyOn($http,'post');

    productService.addToCart(product);

    expect($http.post.calls.count()).toBe(0);

    $httpBackend.flush();
  });


  it('should addToCart worked when it not exist', function () {

    var product = {id: 1,name: 'fan', unit: 'piece', categoryId: 1, price: 30};
    var cartItems = [{id: 1,productId:2 , count : 2}];

    $httpBackend.expectGET('/api/cartItems').respond(200,cartItems);
    $httpBackend.expectPOST('/api/cartItems').respond(200,cartItems);
    spyOn($http,'put');

    productService.addToCart(product);

    expect($http.put.calls.count()).toBe(0);

    $httpBackend.flush();
  });
});
