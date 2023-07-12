package com.rinku.electronic.store.ElectronicStore.service;

import com.rinku.electronic.store.ElectronicStore.dtos.AddItemTOCartRequest;
import com.rinku.electronic.store.ElectronicStore.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemTOCartRequest request);
    void removeItemfromCart(String userId,int cartItem);
    void clearCart(String userId);





}
