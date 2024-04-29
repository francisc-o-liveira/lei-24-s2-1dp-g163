package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.RegisterJobCategoryUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowJobCategoryListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageJobCategorysUI implements Runnable{
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("1 - Register Job Category", new RegisterJobCategoryUI()));
        options.add(new MenuItem("2 - Show Job Category List", new ShowJobCategoryListUI()));
        options.add(new MenuItem("3 - Remove Job Category", new ShowTextUI("Implementing...")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- Manage Job Category's -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
