package com.thoughtworks.letusgo.controller;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.model.Item;
import com.thoughtworks.letusgo.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
@WebAppConfiguration
public class CategoryControllerTest {
    private MockMvc mockMvc;
    private ImmutableList<Category> categoryList;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        categoryList = ImmutableList.of(
                new Category(1,"水果"),
                new Category(2,"饮料"),
                new Category(3,"服装")
        );

        when(categoryService.getCategories()).thenReturn(categoryList);
        when(categoryService.getCategory(1)).thenReturn(categoryList.get(0));
    }

    @Test
    public void should_return_categoryList() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("水果")));
    }

    @Test
    public void should_return_category_when_input_id() throws Exception {
        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("水果")));
    }

    @Test
    public void should_delete_category_when_input_id() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().is(204));
        verify(categoryService, times(1)).deleteCategory(1);
    }

    @Test
    public void should_insert_category() throws Exception {
        String requestBody = "{\"id\":1,\"name\":\"水果\"}";

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);
        verify(categoryService, times(1)).insertCategory(category.capture());
    }

    @Test
    public void should_update_category() throws Exception {
        String requestBody = "{\"id\":1,\"name\":\"水果\"}";

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is2xxSuccessful());

        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);
        verify(categoryService, times(1)).updateCategory(category.capture());
    }
}
