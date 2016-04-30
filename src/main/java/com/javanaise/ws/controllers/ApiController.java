package com.javanaise.ws.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ro22e0 on 29/04/2016.
 */

@RestController
@RequestMapping(value = "/")
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> infos() {
        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("Mapped \"[/users/sign_up], methods=[POST], produces=[application/json, body_param=[email, password]\"\n" +
                        "Mapped \"[/users/sign_in], methods=[POST], produces=[application/json], body_param=[email, password]\"\n" +
                        "Mapped \"[/users/{userId}], methods=[GET], produces=[application/json]\"\n" +
                        "Mapped \"[/users],methods=[GET], produces=[application/json]\"\n" +
                        "Mapped \"[/users],methods=[PUT], produces=[application/json], body_param=[firstname, lastname, email, password]\"\n" +
                        "Mapped \"[/feeds/create], methods=[POST], produces=[application/json], body_param=[url]\"" +
                        "Mapped \"{[/feeds/{feedId}],methods=[GET],produces=[application/json]}\"\n" +
                        "Mapped \"{[/feeds/{feedId}/items],methods=[GET],produces=[application/json]}\"", HttpStatus.OK);
        return errorResponseEntity;
    }
}