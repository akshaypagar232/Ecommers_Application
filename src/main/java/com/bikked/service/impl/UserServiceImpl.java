package com.bikked.service.impl;

import com.bikked.constant.AppConstant;
import com.bikked.dto.PageableResponse;
import com.bikked.dto.UserDto;
import com.bikked.entity.User;
import com.bikked.exceptions.ResourceNotFoundException;
import com.bikked.helper.Helper;
import com.bikked.repository.UserRepository;
import com.bikked.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        log.info("Initiated request for save the User details in database");


        String userId = UUID.randomUUID().toString();

        userDto.setUserId(userId);

        User user = mapper.map(userDto, User.class);

        User save = userRepository.save(user);

        UserDto map = mapper.map(save, UserDto.class);

        log.info("Completed request for save the User details in database");

        return map;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        log.info("Initiated request for update the User details in database with userId : {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.User, AppConstant.UserId, userId));

        User user1 = mapper.map(userDto, User.class);

        user.setName(user1.getName());
        user.setGender(user1.getGender());
        user.setPassword(user1.getPassword());
        user.setAbout(user1.getAbout());
        user.setImageName(user1.getImageName());

        User save = userRepository.save(user);

        UserDto user2 = mapper.map(save, UserDto.class);

        log.info("Completed request for update the User details in database with userId : {}", userId);

        return user2;
    }

    @Override
    public void deleteUser(String userId) {

        log.info("Initiated request for delete the User details in database with userId : {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.User, AppConstant.UserId, userId));

        userRepository.delete(user);

        log.info("Completed request for delete the User details in database with userId:{}", userId);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize , String sortBy, String sortDirection) {

        Sort sort = (sortDirection.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        log.info("Initiated request for get all User details in database");

        Page<User> page = userRepository.findAll(pageable);

        log.info("Completed request for get all User details in database");

        PageableResponse<UserDto> response = Helper.getPagableResponse(page, UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {

        log.info("Initiated request for get User By Id User details in database with userId:{}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.User, AppConstant.UserId, userId));

        UserDto userDto = mapper.map(user, UserDto.class);

        log.info("Completed request for get User By Id User details in database with userId:{}", userId);

        return userDto;
    }


    @Override
    public UserDto getUserByEmail(String email) {

        log.info("Initiated request for get User By Email User details in database with email:{}", email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.User, AppConstant.UserEmail, email));

        UserDto userDto = mapper.map(user, UserDto.class);

        log.info("Completed request for get User By Email User details in database with email:{}", email);

        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        log.info("Initiated request for get User by using search keyword in User details in database with keyword:{}", keyword);

        List<User> userList = userRepository.findByNameContaining(keyword);

        List<UserDto> userDtos = userList.stream().map((i) -> mapper.map(i, UserDto.class)).collect(Collectors.toList());

        log.info("Completed request for get User by using search keyword in User details in database with keyword:{}", keyword);

        return userDtos;
    }
}
