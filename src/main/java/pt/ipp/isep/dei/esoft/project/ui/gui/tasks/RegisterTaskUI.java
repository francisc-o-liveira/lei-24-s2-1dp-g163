package pt.ipp.isep.dei.esoft.project.ui.gui.tasks;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterTaskController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

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
    private ComboBox<Task.DegreeUrgency> degreeOfUrgency;

    @FXML
    private ComboBox<GreenSpaceDto> greenSpace;

    @FXML
    private TextField timeExpected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new RegisterTaskController();
        greenSpace.setItems(FXCollections.observableArrayList(ctrl.getGreenSpaceList()));
        degreeOfUrgency.setItems(FXCollections.observableArrayList(ctrl.getDegreOfUrgency()));
    }

    @FXML
    public void btnRegister(ActionEvent event) {
        String title = this.title.getText();
        String description = this.description.getText();
        Task.DegreeUrgency degreeOfUrgency = this.degreeOfUrgency.getValue();
        String timeExpected = this.timeExpected.getText();
        GreenSpaceDto greenSpaceDto = this.greenSpace.getValue();
        if (title==null ||  description==null || degreeOfUrgency==null || timeExpected==null || greenSpaceDto==null) {
            popUpOfVerifications(Alert.AlertType.ERROR,"Please input data in all fields");
        }else {
            try {
                Tempo timeExpec = getTimeForEntry(timeExpected);
                EntryDto entryDto = new EntryDto(new EntryState(),title,description,degreeOfUrgency,timeExpec,greenSpaceDto);
                ctrl.registerTaskEntry(entryDto);
                if(popUp().showAndWait().get()== ButtonType.OK){
                    stage.close();
                }
            }catch (IllegalArgumentException e){
                popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Tempo getTimeForEntry(String timeExpected) {
        String[] times = timeExpected.split(":");
        Tempo time;
        if (times.length == 2) {
            time = new Tempo(Integer.parseInt(times[0]),Integer.parseInt(times[1]));
        }else {
            throw new IllegalArgumentException("Invalid time format");
        }
        return time;
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
