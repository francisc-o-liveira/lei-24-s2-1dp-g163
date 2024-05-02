package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.team.ShowTeamsListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.team.GenerateTeamUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageTeamsUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Generate a Team", new GenerateTeamUI()));
        options.add(new MenuItem("Show Team's List", new ShowTeamsListUI()));
        options.add(new MenuItem("Register Team", new ShowTextUI("Implementing...")));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- Manage Skills -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
