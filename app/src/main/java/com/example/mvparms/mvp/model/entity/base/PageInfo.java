package com.example.mvparms.mvp.model.entity.base;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 分页基类
 * @param <T>   bean类型
 */
@Data
public class PageInfo<T> implements Serializable {
    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    private List<T> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public boolean isLastPage() {
        return pages == 0 || current == pages;
    }
}
