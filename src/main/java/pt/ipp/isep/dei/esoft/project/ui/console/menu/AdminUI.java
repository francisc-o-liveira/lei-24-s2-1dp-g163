package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.ui.console.CreateTaskUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ManageAppUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ManageEquipmentUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

public class AdminUI implements Runnable {
    public AdminUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("1 - Create Task", new CreateTaskUI()));
        options.add(new MenuItem("2 - Manage Teams", new ManageTeamsUI()));
        options.add(new MenuItem("3 - Manage Collaborators", new ManageCollaboratorsUI()));
        options.add(new MenuItem("4 - Manage JobCategories", new ManageVehiclesUI()));
        options.add(new MenuItem("5 - Manage Skills", new ManageVehiclesUI()));
        options.add(new MenuItem("6 - Manage Vehicles", new ManageVehiclesUI()));
        options.add(new MenuItem("7 - Manage Equipment", new ManageEquipmentUI()));
        options.add(new MenuItem("8 - Application Configuration", new ManageAppUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- ADMIN MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}