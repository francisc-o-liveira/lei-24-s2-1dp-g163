package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import pt.ipp.isep.dei.esoft.project.core.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

import java.io.File;
import java.io.IOException;



public class MainApp extends Application {

    private static final int MAX_ATTEMPTS = 3;
    private int attempts = 0;
    Bootstrap bootstrap = new Bootstrap();
    private ApplicationSession appSession;

    @Override
    public void start(Stage stage) {
        if (attempts < MAX_ATTEMPTS) {
            attempts++;
            if (popUpBootStrap().showAndWait().get() == ButtonType.OK) {
                File selectedDirectory = pickingDirectory();
                if (selectedDirectory != null) {
                    try {
                        appSession = ApplicationSession.getInstance();
                        appSession.setFilePath(selectedDirectory);
                        File configFile = new File(appSession.getConfigFilePath());
                        if (!configFile.exists()) {
                            throw new IOException("Configuration file not found in the selected directory.");
                        }
                        bootstrap.run();
                        initializeApp();
                    } catch (IOException io) {
                        criarAlertaErro(io.getMessage()).show();
                    } catch (Exception e) {
                        criarAlertaErro("An error occurred while starting the application. Please try again.").show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a directory");
                    alert.show();
                    start(stage);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Operation was cancelled");
                alert.show();
            }
        } else {
            criarAlertaErro("Maximum number of attempts reached. Application will exit.").showAndWait();
            System.exit(1);
        }
    }

    private File pickingDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        return directoryChooser.showDialog(null);
    }

    private void initializeApp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/SceneLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage mainStage = new Stage();
        LoginUI controller = fxmlLoader.getController();
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
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Alert criarAlertaErro(String message) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Application");
        alerta.setHeaderText("Application Startup Issues");
        alerta.setContentText(message);

        return alerta;
    }

    private Alert popUpBootStrap() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Configuration File");
        alerta.setContentText("The application did not detect the configuration file. Select one?");
        return alerta;
    }
}
