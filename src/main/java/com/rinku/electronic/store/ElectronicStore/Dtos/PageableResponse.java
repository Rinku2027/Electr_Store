package com.rinku.electronic.store.ElectronicStore.Dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {
private List<T> content;
private int pageNumber;
private int pageSize;
private long totalElements;
private int totalPages;
private boolean lastPage;

}
