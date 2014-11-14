package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.Item;

import java.util.List;

/**
 * Created by sukai on 11/12/14.
 */
public interface ItemDao {
    List<Item> getItems();

    Item getItem(int id);

    void insertItem(Item item);

    void updateItem(Item item);

    void deleteItem(int id);

    Item getItemByCartItemId(int id);
}
