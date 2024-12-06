package org.learning.dlearning_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int currentPage; // trang hiện tại
    int totalPages; // tổng số trang
    int pageSize;   // size của trang là bao nhiêu
    long totalElements; // tổng số phần tử, element

    @Builder.Default
    List<T> data = Collections.emptyList();

}


/*

ở Frontend cần cung cấp và truyền 2 thông tin cho backend đó là: Trang muốn lấy là bao nhiêu ? và số lượng row, phần tử trên 1 trang là bao nhiêu ?

Để phân trang trong spring chúng ta sử dụng interface Pageable


* */
