package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;

import java.util.ArrayList;

public class AssignVehicleUI {
    @FXML
    private TableView<VehicleDto> vehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto,String> platesOfVehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto, Boolean> selectingVehicles;
    private ObservableList<VehicleDto> vehiclesToAssignList= FXCollections.observableArrayList();
    private ArrayList<VehicleDto> vehiclesSelected;
    private EntryDto selectedEntry;

    private ViewDetailsEntryController ctrl;

    public void setEntry(EntryDto entry){
        ctrl=new ViewDetailsEntryController();
        selectedEntry=entry;
        vehiclesSelected=new ArrayList<>();
        initializeUI();
    }

    public void initializeUI(){
        platesOfVehiclesToAssign.setCellValueFactory(new PropertyValueFactory<>("plate"));
        selectingVehicles.setCellValueFactory(cellData -> cellData.getValue().isSelectedForEntry());
        selectingVehicles.setCellFactory(CheckBoxTableCell.forTableColumn(selectingVehicles));
        for(VehicleDto t : ctrl.getVehicleListPossibleForEntry(selectedEntry)){
            vehiclesToAssignList.add(t);
            t.setSelecting(false);
        }
        vehiclesToAssign.setItems(vehiclesToAssignList);
    }

    @FXML
    public void assignVehicles(){
        try{
            getVehiclesToAssign();
            for(VehicleDto vehicleDto : vehiclesSelected){
                ctrl.assignVehicleToEntry(vehicleDto,selectedEntry);
                popUp("Vehicles assigned!").show();
            }
        } catch (Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
