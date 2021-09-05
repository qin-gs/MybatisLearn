package com.mybatis.learn.page;

public class Page {
    /**
     * 总行数
     */
    private int total;
    /**
     * 每页大小
     */
    private int size;
    /**
     * 页码
     */
    private int index;

    public Page(int size, int index) {
        this.size = size;
        this.index = index;
    }

    public int getOffset() {
        return size * (index - 1);
    }

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
