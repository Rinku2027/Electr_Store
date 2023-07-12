package com.rinku.electronic.store.ElectronicStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddItemTOCartRequest {
    private String productId;
    private int quantity;

}
