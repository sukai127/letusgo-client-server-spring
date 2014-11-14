package com.thoughtworks.letusgo.dao;

import com.thoughtworks.letusgo.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> getCategories(){

        List<Category> categories;
        String sql = "select * from category";

        categories = jdbcTemplate.query(sql,new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Category(resultSet.getInt("id"),resultSet.getString("name"));
            }
        });
        return categories;
    }

    @Override
    public Category getCategory(int id){

        String sql = "select * from category where id=?";

        Category category = jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Category(resultSet.getInt("id"),resultSet.getString("name"));
            }
        });
        return category;
    }

    @Override
    public void insertCategory(Category category){

        String sql = "insert into category values(null,?)";
        jdbcTemplate.update(sql,category.getName());
    }

    @Override
    public void updateCategory(Category category){

        String sql = "update category set name=? where id=?";
        jdbcTemplate.update(sql,category.getName(),category.getId());
    }

    @Override
    public void deleteCategory(int id){

        String sql = "delete from category where id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public Category getCategoryByItemId(int itemId) {

        String sql = "select c.* from item i,category c where i.category_id=c.id and i.id=?";

        Category category = jdbcTemplate.queryForObject(sql,new Object[]{itemId},new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Category(resultSet.getInt("id"),resultSet.getString("name"));
            }
        });
        return category;
    }
}
