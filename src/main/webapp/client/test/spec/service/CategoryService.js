'use strict';

describe('Service: categoryService', function () {

    var $httpBackend,categoryService,categories,products,$http;
    beforeEach(function(){
        module('letusgo');
        inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            categoryService = $injector.get('CategoryService');
            $http = $injector.get('$http');
        });
        categories = [
          {id : 1, name: 'grocery'},
          {id : 2, name: 'device'}
        ];
        products = [
          {id : 1, name: 'Instant_noodles', unit: 'bag', categoryId: 1, price: 1},
          {id : 2, name: 'apple', unit: 'kg', categoryId: 1, price: 2.5}
        ];
    });

    it('should isIncludeProduct() worked', function () {

      $httpBackend.expectGET('/api/products').respond(200,products);
      categoryService.isIncludeProduct(1,function(data){
        expect(data.name).toBe('Instant_noodles');
      });
      $httpBackend.flush();
    });

    it('should loadAllCategories() work', function () {

      spyOn(categoryService,'isIncludeProduct').and.callFake(function(id,callback){
        callback(products[0]);
      });

      $httpBackend.expectGET('/api/categories').respond(200,categories);

      categoryService.loadAllCategories(function(data){
        expect(data[0].name).toBe('grocery');
        expect(data[0].couldDelete).toBe(false);
      });

      $httpBackend.flush();
    });

    it('should loadAllCategories() work', function () {

      spyOn(categoryService,'isIncludeProduct').and.callFake(function(id,callback){
        callback(null);
      });

      $httpBackend.expectGET('/api/categories').respond(200,categories);

      categoryService.loadAllCategories(function(data){
        expect(data[0].name).toBe('grocery');
        expect(data[0].couldDelete).toBe(true);
      });

      $httpBackend.flush();
    });

    it('should insert() work when name is null', function () {

      spyOn($http,'post');

      categoryService.insert(null,function(){
      });

      expect($http.post.calls.count()).toBe(0);
    });

    it('should insert() work when name is not null', function () {

      $httpBackend.expectPOST('/api/categories').respond(200,categories[0]);

      categoryService.insert('test',function(data){
        expect(data.name).toBe('test');
        expect(data.couldDelete).toBe(true);
      });

      $httpBackend.flush();
    });

    it('should delete() worked', function () {
      spyOn($http,'delete');
      categoryService.delete(categories[1]);
      expect($http.delete.calls.count()).toBe(1);
    });

    it('should getCategoryById() work', function () {
      var id = 1;
      $httpBackend.expectGET('/api/categories/'+id).respond(200,categories[0]);

      categoryService.getCategoryById(id,function(data){
        expect(data.name).toBe('grocery');
      });

      $httpBackend.flush();
    });

    it('should updateCategory() work', function () {
      spyOn($http, 'put');
      var category = {id : 1, name: 'grocery123'};
      categoryService.updateCategory(category);
      expect($http.put.calls.count()).toBe(1);
    });

});
