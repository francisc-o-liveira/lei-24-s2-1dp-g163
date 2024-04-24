package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.File;

public class Main {
    /**
     * This method correspond the RunProgram Method.
     * @param args it is ignored
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
        File file = new File("../src/main/resources/fxml/SceneLogin.fxml");
        System.out.println(file.canExecute());
        try {
            MainApp.main(args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}