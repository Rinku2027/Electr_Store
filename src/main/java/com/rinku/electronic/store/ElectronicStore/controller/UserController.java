package com.rinku.electronic.store.ElectronicStore.controller;

import com.rinku.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.rinku.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.service.FileService;
import com.rinku.electronic.store.ElectronicStore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
private Logger logger= LoggerFactory.getLogger(UserController.class);

    /**
     * @param userDto
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to create user
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to create user");
        UserDto userDto1 = userService.createUser(userDto);
        log.info("Request completed for service layer to save the user");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    /**
     * @param userId
     * @param userDto
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to Update user
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to update user");
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        log.info("Request Starting for service layer to update user");
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to Delete user
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        log.info("Request Starting for service layer to delete user by userId {}", userId);
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted Sucessfully!!  ")
                .success(true).
                status(HttpStatus.OK)
                .build();
        log.info("Request completed for service layer to delete user by userId {}", userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to GetAllUser Information
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Request  for service layer to get All user ");
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to Get Single User by Id
     */
    @GetMapping("/{userId}")

    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Request for service layer to get user by userId {}", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to Get User By Email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        log.info("Request  for service layer to get user by email {}", email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
    /**
     * @param keywords
     * @return
     * @author Rinku Patil
     * @ApiNote This API is used to Search user
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        log.info("Request for service layer to seach user by keywords {}", keywords);

        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }


    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        {
            log.info("Request Starting for fileservice layer to upload image with userID {}", userId);
            String imageName = fileService.uploadFile(image, imageUploadPath);
            UserDto user = userService.getUserById(userId);
            user.setImageName(imageName);
            UserDto userDto = userService.updateUser(user, userId);
            ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("File Uploaded").success(true).status(HttpStatus.CREATED).build();
            log.info("Request Completed for fileservice layer to upload image with userId: {}", userId);
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
        }
    }
        //Serve User Image
        @GetMapping("/image/{userId}")
        public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
            UserDto user = userService.getUserById(userId);
            logger.info("User Image Name: {}",user.getImageName());
            InputStream resource = fileService.getResource(imageUploadPath,user.getImageName());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());}

    }
