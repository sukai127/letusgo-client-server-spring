package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategories();

    Category getCategory(int id);

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);

    Category getCategoryByItemId(int itemId);
}
