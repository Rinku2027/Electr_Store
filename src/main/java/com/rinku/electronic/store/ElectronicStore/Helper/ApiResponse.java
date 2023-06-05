package com.rinku.electronic.store.ElectronicStore.Helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean status;
}
