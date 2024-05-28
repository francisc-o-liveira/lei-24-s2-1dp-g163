package pt.ipp.isep.dei.esoft.project.ui.gui.register;

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
import java.util.List;
import java.util.ResourceBundle;

public class RegisterEntryUI implements Initializable {

    private Stage stage;
    private AssignEntryOnAgendaController ctrl;
    private EntryDto selectedTaskDto;
    private ObservableList<EntryDto> tasksForTable=FXCollections.observableArrayList();

    @FXML
    private TableView<EntryDto> tasksForEntry;
    @FXML
    private TableColumn<EntryDto, String> titleTask;
    @FXML
    private TableColumn<EntryDto, Boolean> selectTask;
    @FXML
    private DatePicker dateForEntry;
    @FXML
    private ComboBox<EntryState> statusOfEntry;

    private ObservableList<EntryState> entryStates=FXCollections.observableArrayList();


    //TaskDto needs to take a BooleanProperty
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new AssignEntryOnAgendaController();
        statusOfEntry.setItems(entryStates);
        setTableTasks();
    }

    @FXML
    public void btnRegister(ActionEvent event) {
        Date startDate=new Date(dateForEntry.getValue().getYear(),dateForEntry.getValue().getMonthValue(), dateForEntry.getValue().getDayOfMonth());
        selectedTaskDto= tasksForEntry.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
        EntryState state=statusOfEntry.getValue();
        if(selectedTaskDto==null){
            popUpOfVerifications(Alert.AlertType.ERROR, "A task needs to be selected").show();
        } else {
            try{
                EntryDto assigningEntry=new EntryDto(state,selectedTaskDto.getTitle(), selectedTaskDto.getDescription(), selectedTaskDto.getDegreeUrgency(),selectedTaskDto.getExpectedDuration(), selectedTaskDto.getGreenSpace(), startDate);
                //missing arguments in here ^^
                ctrl.assignEntryOnAgenda(assigningEntry);
            } catch (Exception e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void setTableTasks(){
        titleTask.setCellValueFactory(new PropertyValueFactory<>("title"));
        selectTask.setCellFactory(CheckBoxTableCell.forTableColumn(selectTask));

        for(EntryDto t : ctrl.getToDoList()){
            tasksForTable.add(t);
        }

        tasksForEntry.setItems(tasksForTable);
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
