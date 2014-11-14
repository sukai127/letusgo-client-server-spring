package com.thoughtworks.letusgo.service;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.letusgo.dao.CategoryDao;
import com.thoughtworks.letusgo.dao.CategoryDaoImpl;
import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.dao.ItemDaoImpl;
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

public class ItemServiceImplTest {

    private ItemService itemService = new ItemServiceImpl();
    private ImmutableList<Item> itemList;
    private ItemDao itemDao;
    private CategoryDao categoryDao;

    @Before
    public void init(){
        itemDao = mock(ItemDaoImpl.class);
        categoryDao = mock(CategoryDaoImpl.class);
        itemList = ImmutableList.of(new Item(1,"ITEM000001","苹果","斤",3.5,new Category(1,"水果")),
                new Item(2,"ITEM000002","可乐","瓶",3.5,new Category(2,"饮料")),
                new Item(3,"ITEM000003","鞋","双",95,new Category(3,"服装")));

        when(itemDao.getItems()).thenReturn(itemList);
        when(itemDao.getItem(1)).thenReturn(itemList.get(0));
        when(categoryDao.getCategoryByItemId(1)).thenReturn(itemList.get(0).getCategory());

        ReflectionTestUtils.setField(itemService,"itemDao",itemDao);
        ReflectionTestUtils.setField(itemService,"categoryDao",categoryDao);
    }

    @Test
    public void should_return_itemList(){

        List<Item> itemList = itemService.getItems();

        assertThat(itemList.size()).isEqualTo(3);
        assertThat(itemList.get(1).getPrice()).isEqualTo(3.5);
        assertThat(itemList.get(0).getCategory().getName()).isEqualTo("水果");
    }

    @Test
    public void should_return_item_when_input_id(){

        Item item = itemService.getItem(1);

        assertThat(item.getBarcode()).isEqualTo("ITEM000001");
    }

    @Test
    public void should_insert_item(){

        itemService.insertItem(itemList.get(0));
        verify(itemDao).insertItem(itemList.get(0));
    }

    @Test
    public void should_delete_item(){

        itemService.deleteItem(1);
        verify(itemDao).deleteItem(1);
    }


    @Test
    public void should_update_item(){

        itemService.updateItem(itemList.get(0));
        verify(itemDao).updateItem(itemList.get(0));
    }
}
