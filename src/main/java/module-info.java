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
    opens pt.ipp.isep.dei.esoft.project.domain.dto to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.domain.employee to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.domain.vehicle to javafx.base;
    exports pt.ipp.isep.dei.esoft.project.ui.gui;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.manage;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.manage to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.details;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.details to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.login;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.login to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.main;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.main to javafx.fxml;



}