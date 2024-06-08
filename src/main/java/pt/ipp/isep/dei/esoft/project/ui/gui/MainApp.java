package pt.ipp.isep.dei.esoft.project.ui.gui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

public class MainApp extends Application {




    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/SceneLogin.fxml"));
            Scene scene=new Scene(fxmlLoader.load());
            Stage mainStage=new Stage();
            LoginUI controller=fxmlLoader.getController();
            controller.setMainStage(mainStage);
            mainStage.setTitle("AquaCode - Green Space Management");
            mainStage.setScene(scene);
            mainStage.show();
            Image image = new Image(getClass().getResourceAsStream("/Icons/icon.png"));
            mainStage.getIcons().add(image);
            mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

                    alerta.setTitle("Confirmation");
                    alerta.setHeaderText("Closing Application");
                    alerta.setContentText("Do you wish to close the app?");

                    ((Button) alerta.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
                    ((Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

                    if (alerta.showAndWait().get() == ButtonType.CANCEL) {
                        event.consume();
                    }
                }
            });

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                saveData();
            }));

        } catch (IOException ex) {
            criarAlertaErro(ex).show();
        }
    }

    public AssignEntryOnAgendaController ctrlEntry=AssignEntryOnAgendaController.getInstance();

    public void saveData(){
        ctrlEntry.saveToDB();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Alert criarAlertaErro(Exception ex) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Aplicação");
        alerta.setHeaderText("Problemas no arranque da aplicação");
        alerta.setContentText(ex.getMessage());

        return alerta;
    }

    private static String getFilePath(String fileName) {
        // Get the current working directory
        String currentDir = System.getProperty("user.dir");

        // Construct the path to the team database file
        String relativePath = "\\\\target\\\\classes\\\\DataBase\\\\";
        currentDir = currentDir.replace("\\", "\\\\");

        // Combine the current directory with the relative path
        String fullPath = currentDir + relativePath + fileName;

        return fullPath;
    }


    private static String authDataBaseFile = new String("authDataBase.csv");

    public static File getAuthDataBaseFile() {
        return new File(getFilePath(authDataBaseFile));
    }

    private static String collaboratorDataBaseFile = new String("collaboratorDataBase.csv");

    public static String getCollaboratorDataBaseFile() {
        return getFilePath(collaboratorDataBaseFile);
    }

    private static String jobCategoryDataBaseFile = new String("jobCategoryDataBase.csv");

    public static String getJobCategoryDataBaseFile() {
        return getFilePath(jobCategoryDataBaseFile);
    }

    private static String skillDataBaseFile = new String("skillDataBase.csv");

    public static String getSkillDataBaseFile() {
        return getFilePath(skillDataBaseFile);
    }

    private static String entryDataBaseFile = new String("entryDataBase.csv");

    private static String entryReferenceDataBaseFile = new String("entryReferenceDataBase.csv");
    private static String taskDataBaseFile=new String("taskDataBase.csv");

    public static String getEntryDataBaseFile() {
        return getFilePath(entryDataBaseFile);
    }

    private static String getEntryReferenceReferenceDataBaseFile() {
        return getFilePath(entryReferenceDataBaseFile);
    }

    public static String getTaskDataBaseFile(){
        return getFilePath(taskDataBaseFile);
    }

    private static String vehicleDataBaseFile = new String("vehicleDataBase.csv");


    public static String getVehicleDataBaseFile() {
        return getFilePath(vehicleDataBaseFile);
    }
    private static String teamDataBaseFile = new String("teamDataBase.csv");


    public static String getTeamDataBaseFile() {
        return getFilePath(teamDataBaseFile);
    }

    private static String greenSpaceDataBaseFile = new String("greenSpaceDataBase.csv");

    public static String getGreenSpaceDataBaseFile() {
        return getFilePath(greenSpaceDataBaseFile);
    }
    private static String managerDataBaseFile = new String("managerDataBase.csv");

    public static String getManagerDataBaseFile() {
        return getFilePath(managerDataBaseFile);
    }
}