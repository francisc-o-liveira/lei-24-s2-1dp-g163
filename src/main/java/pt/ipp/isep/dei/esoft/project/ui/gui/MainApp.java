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
        } catch (IOException ex) {
            criarAlertaErro(ex).show();
        }
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


    private static File authDataBaseFile = new File("src/main/resources/DataBase/authDataBase.csv");

    public static File getAuthDataBaseFile() {
        return authDataBaseFile;
    }
}