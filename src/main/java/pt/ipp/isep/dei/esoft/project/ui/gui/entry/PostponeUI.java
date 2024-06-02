package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewDetailsEntryController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class PostponeUI {
    @FXML
    private DatePicker postponedDate;
    private EntryDto selectedEntry;

    private ViewDetailsEntryController ctrl;

    public void setEntry(EntryDto entry){
        ctrl=new ViewDetailsEntryController();
        selectedEntry=entry;
    }

    @FXML
    public void postponingEntry(){
        try{
            Date postpone=new Date(postponedDate.getValue().getYear(), postponedDate.getValue().getMonthValue(), postponedDate.getValue().getDayOfMonth());
            ctrl.postponeEntry(selectedEntry/*postpone*/);
            popUp("Entry postponed").show();
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
