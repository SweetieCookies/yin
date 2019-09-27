package com.qf.domain;

import java.util.List;

public class PageBean <T>{
    private int pageNum;
    private int pageSize;
    private long totalSize;
    private int pageCount;
    private int startPage;
    private int endPage;
    private List<T> data;

    public PageBean(int pageNum, int pageSize, long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;
        this.pageCount= (int) (totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1);
        //正常情况
        startPage=pageNum-4;//从第六页开始
        endPage=pageNum+5;
        if(pageNum<5){
            startPage=1;
            endPage=10;
        }
        if(pageNum>(pageCount-5)){
            startPage=pageCount-9;
            endPage=pageCount;
        }
        if(pageCount<10){
            startPage=1;
            endPage=pageCount;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", pageCount=" + pageCount +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", data=" + data +
                '}';
    }
}
