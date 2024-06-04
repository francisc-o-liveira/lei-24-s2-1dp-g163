package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class PostponeUI {
    @FXML
    private DatePicker postponedDate;
    private EntryDto selectedEntry;

    private ViewDetailsEntryController ctrl;

    public void setEntry(EntryDto entry){
        ctrl= ViewDetailsEntryController.getInstance();
        selectedEntry=entry;
    }

    @FXML
    public void postponingEntry(){
        try{
            Date postpone=new Date(postponedDate.getValue().getYear(), postponedDate.getValue().getMonthValue(), postponedDate.getValue().getDayOfMonth());
            ctrl.postponeEntry(selectedEntry,postpone);
            if(popUp("Entry postponed").showAndWait().get()==ButtonType.OK){
                Stage stage = (Stage) postponedDate.getScene().getWindow();
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
