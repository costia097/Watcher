package net.watcher.domain.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * WatcherControllerAdvice controller functionality
 * now not working !!! need to fix
 * @author Kostia
 *
 */
@RestControllerAdvice
public class WatcherControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleBadRequest(BadCredentialsException e) {
        return "Wrong password";
    }

}
