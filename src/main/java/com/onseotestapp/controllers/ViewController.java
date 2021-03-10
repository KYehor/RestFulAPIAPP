package com.onseotestapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class view controller.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Controller
public class ViewController {

    /**
     * We use this method to open our index.html and show it for users.
     *
     * @return  url of index.html page(main page)
     * @see String
     */
    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

}