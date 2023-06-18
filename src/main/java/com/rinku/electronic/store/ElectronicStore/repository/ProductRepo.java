package com.rinku.electronic.store.ElectronicStore.repository;

import com.rinku.electronic.store.ElectronicStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String> {
    List<Product> findByTitleContaining(String subTitle);
    List<Product> findByLive(boolean live);

}
