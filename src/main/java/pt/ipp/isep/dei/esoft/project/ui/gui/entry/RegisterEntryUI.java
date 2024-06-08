package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI Controller class for registering an entry.
 */
public class RegisterEntryUI implements Initializable {
    /**Controller */
    private AssignEntryOnAgendaController ctrl;
    /** Variable for the selected task */
    private EntryDto selectedTaskDto;
    /** Observable List for the TableView */
    private ObservableList<EntryDto> tasksForTable=FXCollections.observableArrayList();

    @FXML
    private TableView<EntryDto> tasksForEntry;
    @FXML
    private TableColumn<EntryDto, String> titleTask;
    @FXML
    private DatePicker dateForEntry;
    @FXML
    private TextField hoursForEntry;

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl =AssignEntryOnAgendaController.getInstance();
        setTableTasks();
    }

    /**
     * Handles the action of registering an entry.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void btnRegister(ActionEvent event) {
        Date startDate=new Date(dateForEntry.getValue().getYear(),dateForEntry.getValue().getMonthValue(), dateForEntry.getValue().getDayOfMonth());
        String startHour=hoursForEntry.getText();
        selectedTaskDto= tasksForEntry.getSelectionModel().getSelectedItem();
        if(selectedTaskDto==null){
            popUpOfVerifications(Alert.AlertType.ERROR, "A task needs to be selected").show();
        } else {
            try{
                Tempo startTime=getTimeForEntry(startHour);
                EntryDto assigningEntry=new EntryDto(selectedTaskDto.getTitle(), selectedTaskDto.getDescription(), selectedTaskDto.getDegreeUrgency(),selectedTaskDto.getExpectedDuration(), selectedTaskDto.getGreenSpace());
                ctrl.assignEntryOnAgenda(assigningEntry, startDate, startTime);
                if(popUp().showAndWait().get()==ButtonType.OK){
                    Stage stage = (Stage) tasksForEntry.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    /**
     * Parses the time string into Tempo object.
     *
     * @param timeExpected The time string to parse.
     * @return The parsed Tempo object.
     */
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

    /**
     * Sets up the TableView for displaying tasks.
     */
    private void setTableTasks(){
        titleTask.setCellValueFactory(new PropertyValueFactory<>("title"));
        for(EntryDto t : ctrl.getToDoList()){
            if(t.getStartDate()==null){
                tasksForTable.add(t);
            }
        }
        tasksForEntry.setItems(tasksForTable);
    }

    /**
     * Creates a popup alert.
     *
     * @return The created Alert object.
     */
    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Entry added!");

        return alert;
    }

    /**
     * Creates a popup alert with the given alert type and message.
     *
     * @param alertType The type of alert.
     * @param messages The message to display in the popup.
     * @return The created Alert object.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
