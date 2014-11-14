package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.Category;
import com.thoughtworks.letusgo.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Item> getItems(){

        List<Item> itemlist;
        String sql = "select * from item";

        itemlist = jdbcTemplate.query(sql,new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {

                return new Item(resultSet.getInt("id"),resultSet.getString("barcode"),
                        resultSet.getString("name"),resultSet.getString("unit"),
                        resultSet.getDouble("price"),null);
            }
        });
        return itemlist;
    }

    @Override
    public Item getItem(int id){

        Item item;
        String sql = "select * from item where id=?";

        item = jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Item(resultSet.getInt("id"),resultSet.getString("barcode"),
                        resultSet.getString("name"),resultSet.getString("unit"),
                        resultSet.getDouble("price"),null);
            }
        });
        return item;
    }

    @Override
    public void insertItem(Item item){
        String sql = "insert into item values(null,?,?,?,?,?)";

        jdbcTemplate.update(sql,item.getBarcode(),item.getName(),item.getPrice(),item.getUnit(),item.getCategory().getId());
    }

    @Override
    public void updateItem(Item item){

        String sql = "update item set barcode=?,name=?,unit=?,price=?,category_id=? where id=?";

        jdbcTemplate.update(sql,item.getBarcode(),item.getName(),item.getUnit(),
                     item.getPrice(),item.getCategory().getId(),item.getId());
    }

    @Override
    public void deleteItem(int id){

        String sql = "delete from item where id = ?";

        jdbcTemplate.update(sql,id);
    }

    @Override
    public Item getItemByCartItemId(int id) {
        String sql = "select i.*,cg.name c_name from item i,cartitem ci,category cg where i.category_id=cg.id and i.id=ci.item_id and ci.id=?";

        Item item = jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Item(resultSet.getInt("id"),resultSet.getString("barcode"),
                        resultSet.getString("name"),resultSet.getString("unit"),
                        resultSet.getDouble("price"),new Category(resultSet.getInt("category_id"),resultSet.getString("c_name")));
            }
        });
        return item;
    }
}
