package com.rinku.electronic.store.ElectronicStore.services;

import com.rinku.electronic.store.ElectronicStore.entity.User;
import com.rinku.electronic.store.ElectronicStore.repository.UserRepo;
import com.rinku.electronic.store.ElectronicStore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import javax.management.relation.Role;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepo userRepo;
    @Autowired
    @InjectMocks
    private UserService userService;

    User user;
    Role role;
    @BeforeEach
    public void init()
    {
                User.builder().name("Rinku").
                email("rinku@gmail.com").about("This is testing create method")
                .gender("Male").imageName("abc.png")
                .password("lcwd").roles(Set.of(role)).build();


    }


    //create user
    public void createUserTest()
    {
        Mockito.when(userRepo.save(Mockito.any())).thenReturn();

    }



}
