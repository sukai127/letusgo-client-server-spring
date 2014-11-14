package com.thoughtworks.letusgo.service;

import com.thoughtworks.letusgo.model.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> getCartItems();

    CartItem getCartItem(int id);

    void insertCartItem(CartItem cartItem);

    void updateCartItem(CartItem cartItem);

    void deleteCartItem(int id);

    void deleteCartItem();

}
