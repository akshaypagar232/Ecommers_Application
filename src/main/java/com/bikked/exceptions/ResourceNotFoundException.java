package com.bikked.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResourceNotFoundException extends RuntimeException {

    String resourcename;    
 
    String fieldname;

    String fieldvalue;

    public ResourceNotFoundException() {
        super("Resource Not Found !!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourcename, String fieldname, String fieldvalue) {
        super(String.format("%s can not found with %s : %s", resourcename, fieldname, fieldvalue));
        this.resourcename = resourcename;
        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
    }
}
