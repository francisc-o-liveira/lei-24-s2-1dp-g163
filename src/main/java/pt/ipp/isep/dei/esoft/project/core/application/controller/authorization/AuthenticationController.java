package pt.ipp.isep.dei.esoft.project.core.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.core.application.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Controller class for authentication operations.
 */
public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMIN";

    private final AuthenticationRepository authenticationRepository;

    // Private constructor to enforce singleton pattern
    private AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Performs user login.
     *
     * @param email The user's email.
     * @param pwd The user's password.
     * @return True if the login is successful, false otherwise.
     */
    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Retrieves the current user's role.
     *
     * @return The current user's role DTO.
     */
    public UserRoleDTO getAtualUserRole(){
        return selectsRole(getUserRoles());
    }

    /**
     * Retrieves the roles of the current user.
     *
     * @return A list of user role DTOs.
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    // Selects a role from the list of roles
    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Adds a new user with a role.
     *
     * @param managerName The name of the new user.
     * @param managerEmail The email address of the new user.
     * @param managerPassword The password of the new user.
     * @param userRole The role of the new user.
     * @return True if the user is successfully added with the role, false otherwise.
     */
    public boolean addUserWithRole(String managerName, String managerEmail, String managerPassword, String userRole){
        return authenticationRepository.addUserWithRole(managerName, managerEmail, managerPassword, userRole);
    }

    /**
     * Performs user logout.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
    }

    // Singleton pattern implementation
    private static AuthenticationController instance;
    public static AuthenticationController getInstance(){
        if(instance == null){
            synchronized (AuthenticationController.class) {
                instance = new AuthenticationController();
            }
        }
        return instance;
    }
}
