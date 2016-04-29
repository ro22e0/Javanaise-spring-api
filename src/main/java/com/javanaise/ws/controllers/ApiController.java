package com.javanaise.ws.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ro22e0 on 29/04/2016.
 */

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> infos() {
        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("Mapped \"{[/users/sign_up],methods=[POST],produces=[application/json]}\"\n" +
                        "Mapped \"{[/users/sign_in],methods=[POST],produces=[application/json]}\"\n" +
                        "Mapped \"{[/users/{userId}],methods=[GET],produces=[application/json]}\"\n" +
                        "Mapped \"{[/users],methods=[GET],produces=[application/json]}\"\n" +
                        "Mapped \"{[/users],methods=[PUT],produces=[application/json]}\"\n" +
                        "Mapped \"{[/webapi/rss/create],methods=[POST]}\"\n" +
                        "Mapped \"{[/error],produces=[text/html]}\"\n" +
                        "Mapped \"{[/error]}\"\n", HttpStatus.BAD_REQUEST);
        return errorResponseEntity;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity<?> error() {
        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("No route found", HttpStatus.BAD_REQUEST);
        return errorResponseEntity;
    }
}