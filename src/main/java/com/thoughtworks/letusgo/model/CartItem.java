package com.thoughtworks.letusgo.model;

public class CartItem {
    private int id;
    private com.thoughtworks.letusgo.model.Item item;
    private int count;

    public CartItem() {
    }

    public CartItem(int id, Item item, int count) {
        this.id = id;
        this.item = item;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", item=" + item +
                ", count=" + count +
                '}';
    }
}
