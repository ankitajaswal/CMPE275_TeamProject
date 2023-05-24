package cmpe275.wiors.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.wiors.util.*;

/**
 * The main rest controller for activation of an account.
 */
@RestController
public class ActivationController {

    @GetMapping("/activate")
    public String activate(@RequestParam("id") String id) {
        // Perform activation logic using the provided id
        // Replace this with your custom activation logic
    	
    	Activation activation = new Activation();
    	String emailAddress = activation.encodeEmail(id);
    	
        if (emailAddress != null) {
        	/* TODO Update the DB with this email address */
        	
            return "Activation successful";
        } else {
            return "Activation failed";
        }
    }
}

