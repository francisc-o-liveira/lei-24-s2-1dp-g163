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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.ipp.isep.dei.esoft.project.core.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.core.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

public class MainApp extends Application {

    private static File saveDirectory = null;


    Bootstrap bootstrap=new Bootstrap();


    @Override
    public void start(Stage stage) {
        try {
            bootstrap.run();
            initializeApp();
        } catch (Exception ex) {
            if(popUpBootStrap().showAndWait().get()==ButtonType.OK){
                File selectedDirectory = pickingDirectory();
                if (selectedDirectory != null) {
                    try{
                        bootstrap.setSaveDirectory(selectedDirectory);
                        bootstrap.run();
                        initializeApp();
                    }catch (IOException io){
                        criarAlertaErro(io).show();
                    } catch (Exception e) {
                        bootstrap.setSaveDirectory(pickingDirectory());
                        start(stage);
                    }
                } else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a directory");
                }
            }
        }
    }

    private File pickingDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        return directoryChooser.showDialog(null);
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

}