package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.NewUserInfo;
import cmpe275.wiors.entity.User;
import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.EmployerService;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main rest controller for user bulk creation and login.
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
            if (!employee.getPassword().equals(password) && employee.getIsVerified()<=0) {
                return new ResponseEntity<>("{\"msg\":\"Invalid user or Not Verified\"}", HttpStatus.FORBIDDEN);
            }
            user.setEmployerId(employee.getEmployerId());
            user.setEmployeeId(employee.getId());
        }

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(user);
    }

    @Transactional
    @RequestMapping(
        value = "/user/bulk",
        method = RequestMethod.POST
    )
	public ResponseEntity<?> bulkAcctCreation(
        @Valid @RequestBody List<NewUserInfo> newUsers,
        @RequestParam(value = "employerId", required = true) String employerId
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        if (employer == null) {
            throw null;
        }
        for (NewUserInfo n: newUsers) {
            Employee e = new Employee();

            if (!validStr(n.getEmail())) {
                throw null;
            }
            if (!validStr(n.getPassword().strip().substring(1,n.getPassword().strip().length() - 1))) {
                throw null;
            }
            e.setName(n.getName().strip());
            e.setEmployerId(employerId);
            e.setEmployer(employer.toDto());
            e.setEmail(n.getEmail().strip());
            e.setPassword(n.getPassword().strip().substring(1,n.getPassword().strip().length() - 1));
            employeeService.createEmployee(e);
            if (validStr(n.getManager())) {
                Employee manager = employeeService.getEmployeeByEmail(n.getManager().strip());
                if (manager == null) {
                    throw null;
                }
                if (!manager.getEmployerId().equals(employer.getId())) {
                    throw null;
                }
                e.setManager(manager.toDto());
                employeeService.updateEmployee(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
