package com.rinku.electronic.store.ElectronicStore.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@ToString
public class CustomeFields implements Serializable {
    @Column(name = "is_active")
    private String isActive;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdOn;
    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;
    @UpdateTimestamp
    @Column(name="modified_date",updatable = false)
    private LocalDateTime modifiedOn;


}
