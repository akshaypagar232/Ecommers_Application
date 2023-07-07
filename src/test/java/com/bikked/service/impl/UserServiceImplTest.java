package com.bikked.service.impl;

import com.bikked.dto.UserDto;
import com.bikked.entity.User;
import com.bikked.repository.UserRepository;
import com.bikked.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user;

    @Autowired
    ModelMapper mapper;

    @BeforeEach
    public void init() {

        String userId = "ndjvfbkdf";

        user = User.builder().name("ak")
                .email("sky@gmail.com")
                .about("testing")
                .gender("male")
                .imageName("abc.png")
                .password("aaa")
                .build();
    }

    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        mapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1);
        Assertions.assertNotNull(user1);

    }

    @Test
    public void updateUser() {

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1);
        Assertions.assertNotNull(user1);

    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void searchUser() {
    }
}