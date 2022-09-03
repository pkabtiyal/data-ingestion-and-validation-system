package app.data_ingestion.controllers.organizationAdmin;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.organizationAdminService.IOrganizationAdminService;

@CrossOrigin
@RestController
public class OrganizationManagementController {

    @Autowired
    IOrganizationAdminService organizationAdminService;
    static final String SUCCESS_MESSAGE = LiteralConstants.LOGIN_SUCCESS_MESSAGE;
    static final String INVALID_CREDENTIALS_MESSAGE = LiteralConstants.LOGIN_INVALID_CREDENTIALS_MESSAGE;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;


    /**
     * controller to add organization user
     *
     * @param user
     * @return
     */
    @PostMapping(path = "/organization/admin/")
    @ResponseBody
    public ResponseEntity<Object> addOrganizationUser(@RequestBody User user) {
        User response;
        try {
            response = organizationAdminService.addOrganizationUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to update system/organization user
     *
     * @param username
     * @param user
     * @return
     */
    @PutMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> updateSystemUser(@PathVariable(required = false, name = "username") String username,
                                                   @RequestBody User user) {
        User response;
        user.setUsername(username);
        try {
            response = organizationAdminService.updateOrganization(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to delete organization user
     *
     * @param username
     * @return
     */
    @DeleteMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> deleteOrganizationUser(
            @PathVariable(required = false, name = "username") String username) {
        User response;
        try {
            response = organizationAdminService.deleteOrganization(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to get an organization admin user
     *
     * @param username
     * @return
     */
    @GetMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> getOrganizationUser(
            @PathVariable(required = false, name = "username") String username) {
        User response;
        try {
            response = organizationAdminService.getOrganizationAdmin(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to get all organization admin users
     *
     * @return ResponseEntity<Object>
     */
    @GetMapping(path = "/organization/admin/")
    @ResponseBody
    public ResponseEntity<Object> getOrganizationUser() {
        try {
            List<User> response = organizationAdminService.listAllOrganizationAdmin();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }

    /**
     * controller to get all users of an organization
     *
     * @return ResponseEntity<Object>
     */
    @GetMapping(path = "/organization/user/{organizationname}")
    @ResponseBody
    public ResponseEntity<Object> getOrganizationListAllUsers(@PathVariable(required = false, name = "organizationname") String organizationname) {
        try {
            List<User> response = organizationAdminService.listAllOrganizationUser(organizationname);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }
}
