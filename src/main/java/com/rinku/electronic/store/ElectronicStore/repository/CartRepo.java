package com.rinku.electronic.store.ElectronicStore.repository;

import com.rinku.electronic.store.ElectronicStore.entity.Cart;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
