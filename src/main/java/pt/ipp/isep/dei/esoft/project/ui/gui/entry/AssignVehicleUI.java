package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;

import java.util.ArrayList;
import java.util.List;

public class AssignVehicleUI {
    @FXML
    private TableView<VehicleDto> vehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto,String> platesOfVehiclesToAssign;
    @FXML
    private TableColumn<VehicleDto, Boolean> selectingVehicles;
    private ObservableList<VehicleDto> vehiclesToAssignList= FXCollections.observableArrayList();
    private List<VehicleDto> vehiclesSelected=new ArrayList<>();
    private EntryDto selectedEntry;

    private ViewDetailsEntryController ctrl;

    public void setEntry(EntryDto entry){
        ctrl= ViewDetailsEntryController.getInstance();
        selectedEntry=entry;
        initializeUI();
    }

    public void initializeUI(){
        platesOfVehiclesToAssign.setCellValueFactory(new PropertyValueFactory<>("plate"));
        selectingVehicles.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(false));
        selectingVehicles.setCellFactory(new Callback<>() {
            @Override
            public TableCell<VehicleDto, Boolean> call(TableColumn<VehicleDto, Boolean> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setGraphic(null);
                            return;
                        }
                        setGraphic(checkBox);
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            VehicleDto vehicle = (VehicleDto) getTableRow().getItem();
                            if (isNowSelected) {
                                vehiclesSelected.add(vehicle);
                            }
                        });
                    }
                };
            }
        });

        for(VehicleDto t : ctrl.getVehicleListPossibleForEntry(selectedEntry)){
            vehiclesToAssignList.add(t);
        }
        vehiclesToAssign.setItems(vehiclesToAssignList);
    }

    @FXML
    public void assignVehicles(){
        //getVehiclesToAssign();
        try{
            for(VehicleDto vehicleDto : vehiclesSelected){
                ctrl.assignVehicleToEntry(vehicleDto,selectedEntry);
            }
            if(popUp("Vehicles assigned!").showAndWait().get()==ButtonType.OK){
                Stage stage= (Stage) vehiclesToAssign.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e){
            e.printStackTrace();
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
