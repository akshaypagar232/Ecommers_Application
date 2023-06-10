package com.bikked.helper;

import com.bikked.dto.PageableResponse;
import com.bikked.dto.UserDto;
import com.bikked.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPagableResponse(Page<U> page, Class<V> type){

        List<U> all = page.getContent();

        List<V> userDtos = all.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();

        response.setContent(userDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
