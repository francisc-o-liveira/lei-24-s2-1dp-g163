package pt.ipp.isep.dei.esoft.project.application.controller.authorization;

import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

public class RegisterController {
    public static final String ROLE_GSM = "GSM";
    public static final String ROLE_HRM = "HRM";
    public static final String ROLE_VFM = "VFM";

    private static final int minDigitsPassword = 2;
    private static final int minCapitalPassword = 3;
    private static final int minAlphPassword = 3;
    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;

    public RegisterController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
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

    public void registerManager(String selectedItem) {
    }

    public void registerCollaborator() {
    }
}
