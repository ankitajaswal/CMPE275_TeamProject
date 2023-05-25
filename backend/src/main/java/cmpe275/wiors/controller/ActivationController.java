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

    private static final String returnMsg = "<html><body><p>Account verified :^)</p><br><p>Click <a href=\"http://107.175.28.141/login\">here</a> to login</p></body></html>";

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployerService employerService;

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> activateEmail(@PathVariable String id) {
		Activation activation = new Activation();
		String emailAddress = activation.decodeEmail(id);

		if (emailAddress != null) {
			Employee employee = employeeService.getEmployeeByEmail(emailAddress);
			if (employee != null) {
				employee.setIsVerified(1);
				employeeService.updateEmployee(employee);
				return new ResponseEntity<>(returnMsg, HttpStatus.OK);
            } else {
                Employer employer = employerService.getEmployerByEmail(emailAddress);
                if (employer != null) {
                    employer.setIsVerified(1);
                    employerService.updateEmployer(employer);
                    return new ResponseEntity<>(returnMsg, HttpStatus.OK);
                }
            }
		}
        return new ResponseEntity<>("<html><body>Account Not Found :(</body></html>", HttpStatus.NOT_FOUND);
	}
}
