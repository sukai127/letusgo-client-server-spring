'use strict';

angular.module('letusgo')
  .service('CartService', function ($http) {

    this.get = function (callback) {

      var service = this;

      $http.get('/api/cartItems').success(function (data) {

        service.buildCartItem(data, function (cartItems) {

          var cart = {cartItems: cartItems,
            count: service.getTotalCount(cartItems)
          };

          callback(cart);
        });
      });
    };

    this.buildCartItem = function (cartItems, callback) {

      $http.get('/api/items').success(function (data) {
//        _.forEach(cartItems, function (cartItem) {
//          var product = _.find(data, function (item) {
//            return item.id.toString() === cartItem.item.id.toString();
//          });
//          cartItem.product = product;
//        });
        callback(cartItems);
      });
    };

    this.add = function (cartItem) {
      $http.post('/api/cartItems', cartItem);
    };

    this.getTotalMoney = function (cartItems) {
      var sum = 0;
      _.forEach(cartItems, function (cartitem) {
        sum += cartitem.product.price * cartitem.count;
      });
      return sum;
    };

    this.getTotalCount = function (cartItems) {

      return _.reduce(_.pluck(cartItems, 'count'), function (count1, count2) {
        return count1 + count2;
      });
    };

    this.getSubtotal = function (cartitem) {
      return (cartitem.product.price * cartitem.count).toFixed(2);
    };

    this.delete = function (cartItem) {
      $http.delete('/api/cartItems/' + cartItem.id);
    };

    this.update = function (cartItem) {
      $http.put('/api/cartItems/' + cartItem.id, cartItem);
    };

    this.remove = function (callback) {
      $http.post('/api/payment').success(function (data, status) {
        if (status === 200) {
          callback();
        }
      });
    };
  });

