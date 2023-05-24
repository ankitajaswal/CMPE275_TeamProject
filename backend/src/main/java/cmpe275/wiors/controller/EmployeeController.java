package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employee;
import cmpe275.wiors.entity.EmployeeDto;
import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.mail.MailSender;
import cmpe275.wiors.entity.Address;
import cmpe275.wiors.service.EmployeeService;
import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.AttendanceRequirementService;
import cmpe275.wiors.service.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The main rest controller for employees.
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private AttendanceRequirementService attendanceRequirementService;

    /**
     * Endpoint handler for creating employees.
     *
     * @param employerId path parameter, employer this employee belongs to
     * @param name required query parameter, name of employee
     * @param email required query parameter, email of employee
     * @param password required query parameter, password of employee
     * @param title optional query parameter, title of employee
     * @param street optional query parameter, street of employee's address
     * @param city optional query parameter, city of employee's address
     * @param state optional query parameter, state of employee's address
     * @param zip optional query parameter, zip of employee's address
     * @param managerId optional query parameter, ID of manager of this employee
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employee on success, and error message on failure
     */
    @Transactional
    @RequestMapping(
        value = "/employer/{employerId}/employee",
        method = RequestMethod.POST,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> createEmployee(
        @PathVariable String employerId,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "is_google", required = false) boolean is_google,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "zip", required = false) String zip,
        @RequestParam(value = "managerId", required = false) Long managerId,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        if (employerId == null || employer == null) {
            return new ResponseEntity<>("{\"msg\":\"EmployerId is invalid\"}", HttpStatus.NOT_FOUND);
        }
        if (!validStr(name)) {
            return new ResponseEntity<>("{\"msg\":\"Missing or invalid name\"}", HttpStatus.BAD_REQUEST);
        }
        if (!validStr(email)) {
            return new ResponseEntity<>("{\"msg\":\"Missing or invalid email\"}", HttpStatus.BAD_REQUEST);
        }
        if (!validStr(password)) {
            return new ResponseEntity<>("{\"msg\":\"Missing or invalid password\"}", HttpStatus.BAD_REQUEST);
        }
        if (employerService.getEmployerByEmail(email) != null || employeeService.getEmployeeByEmail(email) != null) {
            return new ResponseEntity<>("{\"msg\":\"Duplicate email\"}", HttpStatus.BAD_REQUEST);
        }

        Employee employee = new Employee();
        EmployeeDto manager = null;
        if (managerId != null) {
            manager = employeeService.getEmployeeDto(managerId);
            if (manager == null) {
                return new ResponseEntity<>("{\"msg\":\"Invalid managerId\"}", HttpStatus.NOT_FOUND);
            }
            if (!manager.getEmployerId().equals(employerId)) {
                return new ResponseEntity<>("{\"msg\":\"Manager must belong to employee's employer\"}", HttpStatus.BAD_REQUEST);
            }
            employee.setManagerId(managerId);
        }
        employee.setEmployerId(employerId);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setTitle(title);
        employee.setIsGoogle(is_google);
        employee.setAddress(new Address(street, city, state, zip));
        employee.setMop(attendanceRequirementService.calculateMop(employerId, null));

        Employee newEmployee = employeeService.createEmployee(employee);
        newEmployee.setEmployer(employer.toDto());
        newEmployee.setReports(employeeService.getReports(newEmployee.getId()));
        newEmployee.setManager(manager);
        newEmployee.setCollaborators(collaborationService.getCollaborators(newEmployee.getId()));

        
    	MailSender mailSender = new MailSender();
    	mailSender.sendActivationMail(newEmployee.getEmail());     	
        
        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(newEmployee);
    }

    /**
     * Endpoint handler for getting employees.
     *
     * @param employerId path parameter, employer this employee belongs to
     * @param id path parameter, id of employee
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employee on success, and error message on failure
     */
    @Transactional
    @RequestMapping(
        value = "/employer/{employerId}/employee/{id}",
        method = RequestMethod.GET,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> getEmployee(
        @PathVariable String employerId,
        @PathVariable Long id,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        if (employerId == null || employer == null) {
            return new ResponseEntity<>("Invalid employerId", HttpStatus.NOT_FOUND);
        }

        Employee employee = employeeService.getEmployee(id);
        if (id == null || employee == null) {
            return new ResponseEntity<>("Invalid employeeId", HttpStatus.NOT_FOUND);
        }
        EmployeeDto manager = null;
        if (employee.getManagerId() != null) {
            employee.setManager(employeeService.getEmployeeDto(employee.getManagerId()));
        }
        employee.setEmployer(employer.toDto());
        employee.setReports(employeeService.getReports(id));
        employee.setCollaborators(collaborationService.getCollaborators(id));

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(employee);
    }

    /**
     * Endpoint handler for updating employees.
     *
     * @param employerId path parameter, employer this employee belongs to
     * @param id path parameter, employee id
     * @param name required query parameter, name of employee
     * @param title optional query parameter, title of employee
     * @param street optional query parameter, street of employee's address
     * @param city optional query parameter, city of employee's address
     * @param state optional query parameter, state of employee's address
     * @param zip optional query parameter, zip of employee's address
     * @param managerId optional query parameter, ID of manager of this employee
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employee on success, and error message on failure
     */
    @Transactional
    @RequestMapping(
        value = "/employer/{employerId}/employee/{id}",
        method = RequestMethod.PUT,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> updateEmployee(
        @PathVariable String employerId,
        @PathVariable Long id,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "zip", required = false) String zip,
        @RequestParam(value = "managerId", required = false) Long managerId,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        if (employerId == null || employer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Employee employee = employeeService.getEmployee(id);
        if (id == null || employee == null) {
            return new ResponseEntity<>("Invalid employeeId", HttpStatus.NOT_FOUND);
        }
        if (!validStr(name)) {
            return new ResponseEntity<>("Missing or invalid name", HttpStatus.BAD_REQUEST);
        }
        if (!employerId.equals(employee.getEmployerId())) {
            return new ResponseEntity<>("Cannot change employerId", HttpStatus.BAD_REQUEST);
        }

        EmployeeDto manager = null;
        if (managerId != null) {
            manager = employeeService.getEmployeeDto(managerId);
            if (manager == null) {
                return new ResponseEntity<>("Invalid managerId", HttpStatus.NOT_FOUND);
            }
            if (!manager.getEmployerId().equals(employerId)) {
                return new ResponseEntity<>("Manager and employee must have same employer", HttpStatus.BAD_REQUEST);
            }
            employee.setManagerId(managerId);
        }
        employee.setName(name);
        if (validStr(email)) employee.setEmail(email);
        if (validStr(title)) employee.setTitle(title);
        if (validStr(street)) employee.getAddress().setStreet(street);
        if (validStr(city)) employee.getAddress().setCity(city);
        if (validStr(state)) employee.getAddress().setState(state);
        if (validStr(zip)) employee.getAddress().setZip(zip);

        Employee newEmployee = employeeService.updateEmployee(employee);
        newEmployee.setEmployer(employer.toDto());
        newEmployee.setReports(employeeService.getReports(newEmployee.getId()));
        newEmployee.setManager(manager);
        newEmployee.setCollaborators(collaborationService.getCollaborators(id));

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(employee);
    }

    /**
     * Endpoint handler for deleting employees.
     *
     * @param employerId path parameter, employer this employee belongs to
     * @param id path parameter, employee id
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code and error message on failure
     */
    @Transactional
    @RequestMapping(
        value = "/employer/{employerId}/employee/{id}",
        method = RequestMethod.DELETE
    )
	public ResponseEntity<?> deleteEmployee(
        @PathVariable String employerId,
        @PathVariable Long id
    ) {
        Employer employer = employerService.getEmployerById(employerId);
        if (employerId == null || employer == null) {
            return new ResponseEntity<>("Invalid employerId", HttpStatus.NOT_FOUND);
        }
        Employee employee = employeeService.getEmployee(id);
        if (id == null || employee == null) {
            return new ResponseEntity<>("Invalid employeeId", HttpStatus.NOT_FOUND);
        }

        if (employeeService.hasReports(id)) {
            return new ResponseEntity<>("Employee has reports", HttpStatus.BAD_REQUEST);
        }

        collaborationService.deleteAllCollaborationsWithEmployee(id);
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
