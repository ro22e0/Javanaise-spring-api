package com.javanaise.ws.controllers;

import com.javanaise.ws.models.Rss;
import com.javanaise.ws.repositories.RssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by corgi on 28/04/16.
 */

@Controller
public class RssController {

    @Autowired
    private RssRepository rssRepository;

    @RequestMapping(value = "webapi/rss/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody Rss rss){
        return "ok";
    }
}
