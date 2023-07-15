package com.bikked.controllers;

import com.bikked.dto.ImageResponse;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.ProductDto;
import com.bikked.dto.UserDto;
import com.bikked.entity.User;
import com.bikked.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    User user;

    @BeforeEach
    void init() {

        user = User.builder()
                .name("ak")
                .email("sky@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();
    }

    @Test
    void createUserTest() throws Exception {

        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    private String convertObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void updateUser() throws Exception {

        String userId = "fdkj";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void deleteUserTest() throws Exception {
    }

    @Test
    void getAllUserTest() throws Exception {

        User user1 = User.builder()
                .name("ak1")
                .email("sky1@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();
        User user2 = User.builder()
                .name("ak2")
                .email("sky2@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();
        User user3 = User.builder()
                .name("ak3")
                .email("sky3@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();

        UserDto userDto1 = mapper.map(user1, UserDto.class);
        UserDto userDto2 = mapper.map(user1, UserDto.class);
        UserDto userDto3 = mapper.map(user1, UserDto.class);

        PageableResponse<UserDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(100);
        pageableResponse.setPageSize(3);
        pageableResponse.setPageNumber(0);
        pageableResponse.setContent(Arrays.asList(userDto1,userDto2,userDto3));
        pageableResponse.setTotalPage(10);

        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getUserByIdTest() throws Exception {

        String userId = "jkdf";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/id/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void getUserByEmailTest() throws Exception {

        String userEmail = "sky2@gmail.com";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/email/"+userEmail)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void searchUserTest() throws Exception {

        String keyword = "ak";
        User user1 = User.builder()
                .name("ak1")
                .email("sky1@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();
        User user2 = User.builder()
                .name("ak2")
                .email("sky2@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();
        User user3 = User.builder()
                .name("ak3")
                .email("sky3@gmail.com")
                .password("A2ab@68")
                .about(" add new user")
                .imageName("abc.jpg")
                .gender("male")
                .build();

        UserDto userDto1 = mapper.map(user1, UserDto.class);
        UserDto userDto2 = mapper.map(user1, UserDto.class);
        UserDto userDto3 = mapper.map(user1, UserDto.class);

        List<UserDto> dtoList = Arrays.asList(userDto1, userDto2, userDto3);

        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(dtoList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/"+keyword)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void uploadUserImageTest() {
    }

    @Test
    void serveUserImageTest() {
    }
}