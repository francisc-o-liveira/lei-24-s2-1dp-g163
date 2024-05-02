package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

public class Vehicle {
    private String brand;
    private String model;
    public enum Type {LigeiroPassageiros,LigeiroCarga,Pesado,Motociclo}
    private Type type;
    private int tare;
    private String plate;
    private double grossWeight;
    private double currentKm;
    private Date registerDate;
    private Date acquisitionDate;
    private double frequencyCheckKm;
    private List<CheckUp> checkUpList;
    public enum StatusType {Use,NotUse}
    private StatusType statusType;
    private static final double TAX_FOR_CLOSE_CHECK=0.05;


    public Vehicle(String brand, String model, Type type, int tare, double grossWeight, double currentKm, Date registerDate, Date acquisionDate, double frequencyCheckKm, String plate, Date date, double lastKmCheck){
        setBrand(brand);
        setModel(model);
        setType(type);
        setTare(tare);
        setGrossWeight(grossWeight);
        setCurrentKm(currentKm);
        setRegisterDate(registerDate);
        setAcquisitionDate(acquisionDate);
        setFrequencyCheckKm(frequencyCheckKm);
        setStatusType(StatusType.NotUse);
        setPlate(plate);
        setLastCheckUp(date,lastKmCheck);
    }

    private void setLastCheckUp(Date date, double lastKmCheck) {
        checkUpList=new ArrayList<>();
        if (currentKm<10000 && lastKmCheck==0 && date.compareTo(registerDate)==0){
            CheckUp defaultCheck = new CheckUp(lastKmCheck,date);
            checkUpList.add(defaultCheck);
        } else if (currentKm < 10000) {
            throw new IllegalArgumentException("The last check up need to be default: Fatal Error UI");
        }else {
            if (lastKmCheck<currentKm && lastKmCheck>0 && date.compareTo(registerDate)>0){
                CheckUp lastCheck = new CheckUp(lastKmCheck,date);
                checkUpList.add(lastCheck);
            }else {
                throw new IllegalArgumentException("The last check up is incorrect to register vehicle");
            }

        }
    }


    private boolean verifyPlate(String plate) {
        //IMPLEMENTATION OF VERIFY PLATE NEED TO BE DONE IN WHERE
        return true;
    }

    public StatusType getStatus() {
        return statusType;
    }

    public boolean isCloseToCheck(){
         return (frequencyCheckKm*TAX_FOR_CLOSE_CHECK) >= getKmCloseToCheck();
    }
    public double getKmCloseToCheck() {
        return (this.getLastCheckUpKm()  + frequencyCheckKm) - currentKm;
    }

    public Optional<CheckUp> registerCheckUp(double currentKmOfCheck, Date dateOfCheck){
        Optional <CheckUp> newCheck = Optional.empty();
        boolean operationSucess=false;
        if(currentKmOfCheck>=currentKm && currentKmOfCheck>getLastCheckUpKm() && Date.atualDate().diference(dateOfCheck)<30){
            CheckUp regist = new CheckUp(currentKmOfCheck,dateOfCheck);
            newCheck = Optional.of(regist);
            operationSucess=this.checkUpList.add(regist);
        }
        if(!operationSucess){
            throw new RejectedExecutionException("The check up could not be registered because of data(current Kilometers of check up and date of check up)");
        }
        return newCheck;
    }

    private double getLastCheckUpKm() {
        double save = 0;
        for(CheckUp c : checkUpList){
            save = c.getKmOfCheck();
        }
        return save;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public List<CheckUp> getCheckUpList() {
        return checkUpList;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public Date getacquisitionnDate() {
        return acquisitionDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getCurrentKm() {
        return currentKm;
    }

    public double getFrequencyCheckKm() {
        return frequencyCheckKm;
    }


    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
    public String getPlate() {
        return plate;
    }

    public Type getType() {
        return type;
    }


    @Override
    public boolean equals(Object obj) {
            return this.plate == ((Vehicle) obj).plate;
    }

    @Override
    public String toString(){
        return String.format("Vehicle:\n" +
                        "Brand: %s\n" +
                        "Model: %s\n" +
                        "Type: %s\n" +
                        "Tare: %d\n" +
                        "Plate: %s\n" +
                        "Gross Weight: %.2f\n" +
                        "Current Km: %d\n" +
                        "Register Date: %s\n" +
                        "Acquisition Date: %s\n" +
                        "Frequency Check Km: %d\n" +
                        "Check Up List: %s\n" +
                        "Status Type: %s\n",
                brand, model, type, tare, plate, grossWeight, currentKm, registerDate, acquisitionDate, frequencyCheckKm, checkUpList, statusType);
    }

    public void setBrand(String brand) {
        if (verifyModelAndBrand(brand)){
            this.brand = brand;
        }else {
             throw new IllegalArgumentException("Invalid brand");
        }
    }

    public void setCurrentKm(double currentKm) {
        if (currentKm>0){
            this.currentKm = currentKm;
        }else {
            throw new IllegalArgumentException("Invalid current km");
        }
    }

    public void setModel(String model) {
        if(verifyModelAndBrand(model)){
            this.model = model;
        }else{
            throw new IllegalArgumentException("Invalid model name");
        }
    }

    private boolean verifyModelAndBrand(String model) {
        return model.split(" ").length<=3;
    }

    public void setPlate(String plate) {
        if (verifyPlate(plate)){
            this.plate=plate;
        }else {
            throw new RejectedExecutionException("Plate dont correspont with the Acquisition Date ");
        }
    }

    public void setFrequencyCheckKm(double frequencyCheckKm) {
        if (frequencyCheckKm >= 1000){
            this.frequencyCheckKm = frequencyCheckKm;
        }else {
            throw new IllegalArgumentException("Frequency Check Km should be greater than 1000");
        }
    }
    public void setTare(int tare) {
        this.tare = tare;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        if (acquisitionDate.getYear()>1900) {
            this.acquisitionDate = acquisitionDate;
        }else {
            throw new IllegalArgumentException("Acquisition date cannot be less than 1900");
        }

    }

    public void setGrossWeight(double grossWeight) {
        if (grossWeight>tare){
            this.grossWeight = grossWeight;
        }else {
            throw new IllegalArgumentException("Gross Weight cannot be less than tare");
        }
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
