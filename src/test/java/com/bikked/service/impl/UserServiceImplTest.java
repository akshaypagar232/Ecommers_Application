package com.bikked.service.impl;

import com.bikked.dto.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
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

        String userId = "abcd";

        user = User.builder().name("ak")
                .email("sky@gmail.com")
                .about("testing")
                .gender("male")
                .imageName("abc.jpg")
                .password("aaa")
                .build();

    }

    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(userDto);
        System.out.println(user1);
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1.getName(), "ak");

    }


    @Test
    public void updateUserTest() {

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(userDto);
        System.out.println(user1);
        Assertions.assertNotNull(user1);

    }

    @Test
    void deleteUserTest() {

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        userService.deleteUser(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);

    }

    @Test
    void getAllUserTest() {

        User user1 = User.builder().name("ak")
                .email("sky@gmail.com")
                .about("testing")
                .gender("male")
                .imageName("abc.jpg")
                .password("aaa")
                .build();

        User user2 = User.builder().name("akk")
                .email("skyy@gmail.com")
                .about("testing2")
                .gender("male")
                .imageName("abc.jpg")
                .password("bb")
                .build();

        List<User> userList = Arrays.asList(user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");
        Assertions.assertEquals(2,allUser.getContent().size());
        System.out.println(allUser);

    }

    @Test
    void getUserByIdTest() {

        Mockito.when(userRepository.findById("abcd")).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUserById("abcd");
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user.getName(),"ak","Not found name !!!");
        System.out.println(user1.getName());

    }

    @Test
    void getUserByEmailTest() {

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUserByEmail(user.getEmail());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1.getName(),"ak","Not found name ");
        System.out.println(user1.getName());

    }


    @Test
    void searchUserTest() {

        String keyword = "patil";

        User user1 = User.builder().name("akshay patil")
                .email("sky@gmail.com")
                .about("testing")
                .gender("male")
                .imageName("abc.jpg")
                .password("aaa")
                .build();

        User user2 = User.builder().name("pankaj patil")
                .email("skyy@gmail.com")
                .about("testing2")
                .gender("male")
                .imageName("abc.jpg")
                .password("bb")
                .build();

        List<User> userList = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(userList);
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(2,userDtos.size());
    }
}