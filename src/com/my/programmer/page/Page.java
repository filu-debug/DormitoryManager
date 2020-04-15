package com.my.programmer.page;

public class Page {
    private int page;//当前页码
    private int rows;//每页记录条数
    private int offset;//偏移量

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        //计算每页的起始位置
        this.offset = (page-1)* rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
