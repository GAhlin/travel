package cn.pers.travel.dao;

import cn.pers.travel.domain.Seller;

public interface SellerDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Seller findById(int id);
}
