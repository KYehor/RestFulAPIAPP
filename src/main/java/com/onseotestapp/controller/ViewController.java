package com.onseotestapp.controller;

import com.onseotestapp.dao.ProductRepository;
import com.onseotestapp.dao.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private StatusRepository statusRepository;


    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping(value = {"/confirm"})
    public String confirmForm(){
        return "confirm";
    }
}