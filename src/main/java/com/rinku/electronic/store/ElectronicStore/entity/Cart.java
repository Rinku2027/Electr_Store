package com.rinku.electronic.store.ElectronicStore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    @Id
    private String cartId;

    private Date createdAt;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "cart",cascade = defa)
    private List<CartItem> items=new ArrayList<>();

}
