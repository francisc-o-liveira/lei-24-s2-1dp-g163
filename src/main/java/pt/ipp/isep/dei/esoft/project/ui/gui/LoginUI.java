package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public Stage leadingPage;

    @FXML
    public void uiToShow() throws IOException{
        //authentication to make sure email is correct goes here

        //choosing which ui is going to be shown
        if(emailLogin.getText().contains("hrm")){
            showHRManagerUI();
        }
        if(emailLogin.getText().equals("admin")){
            showAdminUI();
        }
    }

    public void showHRManagerUI()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneMenu_HRM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);

        leadingPage=new Stage();
        leadingPage.setScene(scene);
        leadingPage.show();
    }

    public void showAdminUI()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/SceneAdmin.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);

        leadingPage=new Stage();
        leadingPage.setScene(scene);
        leadingPage.show();
    }


    private Alert popUp(Alert.AlertType alertType, String header, String message){
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
