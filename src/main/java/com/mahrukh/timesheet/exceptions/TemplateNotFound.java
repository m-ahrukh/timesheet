package com.mahrukh.timesheet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TemplateNotFound extends RuntimeException{
    public TemplateNotFound(String message) {
        super(message);
    }
}
