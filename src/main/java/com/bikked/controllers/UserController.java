package com.bikked.controllers;

import com.bikked.constant.AppConstant;
import com.bikked.dto.ImageResponse;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.UserDto;
import com.bikked.exceptions.ApiResponseMessage;
import com.bikked.service.FileService;
import com.bikked.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param userDto
     * @return
     * @author Akshay
     * @apiNote save User details
     */
    @PostMapping
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
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable String userId

    ) {

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
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {

        log.info("Initiated request pass service for delete the User details with userId : {}", userId);

        userService.deleteUser(userId);

        ApiResponseMessage api = ApiResponseMessage
                .builder()
                .message(AppConstant.USER_DELETE)
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
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection

    ) {

        log.info("Initiated request pass service for get all the User details");

        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDirection);

        log.info("Completed request for get all the User details");

        return new ResponseEntity<PageableResponse<UserDto>>(allUser, HttpStatus.FOUND);
    }

    /**
     * @param userId
     * @return
     * @author Akshay
     * @apiNote get user by using userId
     */
    @GetMapping("/{userId}")
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
    @GetMapping("/{userEmail}")
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
    @GetMapping("/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Initiated request pass service for get User details with keyword : {}", keyword);

        List<UserDto> userDtos = userService.searchUser(keyword);

        log.info("Completed request for get User details with keyword : {}", keyword);

        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.FOUND);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId

    ) throws IOException {

        log.info("Initiated request pass service for upload User image details with userId : {}", userId);

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);

        user.setImageName(imageName);

        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .success(HttpStatus.CREATED)
                .status(true)
                .build();

        log.info("Completed request for upload User image details with userId : {}", userId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }


    @GetMapping("/image/{userId}")
    public void serveUserImage(
            @PathVariable String userId,
            HttpServletResponse response

    ) throws IOException {

        log.info("Initiated request pass service for serve User image details with userId : {}", userId);

        UserDto user = userService.getUserById(userId);

        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Completed request for serve user image details with userId : {}", userId);

    }

}