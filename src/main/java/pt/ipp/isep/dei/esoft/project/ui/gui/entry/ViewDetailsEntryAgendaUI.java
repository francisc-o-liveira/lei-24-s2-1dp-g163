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
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class ViewDetailsEntryAgendaUI {
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

    public void setLabels(EntryDto entry){
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
        //go to controller and remove it
    }

    @FXML
    public void btnPostpone(ActionEvent event){
        //go to controller and postpone the event
    }

    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

}