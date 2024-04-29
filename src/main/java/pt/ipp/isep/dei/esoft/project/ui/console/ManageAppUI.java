package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageAppUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("1 - Register Manager/Employee", new RegisterManageUI()));
        options.add(new MenuItem("2 - Remove Manager/Employee", new ShowTextUI("Implementing....")));
        options.add(new MenuItem("3 - Show Managers/Employees List", new ShowTextUI("Remove Equipment")));
        options.add(new MenuItem("4 - Manage Organization Data", new ShowTextUI("Remove Equipment")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- System Configurations GSM -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
