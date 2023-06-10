package com.rinku.electronic.store.ElectronicStore.ServiceImpl;

import com.rinku.electronic.store.ElectronicStore.Dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.Dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.Entity.User;
import com.rinku.electronic.store.ElectronicStore.Exception.ResourceNotFoundException;
import com.rinku.electronic.store.ElectronicStore.Helper.ApiConstants;
import com.rinku.electronic.store.ElectronicStore.Helper.Helper;
import com.rinku.electronic.store.ElectronicStore.Repository.UserRepo;
import com.rinku.electronic.store.ElectronicStore.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;

    public UserDto createUser(UserDto userDto) {
        logger.info(" Initiated Request for creating user");
        String UserID = UUID.randomUUID().toString();
        userDto.setUserId(UserID);
        //dto->entity
        User user = dtoToEntity(userDto);
        User saveUser = userRepo.save(user);
        //entity-> dto
        UserDto newDto = entityToDto(saveUser);
        logger.info(" Completed Request for creating user");
        return newDto;
    }

    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info(" Initiated Request  for updating user with userId :{}", userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, userId, userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepo.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info(" Completed Request  for updating user with userId :{}", userId);
        return updatedDto;
    }

    //Delete User
    public void deleteUser(String userId) {
        logger.info(" Initiated Request  for deleting user with userId :{}", userId);

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, userId, userId));

        logger.info(" Completed Request  for deleting user with userId :{}", userId);

        this.userRepo.delete(user);
    }

    //GetUserByID
    public UserDto getUserById(String userId) {
        logger.info(" Initiated Request  for getting user with userId :{}", userId);

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, userId, userId));
        logger.info(" Completed Request  for getting user with userId :{}", userId);
        return entityToDto(user);
    }

    //GetAll USer
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info(" Initiated Request for getting Users");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> all = userRepo.findAll(pageable);
        Page<User> page = all;
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        logger.info(" completed Request  for getting users ");
        return response;
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException(ApiConstants.EXCEPTION_MESSAGE, email, email));

        return entityToDto(user);
    }

    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepo.findByNameContaining(keyword);

        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto=UserDto.builder()
//                .id(savedUser.getId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
//                return userDto;

        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);

    }


}
