package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.system.RegisterManageUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageAppUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Manager/Employee", new RegisterManageUI()));
        options.add(new MenuItem("Remove Manager/Employee", new ShowTextUI("Implementing....")));
        options.add(new MenuItem("Show Managers/Employees List", new ShowTextUI("Implementing....")));
        options.add(new MenuItem("Manage Organization Data", new ShowTextUI("Implementing....")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- System Configurations GSM -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
