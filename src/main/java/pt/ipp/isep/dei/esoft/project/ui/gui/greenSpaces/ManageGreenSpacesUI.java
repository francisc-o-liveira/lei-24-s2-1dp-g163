package pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces;

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
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageGreenSpacesUI implements Initializable {
    public AuthenticationController ctrlAuth;
    public RegisterGreenSpaceController ctrl;
    public Stage stage = LoginUI.getMainStage();

    @FXML
    private TableColumn<GreenSpaceDto, Address> colAddress;

    @FXML
    private TableColumn<GreenSpaceDto, Double> colArea;

    @FXML
    private TableColumn<GreenSpaceDto, String> colName;

    @FXML
    private TableColumn<GreenSpaceDto, Void> colDetails;

    @FXML
    private TableView<GreenSpaceDto> tableGreenSpaces;
    @FXML
    private Label labelRole;

    private GreenSpaceDto selectedGreenSpace;
    private ObservableList<GreenSpaceDto> greenSpaceObservableList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ctrlAuth= AuthenticationController.getInstance();
            ctrl=RegisterGreenSpaceController.getInstance();
            setTableGreenSpaces();
            setLabel();
        }catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        }

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
            labelRole.setLayoutX(69);
            labelRole.setLayoutY(130.0);
        }
    }

    public void setTableGreenSpaces(){
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("areaInHectares"));
        colDetails.setCellFactory(new Callback<
                TableColumn<GreenSpaceDto, Void>, TableCell<GreenSpaceDto, Void>>() {
            @Override
            public TableCell<GreenSpaceDto, Void> call(TableColumn<GreenSpaceDto, Void> param) {
                return new TableCell<GreenSpaceDto, Void>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            selectedGreenSpace = tableGreenSpaces.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
                            try {
                                showMore(selectedGreenSpace);
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
        for(GreenSpaceDto greenSpace : ctrl.getGreenSpacesByEmail()){
            greenSpaceObservableList.add(greenSpace);
        }

        tableGreenSpaces.setItems(greenSpaceObservableList);

    }

    public void showMore(GreenSpaceDto gs) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/green_spaces/Scene_ViewDetailsGreenSpace.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stageViewDetails=new Stage();
        stageViewDetails.setScene(scene);
        stageViewDetails.show();
        ViewDetailsGreenSpaceUI ui=fxmlLoader.getController();
        ui.setLabels(gs);
    }

    @FXML
    void btnRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/green_spaces/Scene_RegisterGreenSpace.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stageRegister= new Stage();
        stageRegister.setScene(scene);
        stageRegister.show();
        RegisterGreenSpaceUI ui=fxmlLoader.getController();
        ui.setStage(stageRegister);
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
    public void update(ActionEvent event){
        tableGreenSpaces.getItems().clear();
        setTableGreenSpaces();
    }
}
