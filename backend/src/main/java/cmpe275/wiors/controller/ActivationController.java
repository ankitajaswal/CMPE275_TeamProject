package cmpe275.wiors.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import cmpe275.wiors.util.*;
import cmpe275.wiors.entity.*;
import cmpe275.wiors.service.*;

/**
 * The main rest controller for activation of an account.
 */
@RestController
public class ActivationController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam("id") String id) {
        // Perform activation logic using the provided id
        // Replace this with your custom activation logic
    	
    	Activation activation = new Activation();
    	String emailAddress = activation.encodeEmail(id);
    	
        if (emailAddress != null) {
            Employee e = employeeService.getEmployeeByEmail(emailAddress);
            e.setIsVerified(true);
            employeeService.updateEmployee(e);

            return new ResponseEntity<>("<html><body>Account verified :^)</body></html>", HttpStatus.OK);
        } else {
            throw null;
        }
    }
}

