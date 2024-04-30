package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.ui.console.menu.ManageCollaboratorsUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.ManageTeamsUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.ManageVehiclesUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageEquipmentUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Equipment", new ShowTextUI("Register Equipment")));
        options.add(new MenuItem("Show Equipment List", new ShowTextUI("Show Equipment List")));
        options.add(new MenuItem("Remove Equipment", new ShowTextUI("Remove Equipment")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- EQUIPMENT MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
