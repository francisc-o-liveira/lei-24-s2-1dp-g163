package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.adapters.SendEmailExternalAPI;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

public class AuthenticationRepository {

    private final AuthFacade authenticationFacade;

    private static SendEmailExternalAPI sendEmailExternalAPI;

    public AuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }

    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    public void doLogout() {
        authenticationFacade.doLogout();
    }

    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    public boolean existUser(String email) {
        return authenticationFacade.existsUser(email);
    }

    public static void setSendEmailExternalAPI(SendEmailExternalAPI sendEmailExternalAPI) {
        AuthenticationRepository.sendEmailExternalAPI = sendEmailExternalAPI;
    }

}