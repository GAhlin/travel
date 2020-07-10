package cn.pers.travel.service;

/**
 * @Author Gavin
 * @Date 2020/7/10
 */
public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);
}
