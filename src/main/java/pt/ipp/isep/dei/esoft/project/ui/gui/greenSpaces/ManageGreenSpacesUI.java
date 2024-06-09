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
import pt.ipp.isep.dei.esoft.project.core.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI Controller class for managing green spaces.
 */
public class ManageGreenSpacesUI implements Initializable {
    /**Controller for authentication */
    public AuthenticationController ctrlAuth;
    /** Controller */
    public RegisterGreenSpaceController ctrl;
    /** Stage */
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

    /** Varibale for the selected green space */
    private GreenSpaceDto selectedGreenSpace;
    /** Observable list for the TableView */
    private ObservableList<GreenSpaceDto> greenSpaceObservableList= FXCollections.observableArrayList();

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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

    /**
     * Sets the label based on the current user's role.
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
     * Sets up the table for displaying green spaces.
     */
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

    /**
     * Shows more details about a selected green space.
     *
     * @param gs The selected green space.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles the action when the register button is clicked.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles the action when the logout button is clicked.
     *
     * @param event The action event.
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
     * Handles the action when the go back button is clicked.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
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
     * Creates and returns a pop-up alert for displaying error messages.
     *
     * @param alertType The type of the alert.
     * @param message   The message to be displayed.
     * @return The pop-up alert.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }

    /**
     * Updates the table of green spaces with the latest data.
     *
     * @param event The action event.
     */
    @FXML
    public void update(ActionEvent event){
        tableGreenSpaces.getItems().clear();
        setTableGreenSpaces();
    }
}
