package com.thoughtworks.letusgo.controller;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.model.Item;
import com.thoughtworks.letusgo.service.ItemService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
@WebAppConfiguration
public class ItemControllerTest {

    private MockMvc mockMvc;
    private ImmutableList<Item> itemList;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();

        itemList = ImmutableList.of(new Item(1, "ITEM000001", "苹果", "斤", 3.5, new Category(1, "水果")),
                new Item(2, "ITEM000002", "可乐", "瓶", 3.5, new Category(2, "饮料")),
                new Item(3, "ITEM000003", "鞋", "双", 95, new Category(3, "服装")));
        when(itemService.getItems()).thenReturn(itemList);
        when(itemService.getItem(1)).thenReturn(itemList.get(0));
    }

    @Test
    public void should_return_itemList() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("苹果")));
    }

    @Test
    public void should_return_item_when_input_id() throws Exception {
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("苹果")));
    }

    @Test
    public void should_delete_item_when_input_id() throws Exception {
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().is(204));
        verify(itemService, times(1)).deleteItem(1);
    }

    @Test
    public void should_insert_item() throws Exception {
        String requestBody = "{\"id\":1, \"barcode\":\"ITEM000001\", " +
                "\"name\":\"苹果\", " +
                "\"price\":3.5, " +
                "\"unit\":\"斤\", " +
                "\"category\":{\"id\":1,\"name\":\"水果\"}}";
        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        ArgumentCaptor<Item> item = ArgumentCaptor.forClass(Item.class);
        verify(itemService, times(1)).insertItem(item.capture());
    }

    @Test
    public void should_update_item() throws Exception {
        String requestBody = "{\"id\":1, \"barcode\":\"ITEM000001\", " +
                "\"name\":\"苹果\", " +
                "\"price\":3.5, " +
                "\"unit\":\"斤\", " +
                "\"category\":{\"id\":1,\"name\":\"水果\"}}";
        mockMvc.perform(put("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is2xxSuccessful());

        ArgumentCaptor<Item> item = ArgumentCaptor.forClass(Item.class);
        verify(itemService, times(1)).updateItem(item.capture());
    }
}
