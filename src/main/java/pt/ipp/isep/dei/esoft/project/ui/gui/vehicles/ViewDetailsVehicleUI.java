package pt.ipp.isep.dei.esoft.project.ui.gui.vehicles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.RegisterCheckUpController;
import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

public class ViewDetailsVehicleUI {

    public RegisterVehicleController ctrl;
    public RegisterCheckUpController ctrlCheck;

    private static Vehicle selectedVehicle;

    private String vBrand;
    private String vModel;
    private String vPlate;
    private int vTare;
    private double vGrossWeight;
    private double vCurrentKm;
    private double vFrequencyCheck;
    private Date vRegister;
    private Date vAcquisition;
    private Vehicle.Type vType;
    private Date vlastDateCheck;
    private double vlastCheckKm;
    private double updateMaintenance;

    @FXML
    private DatePicker acquisitionDate;

    @FXML
    private TextField brand;

    @FXML
    private TextField checkupFrequency;

    @FXML
    private TextField currentKms;

    @FXML
    private TextField grossWeight;

    @FXML
    private TextField model;

    @FXML
    private TextField plate;

    @FXML
    private DatePicker registerDate;

    @FXML
    private TextField tare;

    @FXML
    private ComboBox<Vehicle.Type> type;

    @FXML
    private DatePicker checkDate;

    @FXML
    private TextField checkUpKMs;
    @FXML
    private TextField updateCurrentKm;

    @FXML
    private DatePicker updateDate;

    @FXML
    private TableColumn<CheckUp, Double> colCheckKm;

    @FXML
    private TableColumn<CheckUp, Date> colDateCheck;
    @FXML
    private TableView<CheckUp> tableCheckUp;
    @FXML
    private CheckBox maintenanceCheckBox;
    @FXML
    private  TextField maintenanceUp;
    @FXML
    private Label newMaintenanceCheck;

    @FXML
    private Button btnAddVehicle;
    @FXML
    private Button btnEditVehicle;

    @FXML
    private TextField lastCheckUp;
    @FXML
    private DatePicker lastDateCheckUp;

    private ObservableList<CheckUp> checkUpObservableList=FXCollections.observableArrayList();


    public ViewDetailsVehicleUI(){
        ctrl =  RegisterVehicleController.getInstance();
        ctrlCheck= RegisterCheckUpController.getInstance();
    }

    public void setComboBox(){
        type.setItems(FXCollections.observableArrayList(Vehicle.Type.values()));
    }

    public void setSelectedVehicle(Vehicle selectedVehicle){
        ViewDetailsVehicleUI.selectedVehicle =selectedVehicle;
    }

    public void setBtnAddVehicleToVisibleOrNot(boolean value){
        btnAddVehicle.setVisible(value);
    }

    public void setBtnEditVehicleToVisileOrNot(boolean value){
        btnEditVehicle.setVisible(value);
    }

    public void putInTextFields(Vehicle selectedVehicle){
        String editedBrand=selectedVehicle.getBrand();
        brand.setText(editedBrand);

        String editedModel=selectedVehicle.getModel();
        model.setText(editedModel);

        String editedPlate=selectedVehicle.getPlate();
        plate.setText(editedPlate);

        int editedTare=selectedVehicle.getTare();
        tare.setText(String.valueOf(editedTare));

        double editedGrossWeight=selectedVehicle.getGrossWeight();
        grossWeight.setText(String.valueOf(editedGrossWeight));

        double editedCurrentKm= selectedVehicle.getCurrentKm();
        currentKms.setText(String.valueOf(editedCurrentKm));

        double editedFrequencyCheckKm= selectedVehicle.getFrequencyCheckKm();
        checkupFrequency.setText(String.valueOf(editedFrequencyCheckKm));

        LocalDate editedDateRegister=selectedVehicle.getRegisterDateLocal();
        registerDate.setValue(editedDateRegister);

        LocalDate editedDateAcquisition=selectedVehicle.getAcquisitionDateLocal();
        acquisitionDate.setValue(editedDateAcquisition);

        Vehicle.Type typeOfVehicle = selectedVehicle.getType();
        type.setValue(typeOfVehicle);
    }

