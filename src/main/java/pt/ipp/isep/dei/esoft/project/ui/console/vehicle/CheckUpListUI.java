package pt.ipp.isep.dei.esoft.project.ui.console.vehicle;

import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.CheckUpListController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;

import java.util.List;

public class CheckUpListUI implements Runnable {
    private CheckUpListController ctrl;
    private List<Vehicle> vehiclesNeedingCheckUp;
    public CheckUpListUI(){
       ctrl =CheckUpListController.getInstance();
    }

    private CheckUpListController getController(){
        return ctrl;
    }

    public void run(){
        System.out.println("-----Vehicles Needing CheckUp-----");
        vehiclesNeedingCheckUp=getDataNeeded();
        showData();
    }

    private void showData() {
        if(vehiclesNeedingCheckUp==null || vehiclesNeedingCheckUp.isEmpty()){
            System.out.println("Dont exist vehicles needing to make a maintenance");
        }else {
            int i = 0;
            for(Vehicle v : vehiclesNeedingCheckUp){
                i++;
                System.out.println(i+" ---- "+v);
            }
        }
    }

    private List<Vehicle> getDataNeeded() {
        return ctrl.getVehicleNeedingCheckUpList();
    }
}
