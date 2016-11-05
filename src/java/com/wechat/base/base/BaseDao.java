package com.wechat.base.base;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhi on 2016/10/16.
 */
public interface BaseDao<E> {
    /**
     * 保存
     *
     * @param entity
     * @return
     */
    int save(E entity);

    /**
     * 根据主键删除
     *
     * @param objId
     * @return
     */
    int deleteByObjId(String objId);

    /**
     * 根据查询条件删除
     *
     * @param param
     * @return
     */
    int deleteByParam(Map<String, Object> param);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int update(E entity);

    /**
     * 根据主键更新不为空的字段
     *
     * @param entity
     * @return
     */
    int updateByIdSelective(E entity);

    /**
     * 根据主键查询对象
     *
     * @param objId
     * @return
     */
    E selectByObjId(String objId);

    /**
     * 根据条件查询结果集,包括传入分页参数
     *
     * @param param
     * @return
     */
    List<E> queryList(Map<String, Object> param);

    /**
     * 根据条件查询结果集数量
     *
     * @param param
     * @return
     */
    int countList(Map<String, Object> param);


}