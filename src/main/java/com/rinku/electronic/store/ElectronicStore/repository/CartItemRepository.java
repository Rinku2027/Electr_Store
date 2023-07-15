package com.rinku.electronic.store.ElectronicStore.repository;

import com.rinku.electronic.store.ElectronicStore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

}
