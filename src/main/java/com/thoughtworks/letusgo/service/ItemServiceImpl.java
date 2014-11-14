package com.thoughtworks.letusgo.service;

import com.thoughtworks.letusgo.dao.CategoryDao;
import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> getItems() {

        List<Item> items = itemDao.getItems();

        for(Item item : items){
            Category category = categoryDao.getCategoryByItemId(item.getId());
            item.setCategory(category);
        }

        return items;
    }

    @Override
    public Item getItem(int id) {

        Item item = itemDao.getItem(id);
        Category category = categoryDao.getCategoryByItemId(id);

        item.setCategory(category);

        return item;
    }

    @Override
    public void insertItem(Item item) {
        itemDao.insertItem(item);
    }

    @Override
    public void deleteItem(int id) {
        itemDao.deleteItem(id);
    }

    @Override
    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }
}
