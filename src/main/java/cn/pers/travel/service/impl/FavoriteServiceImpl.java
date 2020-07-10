package cn.pers.travel.service.impl;

import cn.pers.travel.dao.FavoriteDao;
import cn.pers.travel.dao.impl.FavoriteDaoImpl;
import cn.pers.travel.domain.Favorite;
import cn.pers.travel.service.FavoriteService;

/**
 * @Author Gavin
 * @Date 2020/7/10
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }
}
