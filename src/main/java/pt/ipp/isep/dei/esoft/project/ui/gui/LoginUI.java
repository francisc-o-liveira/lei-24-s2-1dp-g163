package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;


public class LoginUI{
    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Button doLogin;

    @FXML
    private Button forgotPassword;

    public Stage mainStage;
    public AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

    public void setMainStage(Stage mainStage){
        this.mainStage=mainStage;
    }

    @FXML
    public void uiToShow(ActionEvent event) throws IOException{
        if(authenticateCredentials(emailLogin.getText(),passwordLogin.getText())){
            //deciding which ui is going to be shown
            if(emailLogin.getText().contains("hrm")){
                showHRManagerUI();
            }
            if(emailLogin.getText().contains("vfm")){
                showVFManagerUI();
            }
            if(emailLogin.getText().contains("gsm")){
                showGSManagerUI();
            }
            if(emailLogin.getText().equals("admin")){
                showAdminUI();
            }
        }
    }

    private boolean authenticateCredentials(String email, String password){
        try{
            authenticationRepository.doLogin(emailLogin.getText(),passwordLogin.getText());
        }catch (IllegalArgumentException e){
            popUp(Alert.AlertType.ERROR, "Invalid Credentials of Login", "Try Again Please").show();
            return false;
        }
        return true;
    }

    @FXML
    public void btnForgotPassword(ActionEvent event) throws IOException{
        // FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneForgotPassword.fxml"));
        // Parent root= fxmlLoader.load();
        // Scene scene= new Scene(root);

            popUp(Alert.AlertType.ERROR, "Please Contact the Administrator", "He can unblock your account and trade your password").show();


        // Stage leadingPage=new Stage();
        // leadingPage.setScene(scene);
        // leadingPage.show();
    }

    public void showHRManagerUI()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneMenu_HRM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showVFManagerUI() throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneMenu_VFM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showGSManagerUI() throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneMenu_GSM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showAdminUI()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneAdmin.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }


    private Alert popUp(Alert.AlertType alertType, String header, String message){
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
