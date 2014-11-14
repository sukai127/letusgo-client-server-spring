'use strict';

angular.module('letusgo')
    .service('ProductService',function(CategoryService,CartService,$http){
        this.loadAllProducts = function(pageNow,callback){
            $http.get('/api/items').success(function(products){

              if(pageNow){
                products  = products.slice((pageNow-1)*2,pageNow*2);
              }

              callback(products);
            });
        };

        this.getPageTotal = function(callback){

          this.loadAllProducts(null,function(data){

            var totalCount =data.length;
            var pageCount = totalCount % 2 === 0 ?  parseInt(totalCount / 2) : parseInt(totalCount / 2) + 1;

            callback(_.range(1,pageCount + 1));
          });
        };

        this.getPrevious = function(pageNow){
          var previous = pageNow - 1 < 1 ? 1 : pageNow - 1;
          return previous;
        };

        this.insert = function(product,callback){

          var isAllFullIn = product && product.name && product.price && product.unit && product.category.id;
          if(isAllFullIn){
//            $http.post('/api/items',{product:product}).success(function(){
//              CategoryService.getCategoryById(product.categoryId,function(category){
//                product.category = category;
//                callback(product);
//              });
//            });

              CategoryService.getCategoryById(product.category.id,function(category){

                product.category = category;
                product.barcode = "ITEM00000"+9;

                $http.post('/api/items',product).success(function(){
                  callback(product);
                });
            });
          }
        };

        this.delete = function(id){
          $http.delete('/api/items/'+id);
        };

        this.updateProduct = function (product) {
          $http.put('/api/items/'+product.id,product);
        };

        this.getNext = function(pageNow,pageTotal){
          var next = pageNow + 1 > _.max(pageTotal) ? _.max(pageTotal) : pageNow + 1;
          return next;
        };

        this.addToCart = function(product){

           $http.get('/api/cartItems').success(function(cartItems){

             var existItem = function (){

               var result  = null;

               _.forEach(cartItems,function(cartItem,i){
                 if(product.id.toString() === cartItem.item.id.toString()){
                   result = cartItems[i];
                 }
               });
               return result;
             };

             var cartItem = {item : product};
             var updateItem = existItem();
             if(!updateItem){
               cartItem.count = 1;
               $http.post('/api/cartItems',cartItem);
             }else{
               updateItem.count = updateItem.count + 1;
               $http.put('/api/cartItems/'+updateItem.id,updateItem);
             }
           });
        };
    });
