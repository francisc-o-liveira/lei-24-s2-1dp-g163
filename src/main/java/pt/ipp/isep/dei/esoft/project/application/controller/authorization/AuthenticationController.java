package pt.ipp.isep.dei.esoft.project.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMIN";

    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;

   private AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public UserRoleDTO getAtualUserRole(){
        return selectsRole(getUserRoles());
    }

    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public boolean addUserWithRole(String managerName, String managerEmail, String managerPassword, String userRole){
        return authenticationRepository.addUserWithRole(managerName, managerEmail, managerPassword, userRole);
    }


    public void doLogout() {
        authenticationRepository.doLogout();
    }

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