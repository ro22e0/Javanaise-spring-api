package com.javanaise.ws.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ro22e0 on 29/04/2016.
 */

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> infos() {
        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("<p>- Infos des endpoints<br />\n" +
                        "Mapped [/], methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- Ajouter un nouveau feed<br />\n" +
                        "Mapped [/feeds/create], methods=[POST], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- Tous les feeds<br />\n" +
                        "Mapped [/feeds], methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- Infos d&rsquo;un feed<br />\n" +
                        "Mapped [/feeds/{feedId}], methods=[GET], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- Les items d&rsquo;un feed<br />\n" +
                        "Mapped [/feeds/{feedId}/items], methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- Mise &agrave; jour du user connect&eacute;<br />\n" +
                        "Mapped [/users], methods=[PUT]</p>\n" +
                        "\n" +
                        "<p>- Tous les users<br />\n" +
                        "Mapped [/users],methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- Cr&eacute;er un user<br />\n" +
                        "Mapped [/users/sign_up], methods=[POST], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- Connecter un user<br />\n" +
                        "Mapped [/users/sign_in], methods=[POST], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- D&eacute;connecter un user<br />\n" +
                        "Mapped [/users/sign_out], methods=[DELETE], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- Infos d&rsquo;un user<br />\n" +
                        "Mapped [/users/{userId}], methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- Supprimer un user<br />\n" +
                        "Mapped [/users/{userId}], methods=[DELETE]</p>\n" +
                        "\n" +
                        "<p>- Subscribed Feeds<br />\n" +
                        "Mapped [/feeds/subscribed], methods=[GET]</p>\n" +
                        "\n" +
                        "<p>- S&rsquo;abonner &agrave; un feed<br />\n" +
                        "Mapped [/feeds/subscribe], methods=[POST], produces=[application/json]</p>\n" +
                        "\n" +
                        "<p>- Se d&eacute;sabonner d&rsquo;un feed<br />\n" +
                        "Mapped [/feeds/unsubscribe], methods=[DELETE]</p>", HttpStatus.OK);
        return errorResponseEntity;
    }
}