package com.thoughtworks.letusgo.controller;

import com.thoughtworks.letusgo.model.CartItem;
import com.thoughtworks.letusgo.model.Item;
import com.thoughtworks.letusgo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @RequestMapping(value = "/cartItems" ,method = RequestMethod.GET)
    public List<CartItem> getCartItems(){
        return cartItemService.getCartItems();
    }

    @RequestMapping(value = "/cartItems/{id}", method = RequestMethod.GET)
    public CartItem getCartItem(@PathVariable int id) {
        return cartItemService.getCartItem(id);
    }

    @RequestMapping(value = "/cartItems/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable int id){
     cartItemService.deleteCartItem(id);
    }

    @RequestMapping(value = "/cartItems", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCartItem(@RequestBody CartItem cartItem) {
        cartItemService.insertCartItem(cartItem);
    }

    @RequestMapping(value = "/cartItems/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCartItem(@PathVariable int id, @RequestBody CartItem cartItem) {
        cartItem.setId(id);
        cartItemService.updateCartItem(cartItem);
    }
}
