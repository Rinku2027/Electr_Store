package com.rinku.electronic.store.ElectronicStore.controller;

import com.rinku.electronic.store.ElectronicStore.dtos.*;
import com.rinku.electronic.store.ElectronicStore.entity.Category;
import com.rinku.electronic.store.ElectronicStore.service.CategoryService;
import com.rinku.electronic.store.ElectronicStore.service.FileService;
import com.rinku.electronic.store.ElectronicStore.service.UserService;
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
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);
   @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @author Rinku Patil
     * @param cate
     * @return
     * @ApiNote This API is used to create user
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto cate) {
        logger.info(" Initiated Request for creating category");
        CategoryDto dto = this.categoryService.create(cate);
        logger.info(" completed Request for creating category");
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Request  for service layer to get All user ");
        return new ResponseEntity<>(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto cate,
                                                      @PathVariable String categoryId) {
        logger.info(" Initiated Request for updating category with categoryId :{}", categoryId);
        CategoryDto dto = this.categoryService.updatecategory(cate, categoryId);
        logger.info(" completed Request for updating category with categoryId :{}", categoryId);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);

    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getcategory(@PathVariable String  categoryId) {
        logger.info(" Initiated Request for getting category with categoryId :{}", categoryId);
        CategoryDto dto = this.categoryService.getcategory(categoryId);
        logger.info(" completed Request for getting category with categoryId :{}", categoryId);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
    }

    @DeleteMapping("categories/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deletecategory(@PathVariable String  categoryId) {
        logger.info(" Initiated Request for deleting category with categoryId :{}", categoryId);
        this.categoryService.deletecategory(categoryId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted Sucessfully!!  ")
                .success(true).
                status(HttpStatus.OK)
                .build();
        logger.info(" completing Request for deleting category with categoryId :{}", categoryId);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("catImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        {
            logger.info("Request Starting for fileservice layer to upload image with categoryId {}", categoryId);
            String imageName = fileService.uploadFile(image, imageUploadPath);
            CategoryDto category = categoryService.getcategory(categoryId);
            category.setCoverImage(imageName);
            CategoryDto categoryDto = categoryService.updatecategory(category, categoryId);
            ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("File Uploaded").success(true).status(HttpStatus.CREATED).build();
            logger.info("Request Completed for fileservice layer to upload image with categoryId: {}", categoryId);
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
        }
    }
    //Serve User Image
    @GetMapping("/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto category = categoryService.getcategory(categoryId);
        logger.info("User Image Name: {}",category.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath,category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }



}


