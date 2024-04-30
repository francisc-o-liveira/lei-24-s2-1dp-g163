package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.RegisterSkillUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowSkillListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageSkillsUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Skill", new RegisterSkillUI()));
        options.add(new MenuItem("Show Skill List", new ShowSkillListUI()));
        options.add(new MenuItem("Remove Skill", new ShowTextUI("Implementing...")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- Manage Skills -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
