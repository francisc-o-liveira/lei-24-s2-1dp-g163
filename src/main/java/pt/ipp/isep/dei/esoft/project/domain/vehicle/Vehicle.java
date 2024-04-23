package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Optional;

public class Vehicle {
    private String brand;
    private String model;
    public enum Type {LigeiroPassageiros,LigeiroCarga,Pesado,Motociclo}
    private Type type;
    private int tare;
    private String plate;
    private double grossWeight;
    private int currentKm;
    private Date registerDate;
    private Date acquisionDate;
    private int frequencyCheckKm;
    private List<CheckUp> checkUpList;
    public enum StatusType {Use,NotUse}
    private StatusType statusType;


    public Vehicle(String brand, String model, Type type, int tare, double grossWeight, int currentKm, Date registerDate, Date acquisionDate, int frequencyCheckKm, String plate){
        this.brand=brand;
        this.model=model;
        this.type=type;
        this.tare=tare;
        this.grossWeight=grossWeight;
        this.currentKm=currentKm;
        this.registerDate=registerDate;
        this.acquisionDate=acquisionDate;
        this.frequencyCheckKm=frequencyCheckKm;
        this.plate=plate;
        this.statusType=StatusType.NotUse;
    }

    public StatusType getStatus() {
        return statusType;
    }

    public void setStatus(StatusType statusType) {
        this.statusType = statusType;
    }

    public boolean isCloseToCheck(){
         return (frequencyCheckKm*0.05) >= getKmCloseToCheck();
    }
    public int getKmCloseToCheck() {
        return (this.getLastCheckUpKm()  + frequencyCheckKm) - currentKm;
    }

    public Optional<CheckUp> registerCheckUp(int currentKmOfCheck, Date dateOfCheck){
        Optional <CheckUp> newCheck = Optional.empty();
        boolean operationSucess=false;
        if(currentKmOfCheck>=currentKm && currentKmOfCheck>getLastCheckUpKm() && Date.atualDate().diference(dateOfCheck)<30){
            CheckUp regist = new CheckUp(currentKmOfCheck,dateOfCheck);
            newCheck = Optional.of(regist);
            operationSucess=this.checkUpList.add(regist);
        }
        if(!operationSucess){
            newCheck=Optional.empty();
        }
        return newCheck;
    }

    private int getLastCheckUpKm() {
        int save = 0;
        for(CheckUp c : checkUpList){
            save = c.getKmOfCheck();
        }
        return save;
    }

    public Date getAcquisionDate() {
        return acquisionDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public int getCurrentKm() {
        return currentKm;
    }

    public int getFrequencyCheckKm() {
        return frequencyCheckKm;
    }

    public int getTare() {
        return tare;
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

    public void setCurrentKm(int currentKm) {
        this.currentKm = currentKm;
    }

    public void setFrequencyCheckKm(int frequencyCheckKm) {
        this.frequencyCheckKm = frequencyCheckKm;
    }

    @Override
    public boolean equals(Object obj) {
            return this.plate == ((Vehicle) obj).plate;
    }
}
