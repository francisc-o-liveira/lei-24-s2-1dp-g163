package pt.ipp.isep.dei.esoft.project.ui.gui.register;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterTaskController;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegisterTaskUI implements Initializable {

    public RegisterTaskController ctrl;
    public RegisterGreenSpaceController ctrlGreenSpaces;
    public Stage stage;
    public ObservableList<GreenSpaceDto> greenSpaces=FXCollections.observableArrayList();
    public ObservableList<Task.DegreeUrgency> degreeUrgencies=FXCollections.observableArrayList();

    @FXML
    private ComboBox<GreenSpaceDto> greenSpaceComboBox;
    @FXML
    private TextField taskTitle;
    @FXML
    private TextField taskDescription;
    @FXML
    private TextField taskDuration;
    @FXML
    private ComboBox<Task.DegreeUrgency> degreeUrgencyComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rbl){
        ctrl=new RegisterTaskController();
        ctrlGreenSpaces=new RegisterGreenSpaceController();
        greenSpaces.addAll(ctrlGreenSpaces.getGreenSpaces());
        greenSpaceComboBox.setItems(greenSpaces);
        degreeUrgencies.addAll(Arrays.asList(ctrl.getDegreOfUrgency()));
        degreeUrgencyComboBox.setItems(degreeUrgencies);
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }


    @FXML
    public void btnRegister(javafx.event.ActionEvent event){
        String titleTask=taskTitle.getText();
        String descriptionTask=taskDescription.getText();
        String durationTask=taskDuration.getText();
        Task.DegreeUrgency urgency=degreeUrgencyComboBox.getValue();
        GreenSpaceDto greenSpace=greenSpaceComboBox.getValue();

        if(titleTask.isEmpty() || descriptionTask.isEmpty() || durationTask.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Green Space is empty").show();
        } else {
            try{
                //registry needs to be done here
                if(popUp().showAndWait().get()== ButtonType.OK){
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
