package com.rinku.electronic.store.ElectronicStore.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean status;
}
