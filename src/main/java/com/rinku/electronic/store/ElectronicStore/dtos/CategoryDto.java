package com.rinku.electronic.store.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String CategoryId;
    @NotBlank
    @Size(min = 5, max = 15, message = "title must be within limit (min =5,max=15) .....!! ")
    private String title;
    @NotBlank
  //  @Size(min = 5, max = 30, message = "description must be within min length 5 and max length 30")
    private String description;
    private String coverImage;


}
