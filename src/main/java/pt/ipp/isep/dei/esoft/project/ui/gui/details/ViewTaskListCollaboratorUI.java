package pt.ipp.isep.dei.esoft.project.ui.gui.details;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewTaskListCollaboratorUI {

    public Stage stage = LoginUI.getMainStage();

    public AuthenticationController ctrlAuth;
    private ObservableList<EntryDto> tasksOfCollab= FXCollections.observableArrayList();
    private List<EntryDto> entriesSelected;
    private List<EntryDto> tasksForCollab;
    @FXML
    private TableView<EntryDto> tableTasks;

    @FXML
    private TableColumn<EntryDto, Date> taskDate;

    @FXML
    private TableColumn<EntryDto, GreenSpaceDto> taskGreenSpace;

    @FXML
    private TableColumn<EntryDto, String> taskName;

    @FXML
    private TableColumn<EntryDto, Boolean> taskSelect;

    @FXML
    private TableColumn<EntryDto, EntryState> taskStatus;

    public ViewTaskListCollaboratorUI(){
        ctrlAuth=new AuthenticationController();
        entriesSelected =new ArrayList<>();
        tasksForCollab=new ArrayList<>();
    }

    public void setTableTasks(List<EntryDto> tasksForCollaborator){
        tasksForCollab=tasksForCollaborator;
        taskDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        taskGreenSpace.setCellValueFactory(new PropertyValueFactory<>("greenSpace"));
        taskName.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskSelect.setCellFactory(CheckBoxTableCell.forTableColumn(taskSelect));

        for(EntryDto entry : tasksForCollab){
            tasksOfCollab.add(entry);
            entry.setSelecting(false);
        }

        tableTasks.setItems(tasksOfCollab);
    }

    @FXML
    void btnComplete(ActionEvent event) {
        getTasksDone();

        for(EntryDto entry : entriesSelected){
            try{
                //in a controller, it passes the collaborator that is completing a task and
                // the list of tasks selected
                popUp().show();
            } catch (Exception e) {
                popUpOfVerifications(Alert.AlertType.ERROR, "s").show();
            }
        }
    }

    private void getTasksDone(){
        for (EntryDto entry : tasksForCollab) {
            if (entry.selectedEntry().get()) {
                entriesSelected.add(entry);
            }
        }
    }


    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp= new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root= fxmlLoader.load();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Tasks done!");

        return alert;
    }
}
