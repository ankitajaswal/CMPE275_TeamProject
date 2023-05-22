package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.User;
import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main rest controller for employees.
 */
@RestController
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;

    @Transactional
    @RequestMapping(
        value = "/login",
        method = RequestMethod.POST,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> login(
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "isEmployer", required = false, defaultValue = "false") boolean isEmployer,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        User user = new User();
        user.setIsEmployer(isEmployer);
        user.setEmail(email);
        if (isEmployer) {
            Employer employer = employerService.getEmployerByEmail(email);
            if (employer == null) {
                return new ResponseEntity<>("{\"msg\":\"email is invalid\"}", HttpStatus.NOT_FOUND);
            }
            if (!employer.getPassword().equals(password)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            user.setEmployerId(employer.getId());
        } else {
            Employee employee = employeeService.getEmployeeByEmail(email);
            if (employee == null) {
                return new ResponseEntity<>("{\"msg\":\"email is invalid\"}", HttpStatus.NOT_FOUND);
            }
            if (!employee.getPassword().equals(password)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            user.setEmployerId(employee.getEmployerId());
            user.setEmployeeId(employee.getId());
        }

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(user);
    }
}
