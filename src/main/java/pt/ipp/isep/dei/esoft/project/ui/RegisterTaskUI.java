package pt.ipp.isep.dei.esoft.project.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterTaskController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterTaskUI implements Initializable {

    private Stage stage;

    private RegisterTaskController ctrl;
    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<String> degreeOfUrgency;

    @FXML
    private ComboBox<GreenSpaceDto> greenSpaceDtoComboBox;

    @FXML
    private TextField timeExpected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new RegisterTaskController();
        greenSpaceDtoComboBox.setItems(FXCollections.observableArrayList(ctrl.getGreenSpaceList()));
    }

    @FXML
    public void btnRegisterOnAction(ActionEvent event) {
        String title = this.title.getText();
        String description = this.description.getText();
        String degreeOfUrgency = this.degreeOfUrgency.getValue();
        String timeExpected = this.timeExpected.getText();
        if (title==null ||  description==null || degreeOfUrgency==null || timeExpected==null) {
            popUpOfVerifications(Alert.AlertType.ERROR,"Please input data in all fields");
        }else {
            try {
                EntryDto entryDto = new EntryDto();
                ctrl.registerTaskEntry(entryDto);
                if(popUp().showAndWait().get()== ButtonType.OK){
                    stage.close();
                }
            }catch (){
                popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    public void setStage(Stage stage){
        this.stage=stage;
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
