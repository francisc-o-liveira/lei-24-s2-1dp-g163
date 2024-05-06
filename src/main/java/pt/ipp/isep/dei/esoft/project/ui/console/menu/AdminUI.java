package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.ui.console.CreateTaskUI;
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
        options.add(new MenuItem("Create Task", new CreateTaskUI()));
        options.add(new MenuItem("Manage Teams", new ManageTeamsUI()));
        options.add(new MenuItem("Manage Collaborators", new ManageCollaboratorsUI()));
        options.add(new MenuItem("Manage JobCategories", new ManageJobCategorysUI()));
        options.add(new MenuItem("Manage Skills", new ManageSkillsUI()));
        options.add(new MenuItem("Manage Vehicles", new ManageVehiclesUI()));
        options.add(new MenuItem("Manage Equipment", new ManageEquipmentUI()));
        options.add(new MenuItem("Application Configuration", new ManageAppUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- ADMIN MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}