package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.CreateTaskUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VFManagerUI implements Runnable{
    public VFManagerUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Task", new CreateTaskUI()));
        options.add(new MenuItem("Manage Teams", new ShowTextUI("You have chosen Option 2.")));
        options.add(new MenuItem("Manage Collaborators", new ShowTextUI("You have chosen Option 3.")));
        options.add(new MenuItem("Manage Vehicles", new ShowTextUI("You have chosen Option 4.")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- VEHICLES AND EQUIPMENT FLEET MANAGER MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}

