package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.CreateTaskUI;

import java.util.ArrayList;
import java.util.List;

public class ManageVehiclesUI implements Runnable{
    public ManageVehiclesUI(){
    }
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("1 - Show Vehicle List", new CreateTaskUI()));
        options.add(new MenuItem("2 - Remove Vehicle", new ManageTeamsUI()));
        options.add(new MenuItem("3 - Add Vehicle", new ManageCollaboratorsUI()));
        options.add(new MenuItem("4 - Show CheckUp List", new ManageVehiclesUI()));
        options.add(new MenuItem("5 - Register a CheckUp", new ManageVehiclesUI()));
        options.add(new MenuItem("6 - Register update Kilometers", new ManageVehiclesUI()));
    }
}
