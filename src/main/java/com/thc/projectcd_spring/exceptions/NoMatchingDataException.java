package com.thc.projectcd_spring.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
@NoArgsConstructor
public class NoMatchingDataException extends RuntimeException {
    public NoMatchingDataException(String message) {
        super(message);
    }
}
