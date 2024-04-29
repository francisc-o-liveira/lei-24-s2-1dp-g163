package pt.ipp.isep.dei.esoft.project.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class AuthenticationController {

    public static final String ROLE_GSM = "GSM";
    public static final String ROLE_HRM = "HRM";
    public static final String ROLE_VFM = "VFM";
    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    public boolean addUserWithRole(String managerName, String managerEmail, String managerPassword, String userRole){
        return authenticationRepository.addUserWithRole(managerName, managerEmail, managerPassword, userRole);
    }

    public List<String> getRolesToSelect(){
        List<String> roles = new ArrayList<>();
        roles.add(ROLE_GSM);
        roles.add(ROLE_HRM);
        roles.add(ROLE_VFM);
        return roles;
    }

    public void doLogout() {
        authenticationRepository.doLogout();
    }
}