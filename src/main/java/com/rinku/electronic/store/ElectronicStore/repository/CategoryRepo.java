package com.rinku.electronic.store.ElectronicStore.repository;

import com.rinku.electronic.store.ElectronicStore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,String> {
}
