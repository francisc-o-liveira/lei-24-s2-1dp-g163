package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.time.LocalDate;

/**
 * UI Controller class for viewing details of an entry in the agenda.
 */
public class ViewDetailsEntryAgendaUI {
    /** Controller */
    private ViewDetailsEntryController ctrl;
    /** Variable for the selected entry to view the details*/
    private EntryDto selectedEntry;
    /** The stage */
    public Stage stage;

    /** Labels for displaying entry details */
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

    /** Observable List for the TableView showing the vehicles assigned to the entry*/
    private ObservableList<VehicleDto> vehiclesList= FXCollections.observableArrayList();

    /**
     * Sets the labels with details of the selected entry.
     *
     * @param entry The selected entry.
     * @param stage The stage for displaying UI.
     */
    public void setLabels(EntryDto entry, Stage stage){
        ctrl= ViewDetailsEntryController.getInstance();
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

    /**
     * Handles the action of assigning vehicles to the entry.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    public void btnAssignVehicles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/entry/Scene_AssignVehicleEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AssignVehicleUI ui=fxmlLoader.getController();
        ui.setEntry(selectedEntry);
    }

    /**
     * Handles the action of assigning a team to the entry.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @FXML
    public void btnAssignTeam(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/entry/Scene_AssignTeamEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AssignTeamUI ui=fxmlLoader.getController();
        ui.setEntry(selectedEntry);
    }


    /**
     * Handles the action of canceling the entry.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an error occurs while handling the cancellation.
     */
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

    /**
     * Handles the action of postponing the entry.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an error occurs while postponing the entry.
     */
    @FXML
    public void btnPostpone(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/entry/Scene_PostponeEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        PostponeUI ui=fxmlLoader.getController();
        ui.setEntry(selectedEntry);
    }

    /**
     * Converts a custom Date object to Java LocalDate object.
     *
     * @param date The custom Date object.
     * @return The converted LocalDate object.
     */
    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

    /**
     * Creates a popup alert for displaying verification messages.
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
