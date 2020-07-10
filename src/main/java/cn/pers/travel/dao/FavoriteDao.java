package cn.pers.travel.dao;

import cn.pers.travel.domain.Favorite;

/**
 * @Author Gavin
 * @Date 2020/7/10
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);
}
