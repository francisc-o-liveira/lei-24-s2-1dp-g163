package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.adapters.SendEmailExternalAPI;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AuthenticationRepository {

    private final AuthFacade authenticationFacade;

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
        storeUserCredentialInDataBase(name, email, pwd, roleId);
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    public boolean existUser(String email) {
        return authenticationFacade.existsUser(email);
    }

    public void storeUserCredentialInDataBase(String name, String email,String pwd,String roleId){
        Boolean emailAlreadyResgistered = false;
        try {
            Scanner scanner = new Scanner(MainApp.getAuthDataBaseFile());
            while (scanner.hasNextLine()){

                if (scanner.nextLine().split(";")[1].equals(email)){
                    emailAlreadyResgistered = true;
                    break;
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!emailAlreadyResgistered){
            try {
                FileWriter send = new FileWriter(MainApp.getAuthDataBaseFile(), true);
                send.write(name+";"+email+";"+pwd+";"+roleId);
                send.write(System.lineSeparator());
                send.flush();
                send.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadFromAuthDataBase(){

        addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        addUserRole(RegisterController.ROLE_HRM, RegisterController.ROLE_HRM);
        addUserRole(RegisterController.ROLE_GSM, RegisterController.ROLE_GSM);
        addUserRole(RegisterController.ROLE_VFM, RegisterController.ROLE_VFM);
        addUserRole(RegisterController.ROLE_COLAB, RegisterController.ROLE_COLAB);


        try {
            Scanner scanner = new Scanner(MainApp.getAuthDataBaseFile());
            while (scanner.hasNextLine()){

                String currentLine = scanner.nextLine();
                String[] curentLineSplited = currentLine.split(";");
                addUserWithRole(curentLineSplited[0],curentLineSplited[1],curentLineSplited[2],curentLineSplited[3]);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}