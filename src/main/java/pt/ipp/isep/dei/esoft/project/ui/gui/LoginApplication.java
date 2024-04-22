package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("/SceneLogin.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        Stage mainStage=new Stage();
        LoginUI controller=fxmlLoader.getController();
        controller.setMainStage(mainStage);
        mainStage.setTitle("AquaCode - Green Space Management");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
