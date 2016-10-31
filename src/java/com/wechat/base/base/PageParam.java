package com.wechat.base.base;

/**
 * Created by lizhi on 2016/10/16.
 */
public class PageParam {

    public static final int MIN_PAGESIZE = 1;
    public static final int DEFAULT_PAGESIZE = 10;
    public static final int MAX_PAGESIZE = 100;
    public static final int MAX_PAGENUM = 200;

    protected int pageNo = 1;
    protected int pageSize = DEFAULT_PAGESIZE;
    protected boolean autoCount = true;
    /**
     * 排序的字段名
     */
    protected String sortFieldName;
    /**
     * 排序的类型
     */
    protected String sortType;


    public PageParam() {
        this.pageNo = 1;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public PageParam(int pageNo) {
        this.pageNo = pageNo;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public int getPageNo() {
        return pageNo;
    }

    //若查询结果为0，则当前页面显示0
    public int getPageNo(int totalPage) {
        if (totalPage == 0) {
            return 0;
        }
        return pageNo;
    }

    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }

//        if (pageNo > MAX_PAGENUM) {
//            this.pageNo = MAX_PAGENUM;
//        }
    }

    public void setPageNoFromTaobao(final int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        if (pageSize < MIN_PAGESIZE) {
            this.pageSize = MIN_PAGESIZE;
            return;
        }
//        if (pageSize > MAX_PAGESIZE) {
//            this.pageSize = MAX_PAGESIZE;
//            return;
//        }
        this.pageSize = pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize);
    }

    public int getLast() {
        return getFirst() + pageSize;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数,默认为false,仅在Criterion查询时有效.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数,仅在Criterion查询时有效.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public static PageParam createPageParam(int pageNo) {
        return new PageParam(pageNo);
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}