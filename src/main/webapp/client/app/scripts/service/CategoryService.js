'use strict';
angular.module('letusgo')
    .service('CategoryService',function($http){

        this.loadAllCategories = function(callback){

            var service = this;

            $http.get('/api/categories').success(function(categories){

              _.forEach(categories,function(category){
                service.isIncludeProduct(category.id,function(data){
                  category.couldDelete = data ? false: true;
                });

                callback(categories);
              });
            });
        };

        this.insert = function(name,callback){

          if(name){

            var category = {name:name};

            $http.post('/api/categories',category).success(function(){

              $http.get('/api/categories').success(function(data){

                var id = _.max(data, function(cate) { return cate.id; }).id;

                category.id = id;
                category.couldDelete = true;
                callback(category);

              });
            });
          }
        };

        this.delete = function(id){
          $http.delete('/api/categories/'+id);
        };

        this.isIncludeProduct = function(id,callback){

          $http.get('/api/items').success(function(products){

            var result = _.find(products,function(product){
              return product.category.id.toString() === id.toString();
            });
            callback(result);
          });
        };

        this.getCategoryById = function(id,callback){
          $http.get('/api/categories/'+id).success(function(data){
            callback(data);
          });
        };

        this.updateCategory = function(category){
          $http.put('/api/categories/'+category.id,category);
        };
    });
