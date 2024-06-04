package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterEntryUI implements Initializable {
    private AssignEntryOnAgendaController ctrl;
    private EntryDto selectedTaskDto;
    private ObservableList<EntryDto> tasksForTable=FXCollections.observableArrayList();

    @FXML
    private TableView<EntryDto> tasksForEntry;
    @FXML
    private TableColumn<EntryDto, String> titleTask;
    @FXML
    private DatePicker dateForEntry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl =  AssignEntryOnAgendaController.getInstance();
        setTableTasks();
    }

    @FXML
    public void btnRegister(ActionEvent event) {
        Date startDate=new Date(dateForEntry.getValue().getYear(),dateForEntry.getValue().getMonthValue(), dateForEntry.getValue().getDayOfMonth());
        selectedTaskDto= tasksForEntry.getSelectionModel().getSelectedItem();
        if(selectedTaskDto==null){
            popUpOfVerifications(Alert.AlertType.ERROR, "A task needs to be selected").show();
        } else {
            try{
                EntryDto assigningEntry=new EntryDto(selectedTaskDto.getTitle(), selectedTaskDto.getDescription(), selectedTaskDto.getDegreeUrgency(),selectedTaskDto.getExpectedDuration(), selectedTaskDto.getGreenSpace());
                ctrl.assignEntryOnAgenda(assigningEntry, startDate);
                if(popUp().showAndWait().get()==ButtonType.OK){
                    Stage stage = (Stage) tasksForEntry.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void setTableTasks(){
        titleTask.setCellValueFactory(new PropertyValueFactory<>("title"));
        for(EntryDto t : ctrl.getToDoList()){
            if(t.getStartDate()==null){
                tasksForTable.add(t);
            }
        }
        tasksForEntry.setItems(tasksForTable);
    }

    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Entry added!");

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
