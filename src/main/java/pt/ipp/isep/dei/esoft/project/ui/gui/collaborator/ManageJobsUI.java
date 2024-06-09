package pt.ipp.isep.dei.esoft.project.ui.gui.collaborator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.collaboratorSystem.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;

/**
 * UI class responsible for managing job categories.
 */
public class ManageJobsUI {
    /**
     * Main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * FXML elements for the scene
     */
    @FXML
    public TextField introducingJobCategory;
    @FXML
    public TableView<JobCategory> tableJobCategory;
    @FXML
    public TableColumn<JobCategory, String> colJobCategory;
    @FXML
    public Label labelRole;

    /**
     * Controller for registering job categories.
     */
    public RegisterJobCategoryController ctrl;

    /**
     * Controller for user authentication.
     */
    public AuthenticationController ctrlAuth;

    /**
     * Constructor for ManageJobsUI.
     */
    public ManageJobsUI() {
        ctrl = RegisterJobCategoryController.getInstance();
        ctrlAuth = AuthenticationController.getInstance();
    }

    /**
     * Set the label for the user role.
     */
    public void setLabel() {
        UserRoleDTO role = ctrlAuth.getAtualUserRole();
        if (role.getDescription().equals(RegisterController.ROLE_HRM)) {
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
     * Set up the job category table.
     */
    public void setJobCategoryTable() {
        setLabel();
        colJobCategory.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        tableJobCategory.getItems().clear();
        for (JobCategory jc : ctrl.getJobCategoriesList()) {
            tableJobCategory.getItems().add(jc);
        }
        tableJobCategory.setOnMouseClicked(mouseEvent -> putInTextField());
    }

    /**
     * Handle the action event for adding a job category.
     */
    @FXML
    public void btnAdd() {
        String jobCategory = introducingJobCategory.getText();
        if (jobCategory.isEmpty()) {
            popUpOfVerifications(Alert.AlertType.ERROR, "The Job Category is Empty").show();
            return;
        } else {
            try {
                ctrl.registerJobCategory(jobCategory);
            } catch (NullPointerException | CloneNotSupportedException e) {
                popUpOfVerifications(Alert.AlertType.ERROR, "The Job Category already exists.").show();
            } catch (IllegalArgumentException e) {
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        introducingJobCategory.clear();
        ObservableList<JobCategory> listForTable = FXCollections.observableArrayList(ctrl.getJobCategoriesList());
        tableJobCategory.getItems().clear();
        tableJobCategory.setItems(listForTable);
    }

    /**
     * Handle the action event for removing a job category.
     */
    @FXML
    public void btnRemove() {
        JobCategory selectedJobCategory = tableJobCategory.getSelectionModel().getSelectedItem();
        if (selectedJobCategory != null) {
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Removing Job Category");
            popUp.setContentText("Do you want to remove this Job Category?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                try {
                    tableJobCategory.getItems().remove(selectedJobCategory);
                    ctrl.removeJobCategory(selectedJobCategory);
                } catch (RuntimeException e) {
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }

        }
    }

    /**
     * Handle the action event for editing a job category.
     */
    @FXML
    public void btnEdit() {
        JobCategory editedJobCategory = tableJobCategory.getSelectionModel().getSelectedItem();
        if (editedJobCategory != null) {
            String newJobCategory = introducingJobCategory.getText();
            editedJobCategory.setName(newJobCategory);
            introducingJobCategory.clear();
            tableJobCategory.refresh();
        }
    }

    /**
     * Put the selected job category in the text field.
     */
    private void putInTextField() {
        JobCategory selectedJobCategory = tableJobCategory.getSelectionModel().getSelectedItem();
        String editJobCategory = selectedJobCategory.getName();
        introducingJobCategory.setText(editJobCategory);
    }

    /**
     * Create a pop-up alert for verifications.
     *
     * @param alertType Type of alert.
     * @param message   Message to display.
     * @return Alert object.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }

    /**
     * Handle the action event for logging out.
     *
     * @param event Action event.
     * @throws IOException If an I/O error occurs.
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
     * Handle the action event for going back.
     *
     * @param event Action event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
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
}
