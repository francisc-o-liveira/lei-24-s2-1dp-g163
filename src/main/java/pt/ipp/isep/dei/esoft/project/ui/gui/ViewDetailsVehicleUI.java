package pt.ipp.isep.dei.esoft.project.ui.gui;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.vehicle.RegisterVehicleUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ViewDetailsVehicleUI {

    public RegisterVehicleController ctrl;

    Vehicle selectedVehicle;

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
    private DatePicker lastDateCheck;
    @FXML
    private TextField lastCheckKm;

    @FXML
    private TableColumn<CheckUp, Double> colCheckKm;

    @FXML
    private TableColumn<CheckUp, Date> colDateCheck;
    @FXML
    private TableView<CheckUp> tableCheckUp;


    public ViewDetailsVehicleUI(){
        ctrl = new RegisterVehicleController();
    }

    public void setComboBox(){
        type.setItems(FXCollections.observableArrayList(Vehicle.Type.values()));
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

        //correct this to get the right information
        /*LocalDate editedDateCheck=selectedVehicle.getKmCloseToCheckLocal();
        lastDateCheck.setValue(editedDateCheck);*/
    }

    public void setTable(){
        colCheckKm.setCellValueFactory(new PropertyValueFactory<>("checkKm"));

        /*colDateCheck.setCellValueFactory(cellData -> {
            //SimpleObjectProperty<Date> property = new SimpleObjectProperty<>(cellData.getValue().getDateCheck());
            //return property;
        });*/
        /// ^^ correct this to display the right info

        colDateCheck.setCellFactory(column -> {
            return new TableCell<CheckUp, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
        });
    }


    @FXML
    public void btnAdd(ActionEvent event) {
        String vBrand= brand.getText();
        String vModel= model.getText();
        String vPlate= plate.getText();
        int vTare= Integer.parseInt(tare.getText());
        double vGrossWeight= Double.parseDouble(grossWeight.getText());
        double vCurrentKm=Double.parseDouble(currentKms.getText());
        double vFrequencyCheck=Double.parseDouble(checkupFrequency.getText());
        Date vRegister=new Date(registerDate.getValue().getYear(),registerDate.getValue().getMonthValue(),registerDate.getValue().getDayOfMonth());
        Date vAcquisition=new Date(acquisitionDate.getValue().getYear(), acquisitionDate.getValue().getMonthValue(), acquisitionDate.getValue().getDayOfMonth());
        Vehicle.Type vType=type.getValue();
        Date vlastDateCheck=new Date(lastDateCheck.getValue().getYear(),lastDateCheck.getValue().getMonthValue(),lastDateCheck.getValue().getDayOfMonth());
        double vlastCheckKm=Double.parseDouble(lastCheckKm.getText());

        if(vBrand.isEmpty() || vModel.isEmpty() || vPlate.isEmpty() || vTare == 0 || vGrossWeight == 0.0 || vCurrentKm == 0.0 || vFrequencyCheck == 0.0){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Vehicle is empty").show();
        } else {
            /*try{
                //ctrl.registerVehicle(vBrand,vModel,vAcquisition,vRegister,vCurrentKm,vFrequencyCheck,vGrossWeight,vTare,vPlate,vType,vlastDateCheck,vlastCheckKm);
            } catch (CloneNotSupportedException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }*/
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
            model.clear();

            String newPlate=plate.getText();
            selectedVehicle.setPlate(newPlate);
            plate.clear();

            String newTare= tare.getText();
            int nTare=Integer.parseInt(newTare);
            selectedVehicle.setTare(nTare);
            tare.clear();

            String newGW=grossWeight.getText();
            double nGW=Double.parseDouble(newGW);
            selectedVehicle.setGrossWeight(nGW);
            grossWeight.clear();

            String newCurrent=currentKms.getText();
            double nCK=Double.parseDouble(newCurrent);
            selectedVehicle.setCurrentKm(nCK);
            currentKms.clear();

            String newFreq=checkupFrequency.getText();
            double nF=Double.parseDouble(newFreq);
            selectedVehicle.setFrequencyCheckKm(nF);
            checkupFrequency.clear();

            selectedVehicle.setRegisterDate(new Date(registerDate.getValue().getYear(),registerDate.getValue().getMonthValue(),registerDate.getValue().getDayOfMonth()));
            registerDate.setValue(null);

            selectedVehicle.setAcquisitionDate(new Date(acquisitionDate.getValue().getYear(), acquisitionDate.getValue().getMonthValue(), acquisitionDate.getValue().getDayOfMonth()));
            acquisitionDate.setValue(null);

            //the other two fields need to be defined in here
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

            popUp.setHeaderText("Removing Collaborator");
            popUp.setContentText("Do you want to remove this collaborator?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableCheckUp.getItems().remove(selectedCheck);
                //ctrl.removeFromList(selectedCheck); -- need this method on the controller
            }
        }
    }

    @FXML
    void addCheckUp(ActionEvent event) {
        //??????????????????????
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
