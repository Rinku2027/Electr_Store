package com.rinku.electronic.store.ElectronicStore.dtos;

import com.rinku.electronic.store.ElectronicStore.entity.CustomeFields;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomDto extends CustomeFields {
    private String isActive;

    private String createdBy;

    private LocalDateTime createdOn;

    private String modifiedBy;

    private LocalDateTime modifiedOn;

}
