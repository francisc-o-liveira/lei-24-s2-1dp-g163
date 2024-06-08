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
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterTaskController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class represents the UI for managing tasks within the system. It provides functionalities to view,
 * add, update, and logout from the tasks management interface.
 */
public class ManageTasksUI implements Initializable {

    /**
     * The authentication controller instance.
     */
    public AuthenticationController ctrlAuth;

    /**
     * The register task controller instance.
     */
    private RegisterTaskController ctrl;

    /**
     * The main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * Observable list of tasks for displaying in the table view.
     */
    private ObservableList<EntryDto> taskObservableList = FXCollections.observableArrayList();

    /**
     * The selected task.
     */
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

    @FXML
    public Label labelRole;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrlAuth = AuthenticationController.getInstance();
        ctrl = RegisterTaskController.getInstance();
        setTableToDoList();
        setLabel();
    }

    /**
     * Sets up the table view for tasks with task data.
     */
    public void setTableToDoList() {
        degreeUrgencyCol.setCellValueFactory(new PropertyValueFactory<>("degreeUrgency"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("expectedDuration"));
        parkCol.setCellValueFactory(new PropertyValueFactory<>("greenSpace"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        detailsCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<EntryDto, Void> call(TableColumn<EntryDto, Void> param) {
                return new TableCell<>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            selectedTask = tableToDoList.getItems().get(((TableCell) ((Button) event.getSource()).getParent()).getIndex());
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

        List<EntryDto> tasks = ctrl.getToDoList();
        for (EntryDto t : tasks) {
            taskObservableList.add(t);
        }

        tableToDoList.setItems(taskObservableList);
    }

    /**
     * Displays more details about the selected task.
     *
     * @param task the selected task
     * @throws IOException if an I/O error occurs
     */
    public void showMore(EntryDto task) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tasks/Scene_ViewDetailsTask.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stageViewDetails=new Stage();
        stageViewDetails.setScene(scene);
        stageViewDetails.show();
        ViewDetailsTaskUI ui=fxmlLoader.getController();
        ui.setLabels(task);
    }

    /**
     * Logs out the current user and navigates to the login screen.
     *
     * @param event the action event
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Navigates back to the main menu based on the user's role.
     *
     * @param event the action event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(RegisterController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(RegisterController.ROLE_VFM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_Admin.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    /**
     * Sets the role label based on the user's role.
     */
    public void setLabel(){
        UserRoleDTO role = ctrlAuth.getAtualUserRole();
        if (role.getDescription().equals(RegisterController.ROLE_HRM)){
            labelRole.setText("HumanResourcesManager");
        } else if (role.getDescription().equals(RegisterController.ROLE_GSM)) {
            labelRole.setText("GreenSpaceManager");
            labelRole.setLayoutX(28.0);
            labelRole.setLayoutY(130.0);
        } else {
            labelRole.setText("Admin");
            labelRole.setLayoutX(69);
            labelRole.setLayoutY(130.0);
        }
    }

    /**
     * Creates and returns an alert with a specified message.
     *
     * @param alertType the type of the alert
     * @param message   the message to be displayed in the alert
     * @return the created alert
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }

    /**
     * Navigates to the task registration screen.
     *
     * @param event the action event
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Updates the task list in the table view.
     *
     * @param event the action event
     */
    @FXML
    public void btnUpdate(ActionEvent event){
        tableToDoList.getItems().clear();
        setTableToDoList();
    }

}
