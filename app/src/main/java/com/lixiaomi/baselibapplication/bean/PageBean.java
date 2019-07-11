package com.lixiaomi.baselibapplication.bean;

/**
 * Description: 分页查询参数<br>
 * User: dell - XiaomiLi<br>
 * Date: 2018-10-18<br>
 * Time: 15:03<br>
 * UpdateDescription：<br>
 */
public class PageBean {
    private int pageNumber;
    private int pageSize;

    public PageBean() {
    }

    public PageBean(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
