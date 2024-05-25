package pt.ipp.isep.dei.esoft.project.ui.gui.register;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.manage.ManageGreenSpacesUI;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterGreenSpaceUI implements Initializable {

    public RegisterGreenSpaceController ctrl;

    public Stage stage;

    @FXML
    private TextField nameGreenSpace;
    @FXML
    private TextField addressGreenSpace;
    @FXML
    private TextField areaGreenSpace;
    @FXML
    private ComboBox<GreenSpace.Type> greenSpaceType;


    @Override
    public void initialize(URL url, ResourceBundle rbl){
        ctrl=new RegisterGreenSpaceController();
        greenSpaceType.setItems(FXCollections.observableArrayList(GreenSpace.getEnumGreenSpaceTypes()));
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    @FXML
    public void btnRegister(javafx.event.ActionEvent event){
        String name=nameGreenSpace.getText();
        String address=addressGreenSpace.getText();
        String area=areaGreenSpace.getText();
        GreenSpace.Type typeOfGreenSpace=greenSpaceType.getValue();
        if(name.isEmpty() || address.isEmpty() || area.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Green Space is empty").show();
        } else {
            try{
                double areaRegistering=Double.parseDouble(area);
                ctrl.registerGreenSpace(name,address,areaRegistering,typeOfGreenSpace);
                if(popUp().showAndWait().get()==ButtonType.OK){
                    stage.close();
                }
            } catch (Exception e){
                popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Green Space added!");
        return alert;
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

}
