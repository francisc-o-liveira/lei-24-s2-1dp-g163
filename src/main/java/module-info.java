module pt.ipp.isep.dei.esoft.project.ui.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires AuthLib;
    requires org.apache.commons.lang3;
    requires java.logging;
    requires org.knowm.xchart;
    requires java.desktop;
    requires jdk.jshell;

    opens pt.ipp.isep.dei.esoft.project.ui.gui to javafx.fxml;

    opens pt.ipp.isep.dei.esoft.project.domain.collaborator to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.domain.team to javafx.base;
    exports pt.ipp.isep.dei.esoft.project.ui.gui;
}