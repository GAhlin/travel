package cn.pers.travel.dao.impl;

import cn.pers.travel.dao.RouteImgDao;
import cn.pers.travel.domain.Category;
import cn.pers.travel.domain.Route;
import cn.pers.travel.domain.RouteImg;
import cn.pers.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
