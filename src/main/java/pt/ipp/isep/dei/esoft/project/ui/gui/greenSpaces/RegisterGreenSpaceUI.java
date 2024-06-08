package pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI Controller class for registering a green space in the GUI.
 */
public class RegisterGreenSpaceUI implements Initializable {

    /** The controller for registering green spaces. */
    public RegisterGreenSpaceController ctrl;

    /** The stage for the UI. */
    public Stage stage;

    /** Text field for the name of the green space. */
    @FXML
    private TextField nameGreenSpace;

    /** Text field for the address of the green space. */
    @FXML
    private TextField addressGreenSpace;

    /** Text field for the city of the green space address. */
    @FXML
    private TextField addressCityGreenSpace;

    /** Text field for the zip code of the green space address. */
    @FXML
    private TextField addressZipCodeGreenSpace;

    /** Text field for the area of the green space. */
    @FXML
    private TextField areaGreenSpace;

    /** Combo box for selecting the type of green space. */
    @FXML
    private ComboBox<GreenSpace.Type> greenSpaceType;


    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rbl The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rbl){
        try {
            ctrl= RegisterGreenSpaceController.getInstance();
            greenSpaceType.setItems(FXCollections.observableArrayList(ctrl.getEnumGreenSpaceType()));
        }catch (ClassNotFoundException | IOException | IllegalAccessException e){
            popUpOfVerifications(Alert.AlertType.ERROR,"Error: Impossible to Access to Sort Algorithm" + e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the stage for the UI.
     *
     * @param stage The stage for the UI.
     */
    public void setStage(Stage stage){
        this.stage=stage;
    }

    /**
     * Handles the action when the register button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void btnRegister(javafx.event.ActionEvent event){
        String name = nameGreenSpace.getText();
        String address = addressGreenSpace.getText();
        String area = areaGreenSpace.getText();
        String addressCity = addressCityGreenSpace.getText();
        String addressZipCode = addressZipCodeGreenSpace.getText();
        GreenSpace.Type typeOfGreenSpace = greenSpaceType.getValue();
        if(name.isEmpty() || address.isEmpty() || area.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Green Space is empty").show();
        } else {
            try{
                double areaRegistering=Double.parseDouble(area);
                ctrl.registerGreenSpace(name,address,addressCity,addressZipCode,areaRegistering,typeOfGreenSpace);
                if(popUp().showAndWait().get()==ButtonType.OK){
                    stage.close();
                }
            } catch (Exception e){
                popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    /**
     * Creates and returns a confirmation pop-up alert.
     *
     * @return The confirmation pop-up alert.
     */
    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Green Space added!");
        return alert;
    }

    /**
     * Creates and returns a pop-up alert for displaying error messages.
     *
     * @param alertType The type of the alert.
     * @param messages The message to be displayed.
     * @return The pop-up alert.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

}
