package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle.StatusType.Use;

import static pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle.StatusType.NotUse;

public class VehicleRepository{
    private List<Vehicle> vehicleList;

    public List<Vehicle> getVehicleNotActive(){
        List<Vehicle> vehicleNotActive=new ArrayList<>();
        for(Vehicle c : vehicleList){
            if(c.getStatus()==NotUse){
                vehicleNotActive.add(c);
            }
        }
        return vehicleNotActive;
    }

    public void activateVehicle(Vehicle vehicle){
        if(vehicle.getStatus()==NotUse){
            vehicle.setStatus(Use);
        }
    }
    public void desactivateVehicle(Vehicle vehicle){
        if(vehicle.getStatus()==Use){
            vehicle.setStatus(NotUse);
        }
    }

    public List<Vehicle> getVehicleListPerCheckUp() {
        ArrayList <Vehicle> vehiclesNeedCheckUp = new ArrayList<>();
        for(Vehicle v : vehicleList){
            if(v.isCloseToCheck()){
                vehiclesNeedCheckUp.add(v);
            }
        }
        return vehiclesNeedCheckUp;
    }

    public List<Vehicle> getVehicleListPerType(Vehicle.StatusType statusType){
        ArrayList<Vehicle> vehiclesPerType = new ArrayList<>();
        for(Vehicle v : vehicleList){
            if(v.getStatus() == statusType){
                vehiclesPerType.add(v);
            }
        }
        return vehiclesPerType;
    }

    public void sortVehiclesPerCloseToCheckUp(List<Vehicle> vehiclesCloseToCheck){
        Vehicle temp;
        for(int i=0; i < vehiclesCloseToCheck.size()-1; i++){
            for(int j=i+1; j < vehiclesCloseToCheck.size(); j++){
                if(vehiclesCloseToCheck.get(i).getKmCloseToCheck()<vehiclesCloseToCheck.get(j).getKmCloseToCheck()){
                    temp=vehiclesCloseToCheck.get(i);
                    vehiclesCloseToCheck.set(i, vehiclesCloseToCheck.get(j));
                    vehiclesCloseToCheck.set(j, temp);
                }
            }
        }
    }

    public Optional<Vehicle> addVehicle(Vehicle vehicle){
        Optional<Vehicle> newVehicle = Optional.empty();
        newVehicle = Optional.of(vehicle);
        boolean operationSucess = false;
        if (isValidVehicle(vehicle)){
            operationSucess = saveVehicle(vehicle);
        }
        if (!operationSucess){
            newVehicle=Optional.empty();
        }
        return newVehicle;
    }

    private boolean saveVehicle(Vehicle vehicle) {
        return vehicleList.add(vehicle);
    }

    private boolean isValidVehicle(Vehicle vehicle) {
       return !vehicleList.contains(vehicle);

    }

    public Vehicle.Type[] getVehicleTypeList() {
        return Vehicle.Type.values();
    }

    public Optional<Vehicle> registerVehicle(String brand, String model, Date acquisitionDate, Date registerDate, int currentKM, int checkupFrequency, double grossWeight, int tare, String plate, Vehicle.Type type) {
        Optional<Vehicle> newVehicle = Optional.empty();
        Vehicle vehicle = new Vehicle(brand,model,type,tare,grossWeight,currentKM,registerDate,acquisitionDate,checkupFrequency,plate);
        newVehicle = addVehicle(vehicle);
        return newVehicle;
    }

    /**
     * Regista um check-up para um veículo com base na sua matrícula.
     * @param vehicle The vehicle to register checkUp a ser encontrado.
     * @param dateOfCheckUp A data do check-up no formato "DD/MM/AAAA".
     * @param currentKms A quilometragem atual do veículo.
     * @return Verdadeiro se o check-up for adicionado com sucesso, falso caso contrário.
     */
    public boolean addCheckUp(Vehicle vehicle, Date dateOfCheckUp, int currentKms) {
        Optional<CheckUp> newCheck = null;
        if (vehicle != null) {
             newCheck = vehicle.registerCheckUp(currentKms, dateOfCheckUp);
        }
        if(newCheck.isPresent()){
            return true;
        }
        return false;
    }

    public Optional<List<CheckUp>> getCheckUpDetailsList(Vehicle vehicle) {
        return Optional.of(vehicle.getCheckUpList());
    }


    public List<String> getVehiclePlateList() {
        List<String> vehiclePlate = new ArrayList<>();
        for (Vehicle v : vehicleList){
            vehiclePlate.add(v.getPlate());
        }
        return vehiclePlate;
    }

    public Vehicle getVehicleByIndex(int selectedPlate) {
        return vehicleList.get(selectedPlate-1);
    }

    public List<Vehicle> getVehicleNeedingCheckUp() {
        List<Vehicle> vehiclesNeedingCheckUp=new ArrayList<>();
        for(Vehicle v : vehicleList){
            if(v.isCloseToCheck()){
                vehiclesNeedingCheckUp.add(v);
            }
        }
        return vehiclesNeedingCheckUp;
    }
}
