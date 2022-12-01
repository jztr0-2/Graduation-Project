package com.poly.jztr.ecommerce.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableSerializer {
    public PageableSerializer(Page page, Object content){
        this.nextPage = page.getNumber() < page.getTotalPages();
        this.prevPage = page.getNumber() > 0;
        this.totalItem = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.content = content;
    }

    public PageableSerializer(Page page){
        this.nextPage = page.getNumber() < page.getTotalPages();
        this.prevPage = page.getNumber() > 0;
        this.totalItem = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.content = page.getContent();
    }
    private Object content;

    private int currentPage;
    private boolean nextPage;
    private int totalPage;
    private long totalItem;
    private boolean prevPage;
}
