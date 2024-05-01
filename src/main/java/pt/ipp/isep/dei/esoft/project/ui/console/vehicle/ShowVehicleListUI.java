package pt.ipp.isep.dei.esoft.project.ui.console.vehicle;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;

import java.util.List;

public class ShowVehicleListUI implements Runnable{
    private RegisterVehicleController ctrl ;

    public ShowVehicleListUI(){
        ctrl=new RegisterVehicleController();
    }
    @Override
    public void run() {
        System.out.println("----- Vehicles List -----");
        showDataAsked();
    }

    private void showDataAsked()  {
        List<Vehicle> vehicleList = ctrl.getVehicleList();
        if (vehicleList == null) {
            System.out.println("Dont exist any Vehicle register on the System.");
        }else{
            for (Vehicle v : vehicleList){
                System.out.println(v);
            }
        }
    }
}
