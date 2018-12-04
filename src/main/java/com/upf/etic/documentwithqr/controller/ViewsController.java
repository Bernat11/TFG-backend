package com.upf.etic.documentwithqr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    Logger logger = LoggerFactory.getLogger(ViewsController.class);

    @GetMapping("home")
    public String getView(){
        logger.info("Rest call received to /home");
        return "home";
    }

}
