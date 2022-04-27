package com.ead.eadnotificationhex.core.domain;

import org.springframework.data.domain.Page;

public class PageInfo {

    private int page;

    private int size;

    public PageInfo(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
