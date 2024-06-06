package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignTeamUI {
    @FXML
    private TableView<TeamDto> teamsToAssign;
    @FXML
    private TableColumn<TeamDto, String> teamsName;
    private ObservableList<TeamDto> teamsToAssignList= FXCollections.observableArrayList();
    private TeamDto selectedTeam;
    private EntryDto selectedEntry;

    private ViewDetailsEntryController ctrl;

    public void setEntry(EntryDto entry){
        ctrl= ViewDetailsEntryController.getInstance();
        selectedEntry=entry;
        initializeUI();
    }

    public void initializeUI(){
        teamsName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        for(TeamDto t : ctrl.getTeamListPossibleForEntry(selectedEntry)){
            teamsToAssignList.add(t);
        }
        teamsToAssign.setItems(teamsToAssignList);
    }

    @FXML
    public void assignTeam(ActionEvent event){
        selectedTeam= teamsToAssign.getSelectionModel().getSelectedItem();
        try{
            ctrl.assignTeamToEntry(selectedTeam,selectedEntry);
            if(popUp("Team assigned!").showAndWait().get()==ButtonType.OK){
                Stage stage= (Stage) teamsToAssign.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private Alert popUp(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);

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
