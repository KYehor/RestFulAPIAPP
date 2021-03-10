package com.onseotestapp.controllers;

import com.onseotestapp.repositories.StatusRepository;
import com.onseotestapp.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Class status controller.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Controller
public class StatusController {

    /** Field statusRepository an instance that allows us to use certain database operations. */
    private final StatusRepository statusRepository;

    /**
     * Constructor - creating a new object of our controller that contain Status repository instance.
     * @param statusRepository - instance of StatusRepository
     * @see StatusRepository
     */
    @Autowired
    public StatusController(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    /**
     * We use this method to find statuses that contain our database table.And show on page.
     * We didn't create a service because of 1 method inside controller.
     * @param model - model of page settings
     * @return  url of status.html page
     * @see String
     */
    @GetMapping("/status")
    public String statusesList(Model model){
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("statuses", statuses);
        return "status";
    }

}
