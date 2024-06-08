package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.adapters.SendEmailExternalAPI;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * Repository class for authentication operations.
 */
public class AuthenticationRepository {

    private final AuthFacade authenticationFacade;

    /**
     * Constructs an AuthenticationRepository object.
     */
    public AuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }

    /**
     * Attempts to log in a user.
     *
     * @param email the user's email
     * @param pwd   the user's password
     * @return true if the login attempt is successful, false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    /**
     * Logs out the current user.
     */
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Retrieves the current user session.
     *
     * @return the current user session
     */
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Adds a new user role.
     *
     * @param id          the ID of the role
     * @param description the description of the role
     * @return true if the role is successfully added, false otherwise
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Adds a new user with a specified role.
     *
     * @param name   the name of the user
     * @param email  the email of the user
     * @param pwd    the password of the user
     * @param roleId the ID of the role
     * @return true if the user is successfully added, false otherwise
     */
    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        storeUserCredentialInDataBase(name, email, pwd, roleId);
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email of the user
     * @return true if the user exists, false otherwise
     */
    public boolean existUser(String email) {
        return authenticationFacade.existsUser(email);
    }

    /**
     * Removes user credentials from the database based on email.
     *
     * @param email the email of the user whose credentials should be removed
     */

    public void removeUserCredentialsInDataBase(String email) {
        String line = "";
        Boolean emailAlreadyResgistered = false;
        try {
            Scanner scanner = new Scanner(MainApp.getAuthDataBaseFile());
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                if (line.split(";")[1].equals(email)){
                    emailAlreadyResgistered = true;
                    break;
                }

            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (emailAlreadyResgistered){
            try {
                // Leia o conteúdo do ficheiro
                String conteudo = new String(Files.readAllBytes(Paths.get(MainApp.getAuthDataBaseFile().toString())));

                // Remova o texto desejado
                String conteudoModificado = conteudo.replace(line, "");

                // Escreva o conteúdo modificado de volta ao ficheiro
                Files.write(Paths.get(MainApp.getAuthDataBaseFile().toString()), conteudoModificado.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Texto removido com sucesso!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Stores user credentials in the database.
     *
     * @param name   the name of the user
     * @param email  the email of the user
     * @param pwd    the password of the user
     * @param roleId the ID of the role
     */
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
        }catch (FileNotFoundException e) {
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

    /**
     * Loads user data from the authentication database.
     */

    public void loadFromAuthDataBase(){
        addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        addUserRole(RegisterController.ROLE_HRM, RegisterController.ROLE_HRM);
        addUserRole(RegisterController.ROLE_GSM, RegisterController.ROLE_GSM);
        addUserRole(RegisterController.ROLE_VFM, RegisterController.ROLE_VFM);
        addUserRole(RegisterController.ROLE_COLAB, RegisterController.ROLE_COLAB);
        try {
            File file = MainApp.getAuthDataBaseFile();
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("Organization database file did not exist and has been created. Starting with an empty list.");
                    } else {
                        throw new IOException("Organization database file does not exist and could not be created.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException("An error occurred while trying to create the Organization database file.", e);
                }
            }
            Scanner scanner = new Scanner(MainApp.getAuthDataBaseFile());

            while (scanner.hasNextLine()){

                String currentLine = scanner.nextLine();
                String[] currentLineSplited = currentLine.split(";");
                if (currentLineSplited.length != 4){
                    break;
                }
                addUserWithRole(currentLineSplited[0],currentLineSplited[1],currentLineSplited[2],currentLineSplited[3]);

            }
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}