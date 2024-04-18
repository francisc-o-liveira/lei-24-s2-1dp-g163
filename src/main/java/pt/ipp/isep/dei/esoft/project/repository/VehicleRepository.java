package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.Vehicle.StatusType.Use;

import static pt.ipp.isep.dei.esoft.project.domain.Vehicle.StatusType.NotUse;

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
                    vehiclesCloseToCheck.set(i, collaborators.get(j));
                    vehiclesCloseToCheck.set(j, temp);
                }
            }
        }
    }

    public Optional<Collaborator> addCollaborator(Collaborator collaborator){
        Optional<Collaborator> newCollaborator = Optional.empty();
        newCollaborator = Optional.of(collaborator);
        if (isValidCollaborator(collaborator)){
            saveCollaborator(collaborator);
        }
        return newCollaborator;
    }

    private void saveCollaborator(Collaborator collaborator) {
        collaboratorList.add(collaborator);
    }

    private boolean isValidCollaborator(Collaborator collaborator) {
        boolean isValid = !collaboratorList.contains(collaborator);
        return isValid;
    }
}
