module pt.ipp.isep.dei.esoft.project.ui.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires AuthLib;


    opens pt.ipp.isep.dei.esoft.project.ui.gui to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.ui.gui;
}