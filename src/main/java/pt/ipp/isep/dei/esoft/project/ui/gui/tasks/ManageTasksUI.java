package pt.ipp.isep.dei.esoft.project.ui.gui.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.DetailsEntryAgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterTaskController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageTasksUI implements Initializable {

    public AuthenticationController ctrlAuth;
    private RegisterTaskController ctrl;
    public Stage stage = LoginUI.getMainStage();
    private ObservableList<EntryDto> taskObservableList= FXCollections.observableArrayList();
    private EntryDto selectedTask;

    @FXML
    private TableColumn<EntryDto, String> degreeUrgencyCol;

    @FXML
    private TableColumn<EntryDto, Integer> durationCol;

    @FXML
    private TableColumn<EntryDto, String> parkCol;

    @FXML
    private TableColumn<EntryDto, String> taskCol;

    @FXML
    private TableColumn<EntryDto, Void> detailsCol;

    @FXML
    private TableView<EntryDto> tableToDoList;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ctrlAuth=new AuthenticationController();
        ctrl=new RegisterTaskController();
        setTableToDoList();
    }

    public void setTableToDoList(){
        degreeUrgencyCol.setCellValueFactory(new PropertyValueFactory<>("degreeUrgency"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("expectedDuration"));
        parkCol.setCellValueFactory(new PropertyValueFactory<>("park"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        detailsCol.setCellFactory(new Callback<
                TableColumn<EntryDto, Void>, TableCell<EntryDto, Void>>() {
            @Override
            public TableCell<EntryDto, Void> call(TableColumn<EntryDto, Void> param) {
                return new TableCell<EntryDto, Void>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            selectedTask = tableToDoList.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
                            try {
                                showMore(selectedTask);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        List<EntryDto> tasks=ctrl.getToDoList();
        for(EntryDto t : ctrl.getToDoList()){
            taskObservableList.add(t);
        }

        tableToDoList.setItems(taskObservableList);

    }

    public void showMore(EntryDto task) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_ViewDetailsTask.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stageViewDetails=new Stage();
        stageViewDetails.setScene(scene);
        ViewDetailsTaskUI ui=fxmlLoader.getController();
        ui.setLabels(task);
    }


    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_VFM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_GSM.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }

    @FXML
    public void btnRegister(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tasks/Scene_RegisterTask.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stageRegister= new Stage();
        stageRegister.setScene(scene);
        stageRegister.show();
        RegisterTaskUI ui=fxmlLoader.getController();
        ui.setStage(stageRegister);
    }

    @FXML
    public void btnUpdate(ActionEvent event){
        tableToDoList.getItems().clear();
        setTableToDoList();
    }

}
