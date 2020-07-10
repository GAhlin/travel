package cn.pers.travel.dao.impl;

import cn.pers.travel.dao.SellerDao;
import cn.pers.travel.domain.RouteImg;
import cn.pers.travel.domain.Seller;
import cn.pers.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int id) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), id);
    }
}
