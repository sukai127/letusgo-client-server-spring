package com.thoughtworks.letusgo.service;

import com.thoughtworks.letusgo.dao.CartItemDao;
import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.model.CartItem;
import com.thoughtworks.letusgo.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<CartItem> getCartItems() {
        List<CartItem> cartItemList = cartItemDao.getCartItems();

        for(CartItem cartItem:cartItemList){
            Item item = itemDao.getItemByCartItemId(cartItem.getId());
            cartItem.setItem(item);
        }

        return cartItemList;
    }

    @Override
    public CartItem getCartItem(int id) {

        CartItem cartItem = cartItemDao.getCartItem(id);
        Item item = itemDao.getItemByCartItemId(id);
        cartItem.setItem(item);

        return cartItem;
    }

    @Override
    public void insertCartItem(CartItem cartItem) {
        cartItemDao.insertCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(int id) {
        cartItemDao.deleteCartItem(id);
    }

    @Override
    public void deleteCartItem() {
        cartItemDao.deleteCartItem();
    }
}
