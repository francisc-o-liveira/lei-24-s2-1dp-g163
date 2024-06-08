package pt.ipp.isep.dei.esoft.project.core.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.core.application.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for user registration and authentication.
 */
public class RegisterController {
    public static final String ROLE_GSM = "GSM";
    public static final String ROLE_HRM = "HRM";
    public static final String ROLE_VFM = "VFM";
    public static final String ROLE_COLAB = "COLAB";
    public static final List<String> roles = new ArrayList<>();

    private static final int minDigitsPassword = 2;
    private static final int minCapitalPassword = 3;
    private static final int minAlphPassword = 3;

    private final AuthenticationRepository authenticationRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final Organization organization;

    // Private constructor to enforce singleton pattern
    private RegisterController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        this.collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        this.organization = Repositories.getInstance().getOrganizationRepository();
        roles.add(ROLE_GSM);
        roles.add(ROLE_HRM);
        roles.add(ROLE_VFM);
        roles.add(ROLE_COLAB);
    }

    /**
     * Checks if a user exists.
     *
     * @param email The email of the user.
     * @return True if the user exists, false otherwise.
     */
    public boolean userExists(String email) {
        return authenticationRepository.existUser(email);
    }

    /**
     * Retrieves the roles available for selection.
     *
     * @return A list of role names.
     */
    public List<String> getRolesToSelect(){
        List<String> roles = new ArrayList<>();
        roles.add(ROLE_GSM);
        roles.add(ROLE_HRM);
        roles.add(ROLE_VFM);
        return roles;
    }

    /**
     * Verifies if a password meets the criteria.
     *
     * @param passwordLogin The password to verify.
     * @return True if the password meets the criteria, false otherwise.
     */
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

    /**
     * Registers a manager.
     *
     * @param selectedItem The selected role for the manager.
     * @param email The email of the manager.
     * @param password The password of the manager.
     */
    public void registerManager(String selectedItem, String email, String password) {
        if (organization.haveManagerWithEmail(email)){
            authenticationRepository.addUserWithRole(selectedItem,email,password,selectedItem);
        }else {
            throw new IllegalArgumentException("Manager does not exist in the database");
        }
    }

    /**
     * Registers a collaborator.
     *
     * @param email The email of the collaborator.
     * @param password The password of the collaborator.
     */
    public void registerCollaborator(String email, String password) {
        if(collaboratorRepository.haveCollaboratorWithEmail(email)){
            authenticationRepository.addUserWithRole(ROLE_COLAB,email,password,ROLE_COLAB);
        }else{
            throw new IllegalArgumentException("Collaborator does not exist in this organization");
        }
    }

    /**
     * Retrieves the available roles.
     *
     * @return A list of available roles.
     */
    public List<String> getRoles(){
        return roles;
    }

    // Singleton pattern implementation
    private static RegisterController instance;
    public static RegisterController getInstance(){
        if(instance == null){
            synchronized (RegisterController.class) {
                instance = new RegisterController();
            }
        }
        return instance;
    }
}
