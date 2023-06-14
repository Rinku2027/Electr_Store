package com.rinku.electronic.store.ElectronicStore.dtos;

import com.rinku.electronic.store.ElectronicStore.validation.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @Size(min=3,max=20,message="Name must be in between 3 char to 10 char")
    private String name;
    @NotEmpty
   @Email(message = "Invalid email format")
    private String email;
    @ImageNameValid
    private String imageName;
    @NotEmpty(message="Write something about yourself")
    private String about;
    @NotBlank(message="password must be required !!!")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$",message="Password must be at least 4 characters, no more than 8 characters, and must include at least one upper case letter, one lower case letter, and one numeric digit")
    private String password;
    @Size(min=4,max=6,message = "Invalid Gender !")
    private String gender;

}
