package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.core.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.VehicleDto;

import java.util.ArrayList;
import java.util.List;

/**
 * UI controller class for assigning vehicles to an entry.
 * It handles the display and interaction with the vehicles available for assignment.
 */
public class AssignVehicleUI {

    /**
     * Table view for displaying vehicles available for assignment.
     */
    @FXML
    private TableView<VehicleDto> vehiclesToAssign;

    /**
     * Table column for displaying the plates of the vehicles.
     */
    @FXML
    private TableColumn<VehicleDto, String> platesOfVehiclesToAssign;

    /**
     * Table column for selecting vehicles via checkboxes.
     */
    @FXML
    private TableColumn<VehicleDto, Boolean> selectingVehicles;

    /**
     * Observable list of vehicles available for assignment.
     */
    private ObservableList<VehicleDto> vehiclesToAssignList = FXCollections.observableArrayList();

    /**
     * List of selected vehicles to be assigned to the entry.
     */
    private List<VehicleDto> vehiclesSelected = new ArrayList<>();

    /**
     * The selected entry for which vehicles are to be assigned.
     */
    private EntryDto selectedEntry;

    /**
     * Controller for viewing details of an entry.
     */
    private ViewDetailsEntryController ctrl;

    /**
     * Sets the entry for which vehicles are to be assigned and initializes the UI.
     *
     * @param entry The entry for which vehicles are to be assigned.
     */
    public void setEntry(EntryDto entry) {
        ctrl = ViewDetailsEntryController.getInstance();
        selectedEntry = entry;
        initializeUI();
    }

    /**
     * Initializes the UI by setting up the table columns and populating the vehicle list.
     */
    public void initializeUI() {
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

        for (VehicleDto t : ctrl.getVehicleListPossibleForEntry(selectedEntry)) {
            vehiclesToAssignList.add(t);
        }
        vehiclesToAssign.setItems(vehiclesToAssignList);
    }

    /**
     * Handles the action of assigning the selected vehicles to the entry.
     */
    @FXML
    public void assignVehicles() {
        try {
            for (VehicleDto vehicleDto : vehiclesSelected) {
                ctrl.assignVehicleToEntry(vehicleDto, selectedEntry);
            }
            if (popUp("Vehicles assigned!").showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) vehiclesToAssign.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
