package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.ui.console.menu.MainMenuUI;

public class MainConsole {
    public static void main(String[] args) {

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.run();
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
