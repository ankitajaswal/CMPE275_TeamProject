package cmpe275.wiors.controller;

import cmpe275.wiors.entity.Employer;
import cmpe275.wiors.entity.Address;
import cmpe275.wiors.service.EmployerService;
import cmpe275.wiors.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The main rest controller for employers
 */
@RestController
public class EmployerController {

    @Autowired
    private EmployerService service;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint handler for creating employers.
     *
     * @param id optional query parameter, id of employer. If not provided, creates one automatically
     * @param name required query parameter, name of employer
     * @param email required query parameter, email of employee
     * @param password required query parameter, password of employee
     * @param description optional query parameter, description of employer
     * @param street optional query parameter, street of employer's address
     * @param city optional query parameter, city of employer's address
     * @param state optional query parameter, state of employer's address
     * @param zip optional query parameter, zip of employer's address
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employer on success, and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/employer",
        method = RequestMethod.POST,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> createEmployer(
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "zip", required = false) String zip,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer e = new Employer();
        if (!validStr(name) || service.employerWithNameExists(name)) {
            return new ResponseEntity<>("{\"msg\":\"Missing, duplicate, or invalid name\"}", HttpStatus.BAD_REQUEST);
        }
        if (!validStr(email)) {
            return new ResponseEntity<>("{\"msg\":\"Missing or invalid email\"}", HttpStatus.BAD_REQUEST);
        }
        if (!validStr(password)) {
            return new ResponseEntity<>("{\"msg\":\"Missing or invalid password\"}", HttpStatus.BAD_REQUEST);
        }
        if (service.getEmployerByEmail(email) != null || employeeService.getEmployeeByEmail(email) != null) {
            return new ResponseEntity<>("{\"msg\":\"Duplicate email\"}", HttpStatus.BAD_REQUEST);
        }
        e.setName(name);
        if (!validStr(id)) {
            e.setId(service.createIdFromName(name));
        } else {
            e.setId(id);
        }
        e.setDescription(description);
        e.setAddress(new Address(street, city, state, zip));
        e.setEmail(email);
        e.setPassword(password);

        Employer employer = service.createEmployer(e);

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(employer);
	}

    /**
     * Endpoint handler for getting employers.
     *
     * @param id path parameter, id of employer
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employer on success, and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/employer/{id}",
        method = RequestMethod.GET,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE 
        }
    )
	public ResponseEntity<?> getEmployer(
        @PathVariable String id,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer e = service.getEmployerById(id);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(e);
	}

    /**
     * Endpoint handler for updating employers.
     *
     * @param id path parameter, id of employer
     * @param name required query parameter, name of employer
     * @param description optional query parameter, description of employer
     * @param street optional query parameter, street of employer's address
     * @param city optional query parameter, city of employer's address
     * @param state optional query parameter, state of employer's address
     * @param zip optional query parameter, zip of employer's address
     * @param format optional query parameter, result format type (json or xml)
     *
     * @return ResponseEntity that includes status code, employer on success, and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/employer/{id}",
        method = RequestMethod.PUT,
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        }
    )
	public ResponseEntity<?> updateEmployer(
        @PathVariable String id,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "street", required = false) String street,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "zip", required = false) String zip,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer e = service.getEmployerById(id);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteEmployer(id);
        if (!validStr(name) || service.employerWithNameExists(name)) {
            return new ResponseEntity<>("Missing, duplicate, or invalid name", HttpStatus.BAD_REQUEST);
        }
        e.setName(name);
        if (validStr(description)) e.setDescription(description);
        if (validStr(street)) e.getAddress().setStreet(street);
        if (validStr(city)) e.getAddress().setCity(city);
        if (validStr(state)) e.getAddress().setState(state);
        if (validStr(zip)) e.getAddress().setZip(zip);

        Employer employer = service.createEmployer(e);

        MediaType contentType = (format.equalsIgnoreCase("json")) ? 
            MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        return ResponseEntity.ok().contentType(contentType).body(employer);
	}

    /**
     * Endpoint handler for deleting employers.
     *
     * @param id path parameter, id of employer
     *
     * @return ResponseEntity that includes status code and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/employer/{id}",
        method = RequestMethod.DELETE
    )
	public ResponseEntity<?> deleteEmployer(
        @PathVariable String id,
        @RequestParam(value = "format", required = false, defaultValue = "json") String format
    ) {
        Employer e = service.getEmployerById(id);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (employeeService.employerHasEmployees(e.getId())) {
            return new ResponseEntity<>("Employer has employees", HttpStatus.BAD_REQUEST);
        }

        service.deleteEmployer(id);
        return new ResponseEntity<>(HttpStatus.OK);
	}

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
