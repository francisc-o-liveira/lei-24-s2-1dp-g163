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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.ipp.isep.dei.esoft.project.core.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

public class MainApp extends Application {

    private static File saveDirectory = null;
    @Override
    public void start(Stage stage) {
        try {
            // PRIMEIRA TENTATIVA DE LOAD DATA
            loadDatabases();
            initializeApp();
        } catch (Exception ex) {
            //criarAlertaErro(ex).show();
            if(popUpBootStrap().showAndWait().get()==ButtonType.OK){
                /// ISTO PODE TAR NUM METODO 777777
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Select Directory");
                File selectedDirectory = directoryChooser.showDialog(null);

                ///
                if (selectedDirectory != null) {
                    try{
                        // SEGUNDA TENTATIVA LOAD DATA VAI TER UM SAVEDIRECTORY AQUI SALVADO LOGO O METODO GETFILEPATH VAI REAGIR DIFERENTE
                        saveDirectory = selectedDirectory;
                        loadDatabases();
                        initializeApp();
                    }catch (IOException io){
                        criarAlertaErro(io).show();
                    } catch (Exception e) {
                        // NAO SEI SE ISTO FAZ O WHILE QUE NOS QUEREMOS PODES TESTAR!
                        // PODES VOLTAR A CHAMAR AQUI O METODO 77777777 que referi em cima
                        start(stage);
                    }
                } else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a directory");
                }
            }
        }

    }

    private void loadDatabases() throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
    }

    // VERIFICA BEM ESTE METODO NAO SEI QUE MERDA E AQUELA QUE FIZESTE
    private void initializeApp() throws IOException {
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
        // NAO FUNCIONA SE A LUZ FOR A BAIXO CARALHO FDS QUE MERDA É ESTA
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveData();
        }));
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

    private Alert popUpBootStrap(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText("The application did not detect a database. Select one?");
        return alerta;
    }

    private static String getFilePath(String fileName) {
        if (saveDirectory == null) {
            // Get the current working directory
            String currentDir = System.getProperty("user.dir");
            // Construct the path to the team database file
            String relativePath = "\\target\\classes\\DataBase\\";
            // Combine the current directory with the relative path
            String fullPath = currentDir + relativePath + fileName;
            return fullPath;
        }else {
            String fullPath = saveDirectory.getAbsolutePath() + File.separator + fileName;
            return fullPath;
        }
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
    private static String taskDataBaseFile=new String("taskDataBase.csv");

    public static String getEntryDataBaseFile() {
        return getFilePath(entryDataBaseFile);
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