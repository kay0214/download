package com.sandman.download.utils;

import java.util.List;

public class PageBean<T> {
    private Integer currentPage = 1;//当前页
    private Integer pageSize = 10;//每页个数
    private Integer totalNumber;//总个数
    private Integer totalPage;//总页数
    private Integer isMore;//是否有下一页
    private Integer startIndex;//开始索引
    private List<T> items;

    public PageBean(Integer currentPage, Integer pageSize, Integer totalNumber) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNumber = totalNumber;
        this.totalPage = (this.totalNumber + this.pageSize - 1)/this.pageSize;
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage?0:1;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getIsMore() {
        return isMore;
    }

    public void setIsMore(Integer isMore) {
        this.isMore = isMore;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
