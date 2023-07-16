package com.rinku.electronic.store.ElectronicStore.services;

import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.repository.UserRepo;
import com.rinku.electronic.store.ElectronicStore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @MockBean
    private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .about("testing for userCreate method")
                .name("Rinku")
                .email("rinkupatil.20@gmail.com")
                .gender("female")
                .password("rinku@123")
                .imageName("xyz.png").build();
    }

    @Test
    void saveUser() {

        //stubbing
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        //actual method calling
        UserDto userDto = userService.createUser(this.mapper.map(user, UserDto.class));
        //matching using assertion
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Rinku", userDto.getName(), "UserName not matched ");
    }
    @Test
    void getUserById() {
        String userId = "xyz";
        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        //actual method calling
        UserDto userdto = userService.getUserById(userId);
        //matching using assertion
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getGender(), userdto.getGender(), "Gender not matched ");
    }

    @Test
    void getAllUsers() {
        User user1 = User.builder()
                .about("testing for getAllUser method")
                .name("pakhi")
                .email("pakhi@gmail.com")
                .gender("female")
                .password("pakhi@123")
                .imageName("pakhi.png").build();

        User user2 = User.builder()
                .about("testing for getAllUser method")
                .name("Satish")
                .email("satish@gmail.com")
                .gender("male")
                .password("shptl@123")
                .imageName("shatish.png").build();

        List<User> list = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(list);

        //stubbing
        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        //actual method calling
        PageableResponse<UserDto> allUsers = userService.getAllUser(1, 2, "state", "desc");
        //matching using assertion
        Assertions.assertEquals(3, allUsers.getContent().size(), "Size not validate");

    }

    @Test
    void updateUser() {
        String userId = "abc";
        UserDto userDto = UserDto.builder()
                .about(" updated testing for updateProduct method")
                .name("rinku")
                .email("rinkupatil.20@gmail.com")
                .gender("female")
                .password("shweta@123")
                .imageName("shweta.png").build();
        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        //actual method calling
        UserDto userDto1 = userService.updateUser(userDto, userId);
        //matching using assertion
        Assertions.assertEquals("shweta", userDto1.getName(), "username not validate");
        Assertions.assertNotNull(userDto1);

    }

    @Test
    void deleteUser() {

        String userId = "abc";
        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        //actual method calling
        userService.deleteUser(userId);
        //verifying result
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    void getUserByEmail() {

        String email = "rinkupatil.20@gmail.com";
        //stubbing
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        //actual method calling
        UserDto userdto = userService.getUserByEmail(email);
        //matching using assertion
        Assertions.assertEquals(user.getEmail(), userdto.getEmail(), "Email not matched ");
    }

    @Test
    void searchUser() {

        User user1 = User.builder()
                .about("testing for search user method")
                .name("Shweta")
                .email("Shweta@gmail.com")
                .gender("female")
                .password("shweta@123")
                .imageName("pankaj.png").build();

        User user2 = User.builder()
                .about("testing for search user method")
                .name("rachana")
                .email("rachana@gmail.com")
                .gender("female")
                .password("rachana@123")
                .imageName("rachna.png").build();
        String keyword = "rachana";
        //stubbing
        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(Arrays.asList(user, user1, user2));
        //actual method calling
        List<UserDto> userDto = userService.searchUser(keyword);
        //matching using assertion
        Assertions.assertEquals(3, userDto.size(), "size not matched");
    }
}
