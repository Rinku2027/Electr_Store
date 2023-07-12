package com.rinku.electronic.store.ElectronicStore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.rinku.electronic.store.ElectronicStore.dtos.UserDto;
import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    @MockBean
    private UserService userServiceI;

    @Autowired
    private ModelMapper mapper ;

    @Autowired
    private MockMvc mockMvc;

    User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .about("testing for controller")
                .name("Rinku")
                .email("rinkupatil.20@gmail.com")
                .gender("female")
                .password("rinku")
                .imageName("xyz.png")
                .build();

    }

    @Test
    void saveUserTest() throws Exception {
        UserDto userdto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(userdto);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    //extra method for conversion
    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Test
    void getUserByIdTest() throws Exception {
        String userId ="avc";
        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserById(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    void getAllUsersTest() throws Exception {
        UserDto user1 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("pakhi")
                .email("pakhi@gmail.com")
                .gender("female")
                .password("pakhi@123")
                .imageName("xyz.png").build();

        UserDto user2 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("satish")
                .email("satish@gmail.com")
                .gender("male")
                .password("shptl@123")
                .imageName("abc.png").build();

        UserDto user3 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("rachana")
                .email("rachana@gmail.com")
                .gender("female")
                .password("rachana@123")
                .imageName("abc.png").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(user1,user2,user3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(userServiceI.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void updateUserTest() throws Exception {

        String userId ="123";

        UserDto userdto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userdto);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }


    @Test
    void deleteUserTest() {
    }

    @Test
    void getUserByEmailTest() throws Exception {
        String emailId ="rinkupatil.20@gmail.com";
        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserByEmail(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/email/"+emailId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }





    @Test
    void searchUserTest() throws Exception {

        String keyword ="image";
        UserDto user1 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("satish")
                .email("shptl@gmail.com")
                .gender("male")
                .password("sh@123")
                .imageName("xyz.png").build();

        UserDto user2 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("rachana")
                .email("rachana@gmail.com")
                .gender("female")
                .password("rachana@123")
                .imageName("abc.png").build();

        Mockito.when(userServiceI.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user1,user2));

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/search/"+keyword)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());


    }
}
