package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.CreateTaskUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HRManagerUI implements Runnable{
    public HRManagerUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Task", new CreateTaskUI()));
        options.add(new MenuItem("Manage Teams", new ManageTeamsUI()));
        options.add(new MenuItem("Manage Collaborators", new ManageCollaboratorsUI()));
        options.add(new MenuItem("Manage JobCategories", new ManageJobCategorysUI()));
        options.add(new MenuItem("Manage Skills", new ManageSkillsUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- HUMAN RESOURCES MANAGER MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}

