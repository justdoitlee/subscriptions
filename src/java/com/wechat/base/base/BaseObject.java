package com.wechat.base.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by lizhi on 2016/10/16.
 */
public abstract class BaseObject implements Serializable {

    /*日志记录器*/
    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());

    //主键ID
    protected String objId;


    protected String companyId;

    /**
     * 创建时间
     */
    protected String createTime;

    /**
     * 更新时间
     */
    protected String updateTime;

    /**
     * 更新键
     */
    protected String updateKey;


    protected int version = 1;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getCreateTime() {
        return createTime;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateKey() {
        return updateKey;
    }

    public void setUpdateKey(String updateKey) {
        this.updateKey = updateKey;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "objId='" + objId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateKey='" + updateKey + '\'' +
                ", version=" + version +
                '}';
    }
}