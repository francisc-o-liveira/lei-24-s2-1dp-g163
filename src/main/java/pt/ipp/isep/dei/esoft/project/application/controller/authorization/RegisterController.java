package pt.ipp.isep.dei.esoft.project.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    public static final String ROLE_GSM = "GSM";
    public static final String ROLE_HRM = "HRM";
    public static final String ROLE_VFM = "VFM";
    public static final String ROLE_COLAB = "COLAB";
    public static final List<String> roles=new ArrayList<>();

    private static final int minDigitsPassword = 2;
    private static final int minCapitalPassword = 3;
    private static final int minAlphPassword = 3;
    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;

    private final CollaboratorRepository collaboratorRepository;

    private final Organization organization;

    public RegisterController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        this.collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        this.organization= Repositories.getInstance().getOrganizationRepository();
        roles.add(ROLE_GSM);
        roles.add(ROLE_HRM);
        roles.add(ROLE_VFM);
        roles.add(ROLE_COLAB);
    }

    public boolean userExists(String email) {
        return authenticationRepository.existUser(email);
    }

    public boolean verifyPassword(String passwordLogin) {
        int countCapital=0;
        int countDigits=0;
        int countAlph=0;
        char[] password = passwordLogin.toCharArray();
        for (int i = 0; i < password.length; i++) {
            if(Character.isLetter(password[i])) {
                countAlph++;
                if(!Character.isLowerCase(password[i])){
                    countCapital++;
                }
            }
            if(Character.isDigit(password[i])) {
                countDigits++;
            }
        }
        return countCapital >= minCapitalPassword && countDigits >= minDigitsPassword && countAlph >= minAlphPassword;
    }

    public void registerManager(String selectedItem, String email, String password) {
        if (organization.haveManagerWithEmail(email)){
            authenticationRepository.addUserWithRole(selectedItem,email,password,selectedItem);
        }else {
            throw new IllegalArgumentException("Manager does not exist in the database");
        }
    }

    public void registerCollaborator(String email, String password) {
        if(collaboratorRepository.haveCollaboratorWithEmail(email)){
            authenticationRepository.addUserWithRole(ROLE_COLAB,email,password,ROLE_COLAB);
        }else{
            throw new IllegalArgumentException("Collaborator does not exist in this organization");
        }
    }

    public List<String> getRoles(){
        return roles;
    }
}
