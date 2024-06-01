package pt.ipp.isep.dei.esoft.project.ui.gui.entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewDetailsEntryAgendaUI {
    private ViewDetailsEntryController ctrl;
    private EntryDto selectedEntry;
    public Stage stage;

    /** to view the details */
    @FXML
    private Label titleEntry;
    @FXML
    private Label stateEntry;
    @FXML
    private Label teamAssignedEntry;
    @FXML
    private DatePicker startDateEntry;
    @FXML
    private TableView<VehicleDto> vehiclesAssigned;
    @FXML
    private TableColumn<VehicleDto, String> vehiclesPlate;

    private ObservableList<VehicleDto> vehiclesList= FXCollections.observableArrayList();

    /** to postpone the entry*/
    @FXML
    private DatePicker postponedDate;
    @FXML
    private Button postponingEntry;

    /**to assign a team*/
    @FXML
    private TableView<TeamDto> teamsToAssign;
    @FXML
    private TableColumn<TeamDto, String> teamsName;
    @FXML
    private Button assignTeam;
    private ObservableList<TeamDto> teamsToAssignList=FXCollections.observableArrayList();
    private TeamDto selectedTeam;

    /**to assign the vehicles*/
    @FXML
    private TableView<VehicleDto> vehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto,String> platesOfVehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto, Boolean> selectingVehicles;
    @FXML
    private Button assignVehicles;
    private ObservableList<VehicleDto> vehiclesToAssignList=FXCollections.observableArrayList();
    private ArrayList<VehicleDto> vehiclesSelected;
    public void setLabels(EntryDto entry, Stage stage){
        ctrl=new ViewDetailsEntryController();
        selectedEntry=entry;
        this.stage=stage;
        titleEntry.setText(entry.getTitle());
        stateEntry.setText(String.valueOf(entry.getStatus()));
        teamAssignedEntry.setText(String.valueOf(entry.getTeamAssigned()));
        LocalDate start=convertToJavaLocalDate(entry.getStartDate());
        startDateEntry.setValue(start);

        vehiclesPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        for(VehicleDto v : entry.getVehicleList()){
           vehiclesList.add(v);
        }

        vehiclesAssigned.setItems(vehiclesList);
    }

    @FXML
    public void btnAssignVehicles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scenes to be made/Scene_AssignVehicles.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        platesOfVehiclesToAssign.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        selectingVehicles.setCellFactory(CheckBoxTableCell.forTableColumn(selectingVehicles));
        for(VehicleDto t : ctrl.getVehicleListPossibleForEntry(selectedEntry)){
            vehiclesToAssignList.add(t);
        }
        vehiclesToAssign.setItems(vehiclesToAssignList);
        assignVehicles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try{
                    getVehiclesToAssign();
                    for(VehicleDto vehicleDto : vehiclesSelected){
                        ctrl.assignVehicleToEntry(vehicleDto,selectedEntry);
                        popUp("Vehicles assigned!").show();
                        if (popUp("Vehicles assigned").showAndWait().get() == ButtonType.OK) {
                            stage.close();
                        }
                    }
                } catch (Exception e){
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        });
    }

    private void getVehiclesToAssign(){
        for (VehicleDto vehicle : ctrl.getVehicleListPossibleForEntry(selectedEntry)) {
            if (vehicle.isSelectedForEntry().get()) {
                vehiclesSelected.add(vehicle);
            }
        }
        if(vehiclesSelected==null){
            throw new NullPointerException("No vehicles were chosen to assign");
        }
    }

    @FXML
    public void btnAssignTeam(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scenes to be made/Scene_AssignTeam.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        teamsName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        for(TeamDto t : ctrl.getTeamListPossibleForEntry(selectedEntry)){
            teamsToAssignList.add(t);
        }
        teamsToAssign.setItems(teamsToAssignList);
        assignTeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                selectedTeam= teamsToAssign.getSelectionModel().getSelectedItem();
                if(selectedTeam==null){
                    popUpOfVerifications(Alert.AlertType.ERROR, "Select a team").show();
                }
                try{
                    ctrl.assignTeamToEntry(selectedTeam,selectedEntry);
                    popUp("Team assigned!").show();
                    if (popUp("Team assigned").showAndWait().get() == ButtonType.OK) {
                        stage.close();
                    }
                } catch (Exception e){
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        });
    }

    @FXML
    public void btnCancel(ActionEvent event) throws IOException{
        try{
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Canceling Entry");
            popUp.setContentText("Do you want to cancel this entry?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                ctrl.cancelEntry(selectedEntry);
                stage.close();
            }
        } catch(Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    public void btnPostpone(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scenes to be made/Scene_PostponeEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        postponingEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try{
                    Date postpone=new Date(postponedDate.getValue().getYear(), postponedDate.getValue().getMonthValue(), postponedDate.getValue().getDayOfMonth());
                    ctrl.postponeEntry(selectedEntry/*postpone*/);
                    popUp("Entry postponed").show();
                    if (popUp("Entry postponed").showAndWait().get() == ButtonType.OK) {
                        stage.close();
                    }
                } catch (Exception e){
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        });
    }

    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
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