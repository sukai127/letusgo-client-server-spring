package com.thoughtworks.letusgo.controller;

import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryServiceImpl;

    @RequestMapping(value = "/categories" , method = RequestMethod.GET)
    public List<Category> getCategories(){
        return categoryServiceImpl.getCategories();
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable int id){
        return categoryServiceImpl.getCategory(id);
    }

    @RequestMapping(value = "/categories/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id){
        categoryServiceImpl.deleteCategory(id);
    }

    @RequestMapping(value = "/categories",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategory(@RequestBody Category category){
        categoryServiceImpl.insertCategory(category);
    }

    @RequestMapping(value = "/categories/{id}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@PathVariable int id, @RequestBody Category category){
        category.setId(id);
        categoryServiceImpl.updateCategory(category);
    }
}