    public void setTable(Vehicle vehicle){
        colCheckKm.setCellValueFactory(new PropertyValueFactory<>("kmOfCheck"));
        colDateCheck.setCellValueFactory(new PropertyValueFactory<>("dateOfCheck"));
        checkUpObservableList.addAll(vehicle.getCheckUpList());
        tableCheckUp.setItems(checkUpObservableList);
    }


    @FXML
    public void btnAdd(ActionEvent event) throws IOException {
        vBrand= brand.getText();
        vModel= model.getText();
        vPlate= plate.getText();
        vTare= Integer.parseInt(tare.getText());
        vGrossWeight= Double.parseDouble(grossWeight.getText());
        vCurrentKm=Double.parseDouble(currentKms.getText());
        vFrequencyCheck=Double.parseDouble(checkupFrequency.getText());
        vRegister=new Date(registerDate.getValue().getYear(),registerDate.getValue().getMonthValue(),registerDate.getValue().getDayOfMonth());
        vAcquisition=new Date(acquisitionDate.getValue().getYear(), acquisitionDate.getValue().getMonthValue(), acquisitionDate.getValue().getDayOfMonth());
        vType=type.getValue();

        if(vBrand.isEmpty() || vModel.isEmpty() || vPlate.isEmpty() || vTare == 0 || vGrossWeight == 0.0 || vCurrentKm == 0.0 || vFrequencyCheck == 0.0){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Vehicle is empty").show();
        } else {
            if(vCurrentKm<10000){
                try{
                    ctrl.registerVehicle(vBrand,vModel,vAcquisition,vRegister,vCurrentKm,vFrequencyCheck,vGrossWeight,vTare,vPlate,vType, vRegister, 0);
                } catch (CloneNotSupportedException e){
                    popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                    if(!lastDateCheckUp.isVisible() && !lastCheckUp.isVisible()){
                    Alert alert=popUpOfVerifications(Alert.AlertType.ERROR, "Vehicle needs the last data of check-up");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");

                    Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            lastCheckUp.setVisible(true);
                            lastDateCheckUp.setVisible(true);
                            }
                    }else{
                        try{
                            vlastDateCheck=new Date(lastDateCheckUp.getValue().getYear(),lastDateCheckUp.getValue().getMonthValue(),lastDateCheckUp.getValue().getDayOfMonth());
                            vlastCheckKm=Double.parseDouble(lastCheckUp.getText());
                            ctrl.registerVehicle(vBrand,vModel,vAcquisition,vRegister,vCurrentKm,vFrequencyCheck,vGrossWeight,vTare,vPlate,vType,vlastDateCheck,vlastCheckKm);
                        } catch (CloneNotSupportedException e){
                            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
                        }
                    }
            }
        }
    }

