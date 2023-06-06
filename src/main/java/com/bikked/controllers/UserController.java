package com.bikked.controllers;

import com.bikked.constant.AppConstant;
import com.bikked.dto.UserDto;
import com.bikked.exceptions.ApiResponseMessage;
import com.bikked.exceptions.ResourceNotFoundException;
import com.bikked.service.UserService;
import com.bikked.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @param userDto
     * @return
     * @author Akshay
     * @apiNote save User details
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        log.info("Initiated request pass service for save the User details");

        UserDto user = userService.createUser(userDto);

        log.info("Completed request for save the User details");

        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @author Akshay
     * @apiNote update user details
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        log.info("Initiated request pass service for update the User details with userId : {}", userId);

        UserDto user = userService.updateUser(userDto, userId);

        log.info("Completed request for update the User details with userId : {}", userId);

        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return
     * @author Akshay
     * @apiNote delete user
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {

        log.info("Initiated request pass service for delete the User details with userId : {}", userId);

        userService.deleteUser(userId);

        ApiResponseMessage api = ApiResponseMessage
                .builder()
                .message(AppConstant.User_Delete)
                .status(true)
                .success(HttpStatus.OK)
                .build();

        log.info("Completed request for delete the User details with userId : {}", userId);

        return new ResponseEntity<ApiResponseMessage>(api, HttpStatus.OK);
    }


    /**
     * @return
     * @author Akshay
     * @apiNote get all user
     */
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUser() {

        log.info("Initiated request pass service for get all the User details");

        List<UserDto> allUser = userService.getAllUser();

        log.info("Completed request for get all the User details");

        return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.FOUND);
    }

    /**
     * @param userId
     * @return
     * @author Akshay
     * @apiNote get user by using userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

        log.info("Initiated request pass service for get User details with userId : {}", userId);

        UserDto userById = userService.getUserById(userId);

        log.info("Completed request for get User details with userId : {}", userId);

        return new ResponseEntity<UserDto>(userById, HttpStatus.FOUND);
    }


    /**
     * author Akshay
     *
     * @param userEmail
     * @return
     * @apiNote get user by using userEmail
     */
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String userEmail) {

        log.info("Initiated request pass service for get User details with userEmail : {}", userEmail);

        UserDto userByEmail = userService.getUserById(userEmail);

        log.info("Completed request for get User details with userEmail : {}", userEmail);

        return new ResponseEntity<UserDto>(userByEmail, HttpStatus.FOUND);
    }

    /**
     * @param keyword
     * @return
     * @author Akshay
     * @apiNote get user details by using keyword
     */
    @GetMapping("/user/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Initiated request pass service for get User details with keyword : {}", keyword);

        List<UserDto> userDtos = userService.searchUser(keyword);

        log.info("Completed request for get User details with keyword : {}", keyword);

        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.FOUND);
    }


}