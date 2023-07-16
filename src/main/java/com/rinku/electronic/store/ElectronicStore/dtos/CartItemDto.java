package com.rinku.electronic.store.ElectronicStore.dtos;

import com.rinku.electronic.store.ElectronicStore.entity.Cart;
import com.rinku.electronic.store.ElectronicStore.entity.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartitems;
    private ProductDto productdto;
    private int quantity;
    private int totalprice;
}
