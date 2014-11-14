package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartItemDaoImpl implements CartItemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CartItem> getCartItems(){

        List<CartItem> cartItems = null;
        String sql = "select * from cartitem";

        cartItems = jdbcTemplate.query(sql,new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet resultSet, int i) throws SQLException {
                return new CartItem(resultSet.getInt("id"),null,resultSet.getInt("count"));
            }
        });
        return cartItems;
    }

    @Override
    public CartItem getCartItem(int id){

        String sql = "select * from cartitem where id=?";

        CartItem cartItem = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet resultSet, int i) throws SQLException {
                return new CartItem(resultSet.getInt("id"),null,resultSet.getInt("count"));
            }
        });
        return cartItem;
    }

    @Override
    public void insertCartItem(CartItem cartItem){

        String sql = "insert into cartitem values(null,?,?)";

        jdbcTemplate.update(sql,cartItem.getItem().getId(),cartItem.getCount());
    }

    @Override
    public void updateCartItem(CartItem cartItem){

        String sql = "update cartitem set item_id=?,count=? where id=?";

        jdbcTemplate.update(sql,cartItem.getItem().getId(),cartItem.getCount(),cartItem.getId());
    }

    @Override
    public void deleteCartItem(int id){

        String sql = "delete from cartitem where id=?";

        jdbcTemplate.update(sql,id);
    }

    @Override
    public void deleteCartItem(){

        String sql = "delete from cartitem";

        jdbcTemplate.update(sql);
    }
}
