package pt.ipp.isep.dei.esoft.project.core.application.session;

import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Represents a user session within the application.
 * It provides methods to retrieve user details and manage the session.
 */
public class UserSession {

    private pt.isep.lei.esoft.auth.UserSession userSession;

    /**
     * Constructs a UserSession with the given user session.
     *
     * @param userSession the user session from the authentication module
     */
    public UserSession(pt.isep.lei.esoft.auth.UserSession userSession) {
        this.userSession = userSession;
    }

    /**
     * Gets the email of the user associated with this session.
     *
     * @return the email of the user
     */
    public String getUserEmail() {
        return userSession.getUserId().getEmail();
    }

    /**
     * Gets the name of the user associated with this session.
     *
     * @return the name of the user
     */
    public String getUserName() {
        return this.userSession.getUserName();
    }

    /**
     * Gets the roles of the user associated with this session.
     *
     * @return the list of user roles
     */
    public List<UserRoleDTO> getUserRoles() {
        return this.userSession.getUserRoles();
    }

    /**
     * Logs out the user from the current session.
     */
    public void doLogout() {
        this.userSession.doLogout();
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return this.userSession.isLoggedIn();
    }

    /**
     * Checks if the user is logged in with a specific role.
     *
     * @param roleId the role ID to check
     * @return true if the user is logged in with the specified role, false otherwise
     */
    public boolean isLoggedInWithRole(String roleId) {
        return this.userSession.isLoggedInWithRole(roleId);
    }
}
