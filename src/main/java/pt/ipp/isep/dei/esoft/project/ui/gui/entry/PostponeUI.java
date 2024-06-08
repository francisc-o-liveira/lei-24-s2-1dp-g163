package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

/**
 * UI Controller class for postponing an entry.
 */
public class PostponeUI {
    @FXML
    private DatePicker postponedDate;
    @FXML
    private TextField postponeTime;
    /** Vriable of the selected entry to postpone */
    private EntryDto selectedEntry;
    /** Controller */
    private ViewDetailsEntryController ctrl;

    /**
     * Sets the entry to be postponed.
     *
     * @param entry The entry to be postponed.
     */
    public void setEntry(EntryDto entry){
        ctrl= ViewDetailsEntryController.getInstance();
        selectedEntry=entry;
    }

    /**
     * Handles the action of postponing the entry.
     */
    @FXML
    public void postponingEntry(){
        String timeToPostpone=postponeTime.getText();
        try{
            Date postpone=new Date(postponedDate.getValue().getYear(), postponedDate.getValue().getMonthValue(), postponedDate.getValue().getDayOfMonth());
            Tempo timePostponed=getTimeForEntry(timeToPostpone);
            ctrl.postponeEntry(selectedEntry,postpone,timePostponed);
            if(popUp("Entry postponed").showAndWait().get()==ButtonType.OK){
                Stage stage = (Stage) postponedDate.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
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
     * Creates a popup alert with the given message.
     *
     * @param message The message to display in the popup.
     * @return The created Alert object.
     */
    private Alert popUp(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);

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
