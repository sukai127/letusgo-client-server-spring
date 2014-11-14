package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.CartItem;

import java.util.List;

/**
 * Created by sukai on 11/13/14.
 */
public interface CartItemDao {
    List<CartItem> getCartItems();

    CartItem getCartItem(int id);

    void insertCartItem(CartItem cartItem);

    void updateCartItem(CartItem cartItem);

    void deleteCartItem(int id);

    void deleteCartItem();
}
