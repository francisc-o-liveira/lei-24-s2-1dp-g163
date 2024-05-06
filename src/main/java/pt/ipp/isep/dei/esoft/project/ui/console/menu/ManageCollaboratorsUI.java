package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.collaborator.RegisterCollaboratorUI;
import pt.ipp.isep.dei.esoft.project.ui.console.collaborator.ShowCollaboratorListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.skill.AssignSkillsUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageCollaboratorsUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Collaborator", new RegisterCollaboratorUI()));
        options.add(new MenuItem("Show Collaborator List", new ShowCollaboratorListUI()));
        options.add(new MenuItem("Assign Skills to Collaborator", new AssignSkillsUI()));
        options.add(new MenuItem("Remove Collaborator", new ShowTextUI("Implementing...")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- Manage Collaborators -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
