package com.wechat.base;

/**
 * Created by lizhi on 2016/10/16.
 */
public class BaseService {
    //子类必须覆盖该方法
    public BaseDao getDao() {
        return null;
    }
}
