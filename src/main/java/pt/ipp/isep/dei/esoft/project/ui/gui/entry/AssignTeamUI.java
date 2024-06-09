package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.TeamDto;

/**
 * UI controller class for assigning a team to an entry.
 * It handles the display and interaction with the teams available for assignment.
 */
public class AssignTeamUI {

    /**
     * Table view for displaying teams available for assignment.
     */
    @FXML
    private TableView<TeamDto> teamsToAssign;

    /**
     * Table column for displaying the name of the teams.
     */
    @FXML
    private TableColumn<TeamDto, String> teamsName;

    /**
     * Observable list of teams available for assignment.
     */
    private ObservableList<TeamDto> teamsToAssignList = FXCollections.observableArrayList();

    /**
     * The selected team from the table.
     */
    private TeamDto selectedTeam;

    /**
     * The selected entry for which a team is to be assigned.
     */
    private EntryDto selectedEntry;

    /**
     * Controller for viewing details of an entry.
     */
    private ViewDetailsEntryController ctrl;

    /**
     * Sets the entry for which a team is to be assigned and initializes the UI.
     *
     * @param entry The entry for which a team is to be assigned.
     */
    public void setEntry(EntryDto entry){
        ctrl = ViewDetailsEntryController.getInstance();
        selectedEntry = entry;
        initializeUI();
    }

    /**
     * Initializes the UI by setting up the table columns and populating the team list.
     */
    public void initializeUI(){
        teamsName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        for(TeamDto t : ctrl.getTeamListPossibleForEntry(selectedEntry)){
            teamsToAssignList.add(t);
        }
        teamsToAssign.setItems(teamsToAssignList);
    }

    /**
     * Handles the action of assigning the selected team to the entry.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void assignTeam(ActionEvent event){
        selectedTeam = teamsToAssign.getSelectionModel().getSelectedItem();
        try{
            ctrl.assignTeamToEntry(selectedTeam, selectedEntry);
            if(popUp("Team assigned!").showAndWait().get() == ButtonType.OK){
                Stage stage = (Stage) teamsToAssign.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Creates a pop-up alert with a given message.
     *
     * @param message The message to display in the alert.
     * @return The alert.
     */
    private Alert popUp(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);

        return alert;
    }

    /**
     * Creates a pop-up alert for verification errors.
     *
     * @param alertType The type of alert.
     * @param messages  The error message.
     * @return The alert.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
