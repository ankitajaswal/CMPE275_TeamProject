package cmpe275.wiors.controller;

import org.springframework.web.bind.annotation.*;
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

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> activateEmail(@PathVariable String id) {
		Activation activation = new Activation();
		String emailAddress = activation.decodeEmail(id);
        System.out.println(emailAddress);

		if (emailAddress != null) {
			Employee employee = employeeService.getEmployeeByEmail(emailAddress);
			if (employee != null) {
				System.err.println("Found User");
				employee.setIsVerified(1);
				employeeService.updateEmployee(employee);
				return new ResponseEntity<>("<html><body>Account verified :^)</body></html>", HttpStatus.OK);				
			} else {
				System.err.println("User Not Found");
				return new ResponseEntity<>("<html><body>Account Not Found :^)</body></html>", HttpStatus.NOT_FOUND);								
			}
		} else {
			return new ResponseEntity<>("<html><body>Account Not Found :^)</body></html>", HttpStatus.NOT_FOUND);								
		}
	}
}
