package pt.ipp.isep.dei.esoft.project.ui.gui.collaborator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;


import java.io.IOException;


public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();
    public RegisterCollaboratorController ctrl;
    public ViewDetailsCollaboratorUI viewDetailsCollaboratorUI;
    public AuthenticationController ctrlAuth;

    @FXML
    public TableView<Collaborator> tableCollaborators;

    @FXML
    public TableColumn<Collaborator, Integer> columnIDNumber;
    @FXML
    public TableColumn<Collaborator, DocType> columnDocType;
    @FXML
    public TableColumn<Collaborator, String> columnName;
    @FXML
    public TableColumn<Collaborator, JobCategory> columnJobCategory;
    @FXML
    public TableColumn<Collaborator, Void> columnButtonsDetails;
    @FXML
    public Label labelRole;

    ObservableList<Collaborator> collaboratorObservableList=FXCollections.observableArrayList();
    Collaborator selectedCollaboratorForEdit;
    public ManageCollaboratorsUI() {
        ctrl = RegisterCollaboratorController.getInstance();
        ctrlAuth= AuthenticationController.getInstance();
    }

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
            labelRole.setLayoutX(28.0);
            labelRole.setLayoutY(130.0);
        }
    }


    public Stage getStageToViewDetails(){
        return stageToViewDetails;
    }

    @FXML
    public void btnAddCollaborator(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ViewDetailsCollaboratorUI uiToAdd=fxmlLoader.getController();
        uiToAdd.setStageToAdd(stage);
        uiToAdd.setTableAssignSkills();
        uiToAdd.setComboBoxes();
        uiToAdd.setBtnEditCollaboratorValue(false);
    }

    @FXML
    public void btnRemoveCollaborator() {
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        if (selectedCollaborator != null) {
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Removing Collaborator");
            popUp.setContentText("Do you want to remove this collaborator?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableCollaborators.getItems().remove(selectedCollaborator);
                ctrl.removeFromList(selectedCollaborator);
            }
        } else {
            popUpOfVerifications(Alert.AlertType.ERROR, "Select a Collaborator to remove.").show();
        }
    }

    @FXML
    public void btnEditCollaborator() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        ViewDetailsCollaboratorUI uiToAdd=fxmlLoader.getController();
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        try{
            stage.show();
            uiToAdd.setTableAssignSkills();
            uiToAdd.setComboBoxes();
            uiToAdd.putInTextFields(selectedCollaborator);
            uiToAdd.showCollaboratorSelected(selectedCollaboratorForEdit);
            uiToAdd.setBtnAddCollaboratorValue(false);
        } catch (NullPointerException e){
            popUpOfVerifications(Alert.AlertType.ERROR, "Select a Collaborator to edit.").show();
        }
    }



    public void setTableCollaborators() {
        setLabel();

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnIDNumber.setCellValueFactory(new PropertyValueFactory<>("docIDNumber"));
        columnDocType.setCellValueFactory(new PropertyValueFactory<>("docType"));
        columnJobCategory.setCellValueFactory(new PropertyValueFactory<>("jobCategory"));

        columnButtonsDetails.setCellFactory(new Callback<
                TableColumn<Collaborator, Void>, TableCell<Collaborator, Void>>() {
            @Override
            public TableCell<Collaborator, Void> call(TableColumn<Collaborator, Void> param) {
                return new TableCell<Collaborator, Void>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            selectedCollaboratorForEdit = tableCollaborators.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
                            try {
                                showMore(selectedCollaboratorForEdit);
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
        for(Collaborator c : ctrl.getCollaboratorList()){
            collaboratorObservableList.add(c);
        }
        tableCollaborators.setItems(collaboratorObservableList);
    }

    public void showMore(Collaborator collab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsCollaboratorUI ui=fxmlLoader.getController();
        ui.setComboBoxes();
        ui.putInTextFields(collab);
        ui.showCollaboratorSelected(selectedCollaboratorForEdit);
        ui.setTableAssignSkills();
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

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }

    @FXML
    public void btnUpdate(ActionEvent event){
        tableCollaborators.getItems().clear();
        setTableCollaborators();
    }
}