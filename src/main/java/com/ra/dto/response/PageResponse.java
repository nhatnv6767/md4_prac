package com.ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    // Constructor nhan vao mot tham so kieu Page<T> de khoi tao doi tuong
    // PageResponse
    // - content: lay danh sach cac phan tu trong trang hien tai
    // - pageNo: lay so thu tu cua trang hien tai
    // - pageSize: lay kich thuoc cua moi trang (so phan tu toi da trong 1 trang)
    // - totalElements: lay tong so phan tu cua tat ca cac trang
    // - totalPages: lay tong so trang
    // - last: kiem tra xem co phai la trang cuoi cung hay khong
    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNo = page.getNumber();

        // Lay kich thuoc cua moi trang thong qua phuong thuc getSize()
        // Day la so phan tu toi da duoc hien thi trong 1 trang
        // Vi du: pageSize = 10 thi moi trang chi hien thi toi da 10 phan tu
        this.pageSize = page.getSize();

        // Lay tong so phan tu cua tat ca cac trang thong qua phuong thuc
        // getTotalElements()
        // Vi du: neu co 100 phan tu va moi trang hien thi 10 phan tu
        // Thi totalElements = 100
        this.totalElements = page.getTotalElements();

        // Lay tong so trang thong qua phuong thuc getTotalPages()
        // Duoc tinh bang cach lay tong so phan tu chia cho kich thuoc moi trang
        // Vi du: 100 phan tu, moi trang 10 phan tu => totalPages = 10 trang
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}
