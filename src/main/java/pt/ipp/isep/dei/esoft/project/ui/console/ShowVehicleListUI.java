package pt.ipp.isep.dei.esoft.project.ui.console;

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

    private void showDataAsked() {
        List<Vehicle> vehicleList = ctrl.getVehicleList();
        for (Vehicle v : vehicleList){
            System.out.println(v);
        }
    }
}
