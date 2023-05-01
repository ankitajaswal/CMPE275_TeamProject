package cmpe275.lab2.controller;

import cmpe275.lab2.entity.Employer;
import cmpe275.lab2.entity.Employee;
import cmpe275.lab2.entity.Collaboration;
import cmpe275.lab2.service.EmployerService;
import cmpe275.lab2.service.EmployeeService;
import cmpe275.lab2.service.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * The main rest controller for collaborations.
 */
@RestController
public class CollaborationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private CollaborationService collaborationService;

    /**
     * Endpoint handler for creating collaborations.
     *
     * @param employerId1 path parameter
     * @param employeeId1 path parameter
     * @param employerId2 path parameter
     * @param employeeId2 path parameter
     * @return ResponseEntity that includes status code and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/collaborators/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}",
        method = RequestMethod.PUT
    )
	public ResponseEntity<?> createCollaboration(
        @PathVariable String employerId1,
        @PathVariable long employeeId1,
        @PathVariable String employerId2,
        @PathVariable long employeeId2
    ) {

        if (!validStr(employerId1) || !validStr(employerId2)) {
            return new ResponseEntity<>("Invalid or missing employer IDs", HttpStatus.BAD_REQUEST);
        }

        Employer employer1, employer2;
        Employee employee1, employee2;

        if ((employer1 = employerService.getEmployerById(employerId1)) == null ||
            (employer2 = employerService.getEmployerById(employerId2)) == null ||
            (employee1 = employeeService.getEmployee(employeeId1)) == null ||
            (employee2 = employeeService.getEmployee(employeeId2)) == null) {
            return new ResponseEntity<>("Invalid IDs", HttpStatus.NOT_FOUND);
        }

        collaborationService.addCollaboration(new Collaboration(employer1, employee1, employer2, employee2));
        return new ResponseEntity<>("{\"msg\":\"New collaboration created between employees " + employee1.getId() + " and "+ employee2.getId() + "\"}", HttpStatus.OK);
	}

    /**
     * Endpoint handler for deleting collaborations.
     *
     * @param employerId1 path parameter
     * @param employeeId1 path parameter
     * @param employerId2 path parameter
     * @param employeeId2 path parameter
     * @return ResponseEntity that includes status code and error message on failure
     */
    @Transactional
	@RequestMapping(
        value = "/collaborators/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}",
        method = RequestMethod.DELETE
    )
	public ResponseEntity<?> deleteCollaboration(
        @PathVariable String employerId1,
        @PathVariable long employeeId1,
        @PathVariable String employerId2,
        @PathVariable long employeeId2
    ) {

        if (!validStr(employerId1) || !validStr(employerId2)) {
            return new ResponseEntity<>("Invalid employer IDs", HttpStatus.BAD_REQUEST);
        }

        Employer employer1, employer2;
        Employee employee1, employee2;

        if ((employer1 = employerService.getEmployerById(employerId1)) == null ||
            (employer2 = employerService.getEmployerById(employerId2)) == null ||
            (employee1 = employeeService.getEmployee(employeeId1)) == null ||
            (employee2 = employeeService.getEmployee(employeeId2)) == null) {
            return new ResponseEntity<>("Invalid IDs", HttpStatus.NOT_FOUND);
        }
        collaborationService.deleteCollaboration(new Collaboration(employer1, employee1, employer2, employee2));
        return new ResponseEntity<>("{\"msg\":\"Removing collaboration created between employees " + employee1.getId() + " and "+ employee2.getId() + "\"}", HttpStatus.OK);
	}

    private boolean validStr(String str) {
        return str != null && !str.isEmpty();
    }
}
