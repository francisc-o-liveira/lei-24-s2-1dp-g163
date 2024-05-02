package pt.ipp.isep.dei.esoft.project.ui.gui;

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
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class ManageJobsUI {
    public Stage stage = LoginUI.getMainStage();
    @FXML
    public TextField introducingJobCategory;

    @FXML
    public TableView<JobCategory> tableJobCategory;

    @FXML
    public TableColumn<JobCategory, String> colJobCategory;

    public RegisterJobCategoryController ctrl;

    public AuthenticationController ctrlAuth;

    public ManageJobsUI(){
        ctrl=new RegisterJobCategoryController();
        ctrlAuth=new AuthenticationController();
    }

    public void setJobCategoryTable(){
        colJobCategory.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        tableJobCategory.getItems().clear();
        for(JobCategory jc : ctrl.getJobCategoriesList()){
            tableJobCategory.getItems().add(jc);
        }
        tableJobCategory.setOnMouseClicked(mouseEvent -> putInTextField());
    }

    @FXML
    public void btnAdd(){
        String jobCategory = introducingJobCategory.getText();
        if (jobCategory.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Job Category is Empty").show();
            return;
        } else {
            try {
                ctrl.registerJobCategory(jobCategory);
            }catch (NullPointerException | CloneNotSupportedException e){
                popUpOfVerifications(Alert.AlertType.ERROR, "The Job Category already exists.").show();
            } catch (IllegalArgumentException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        introducingJobCategory.clear();
        ObservableList<JobCategory> listForTable= FXCollections.observableArrayList(ctrl.getJobCategoriesList());
        tableJobCategory.getItems().clear();
        tableJobCategory.setItems(listForTable);
    }

    @FXML
    public void btnRemove(){
        JobCategory selectedJobCategory = tableJobCategory.getSelectionModel().getSelectedItem();
        if(selectedJobCategory != null){
            Alert popUp= new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Removing Job Category");
            popUp.setContentText("Do you want to remove this Job Category?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                try{
                tableJobCategory.getItems().remove(selectedJobCategory);
                ctrl.removeJobCategory(selectedJobCategory);
                } catch (RuntimeException e){
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }

        }
    }

    @FXML
    public void btnEdit(){
        JobCategory editedJobCategory = tableJobCategory.getSelectionModel().getSelectedItem();
        if (editedJobCategory != null) {
            String newJobCategory = introducingJobCategory.getText();
            editedJobCategory.setName(newJobCategory);
            introducingJobCategory.clear();
            tableJobCategory.refresh();
        }
    }



    private void putInTextField(){
        JobCategory selectedJobCategory=tableJobCategory.getSelectionModel().getSelectedItem();
        String editJobCategory = selectedJobCategory.getName();
        introducingJobCategory.setText(editJobCategory);
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
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

    @FXML
    public void goBack(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.equals(ctrlAuth.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.equals(ctrlAuth.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
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
