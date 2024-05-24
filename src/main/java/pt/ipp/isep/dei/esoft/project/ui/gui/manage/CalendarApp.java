package pt.ipp.isep.dei.esoft.project.ui.gui.manage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class CalendarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Calendar");

        List<Entry> entries = new ArrayList<>();
        // Example entries
        entries.add(new Entry("Title 1", "Ref 1", "Desc 1", "Informal Desc 1", "Tech Desc 1", 60));
        entries.get(0).postponeEntry(new Date(2024, 5, 1));
        entries.add(new Entry("Title 2", "Ref 2", "Desc 2", "Informal Desc 2", "Tech Desc 2", 120));
        entries.get(1).postponeEntry(new Date(2024, 5, 15));
        entries.add(new Entry("Title 3", "Ref 3", "Desc 3", "Informal Desc 3", "Tech Desc 3", 90));
        entries.get(2).postponeEntry(new Date(2024, 5, 15));

        BorderPane root = new BorderPane();
        CalendarView calendarView = new CalendarView(entries);
        root.setCenter(calendarView.getView());

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

