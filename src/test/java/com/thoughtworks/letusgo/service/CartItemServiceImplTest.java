package com.thoughtworks.letusgo.service;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.letusgo.dao.CartItemDao;
import com.thoughtworks.letusgo.dao.CartItemDaoImpl;
import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.dao.ItemDaoImpl;
import com.thoughtworks.letusgo.model.CartItem;
import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartItemServiceImplTest {

    private CartItemService cartItemService = new CartItemServiceImpl();
    private CartItemDao cartItemDao ;
    private ItemDao itemDao;
    private ImmutableList<CartItem> cartItems;

    @Before
    public void init(){
        cartItemDao = mock(CartItemDaoImpl.class);
        itemDao = mock(ItemDaoImpl.class);
        cartItems = ImmutableList.of(new CartItem(1,new Item(1,"ITEM000001","苹果","斤",3.5,new Category(1,"水果")),3),
                new CartItem(2,new Item(2,"ITEM000002","可乐","瓶",3.5,new Category(2,"饮料")),1),
                new CartItem(3,new Item(3,"ITEM000003","鞋","双",99,new Category(3,"服装")),2));

        when(cartItemDao.getCartItems()).thenReturn(cartItems);
        when(itemDao.getItemByCartItemId(1)).thenReturn(cartItems.get(0).getItem());
        when(cartItemDao.getCartItem(1)).thenReturn(cartItems.get(0));

        ReflectionTestUtils.setField(cartItemService,"cartItemDao",cartItemDao);
        ReflectionTestUtils.setField(cartItemService,"itemDao",itemDao);
    }

    @Test
    public void should_return_cartItemList(){

        List<CartItem> cartItemList = cartItemService.getCartItems();

        assertThat(cartItemList.size()).isEqualTo(3);
        assertThat(cartItemList.get(0).getCount()).isEqualTo(3);
        assertThat(cartItemList.get(0).getItem().getName()).isEqualTo("苹果");
        assertThat(cartItemList.get(0).getItem().getCategory().getName()).isEqualTo("水果");

    }

    @Test
    public void should_return_cartItem_when_input_id(){

        CartItem cartItem = cartItemService.getCartItem(1);

        assertThat(cartItem.getCount()).isEqualTo(3);
    }

    @Test
    public void should_insert_CartItem(){

        cartItemService.insertCartItem(cartItems.get(0));
        verify(cartItemDao).insertCartItem(cartItems.get(0));
    }

    @Test
    public void should_update_CartItem(){

        cartItemService.updateCartItem(cartItems.get(0));
        verify(cartItemDao).updateCartItem(cartItems.get(0));
    }

    @Test
    public void should_delete_CartItem(){

        cartItemService.deleteCartItem(1);
        verify(cartItemDao).deleteCartItem(1);
    }

    @Test
    public void should_delete_all_cartitem(){

        cartItemService.deleteCartItem();

        verify(cartItemDao).deleteCartItem();
    }
}
