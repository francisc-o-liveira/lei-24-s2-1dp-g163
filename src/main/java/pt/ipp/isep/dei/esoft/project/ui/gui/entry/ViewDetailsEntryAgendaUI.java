package pt.ipp.isep.dei.esoft.project.ui.gui.entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class ViewDetailsEntryAgendaUI {
    private ViewDetailsEntryController ctrl;
    private EntryDto selectedEntry;
    public Stage stage;
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

    public void setLabels(EntryDto entry, Stage stage){
        ctrl=new ViewDetailsEntryController();
        selectedEntry=entry;
        this.stage=stage;
        titleEntry.setText(entry.getTitle());
        stateEntry.setText(String.valueOf(entry.getStatus()));
        //teamAssignedEntry.setText(String.valueOf(entry.getTeam()));
        LocalDate start=convertToJavaLocalDate(entry.getStartDate());
        startDateEntry.setValue(start);

        vehiclesPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        /*for(VehicleDto v : entry.getVehicleList()){
            vehiclesList.add(v);
        }*/

        vehiclesAssigned.setItems(vehiclesList);
    }

    @FXML
    public void btnAssignVehicles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_AssignVehicles.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnAssignTeam(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_AssignTeam.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
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
    public void btnPostpone(ActionEvent event){

    }

    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Entry removed!");

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