    @FXML
    public void btnRegisterCheck(ActionEvent event){
        vlastDateCheck=new Date(checkDate.getValue().getYear(),checkDate.getValue().getMonthValue(),checkDate.getValue().getDayOfMonth());
        vlastCheckKm=Double.parseDouble(checkUpKMs.getText());
        if(maintenanceUp.isVisible()){
            updateMaintenance=Double.parseDouble(maintenanceUp.getText());
        } else {
            updateMaintenance=0;
        }
        if(vlastCheckKm<=0 || checkDate.getValue()==null){
            popUpOfVerifications(Alert.AlertType.ERROR, "The data is incorrect").show();
        } else {
            try{
                Optional<Object> opt =ctrlCheck.addCheckUp(selectedVehicle,vlastDateCheck,vlastCheckKm,updateMaintenance);
                if(opt.isPresent()){
                    setTable(this.selectedVehicle);
                    popUpOfConfirmation(Alert.AlertType.CONFIRMATION,"Check up successful registed").show();
                }else {
                    popUpOfVerifications(Alert.AlertType.INFORMATION,"Check up failed to register").show();
                }

            } catch (RejectedExecutionException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnEdit(ActionEvent event) {
        if(selectedVehicle != null){
            String newBrand=brand.getText();
            selectedVehicle.setBrand(newBrand);
            brand.clear();

            String newModel=model.getText();
            selectedVehicle.setModel(newModel);
            model.setText(selectedVehicle.getModel());

            String newPlate=plate.getText();
            selectedVehicle.setPlate(newPlate);
            plate.setText(selectedVehicle.getPlate());

            String newTare= tare.getText();
            int nTare=Integer.parseInt(newTare);
            selectedVehicle.setTare(nTare);
            tare.setText(Integer.toString(selectedVehicle.getTare()));

            String newGW=grossWeight.getText();
            double nGW=Double.parseDouble(newGW);
            selectedVehicle.setGrossWeight(nGW);
            grossWeight.setText(Double.toString(selectedVehicle.getGrossWeight()));

            String newCurrent=currentKms.getText();
            double nCK=Double.parseDouble(newCurrent);
            selectedVehicle.setCurrentKm(nCK);
            currentKms.setText(Double.toString(selectedVehicle.getCurrentKm()));

            String newFreq=checkupFrequency.getText();
            double nF=Double.parseDouble(newFreq);
            selectedVehicle.setFrequencyCheckKm(nF);
            checkupFrequency.setText(Double.toString(selectedVehicle.getFrequencyCheckKm()));

            selectedVehicle.setRegisterDate(new Date(registerDate.getValue().getYear(),registerDate.getValue().getMonthValue(),registerDate.getValue().getDayOfMonth()));
            registerDate.setValue(selectedVehicle.getRegisterDateLocal());

            selectedVehicle.setAcquisitionDate(new Date(acquisitionDate.getValue().getYear(), acquisitionDate.getValue().getMonthValue(), acquisitionDate.getValue().getDayOfMonth()));
            acquisitionDate.setValue(selectedVehicle.getAcquisitionDateLocal());
        }
    }

    public void showSelectedVehicle(Vehicle selectedVehicle){
        this.selectedVehicle=selectedVehicle;
    }

    @FXML
    void btnRemoveCheckUp(ActionEvent event) {
        CheckUp selectedCheck = tableCheckUp.getSelectionModel().getSelectedItem();
        if (selectedCheck != null) {
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);
            popUp.setHeaderText("Removing Check Up");
            popUp.setContentText("Do you want to remove this Check-Up?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableCheckUp.getItems().remove(selectedCheck);
                ctrlCheck.removeFromList(selectedVehicle,selectedCheck);
            }
        }
    }

    @FXML
    void addCheckUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_RegisterCheckVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showMaintenance(ActionEvent event){
        if(maintenanceCheckBox.isSelected()){
            maintenanceUp.setVisible(true);
            newMaintenanceCheck.setVisible(true);
        } else {
            maintenanceUp.setVisible(false);
            newMaintenanceCheck.setVisible(false);
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);
        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);
        return alerta;
    }

    private Alert popUpOfConfirmation(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);
        alerta.setTitle("Confirmation");
        alerta.setHeaderText("Correct Data");
        alerta.setContentText(messages);
        return alerta;
    }



    @FXML
    public void submitDataUpdate(ActionEvent event){
        try {
            if (ctrl.updateKm(selectedVehicle, Double.parseDouble(updateCurrentKm.getText()))) {
                popUpOfConfirmation(Alert.AlertType.CONFIRMATION, "Current Kilometers Update Successfully").show();
            } else {
                popUpOfVerifications(Alert.AlertType.ERROR, "Current Kilometers Update Failed").show();
            }
        }catch (IllegalArgumentException | NullPointerException e) {
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
