package com.onseotestapp.controller;

import com.onseotestapp.dao.StatusRepository;
import com.onseotestapp.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;


    @GetMapping("/status")
    public String statusesList(Model model){
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("statuses", statuses);
        return "status";
    }
}
