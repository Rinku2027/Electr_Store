package com.rinku.electronic.store.ElectronicStore.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartitems;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private int totalprice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
}


