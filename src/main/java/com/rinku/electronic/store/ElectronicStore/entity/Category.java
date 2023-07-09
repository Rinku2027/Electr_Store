package com.rinku.electronic.store.ElectronicStore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
@Builder
public class Category {
    @Id
    @Column(name="id")
    private String CategoryId;
    @Column(name="category_title", length = 60,nullable = false)
    private String title;
    @Column(name="category_desc", length = 500)
    private String description;
    private String coverImage;


    //Other attributes


}
