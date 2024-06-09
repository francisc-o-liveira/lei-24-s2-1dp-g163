module pt.ipp.isep.dei.esoft.project.ui.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires AuthLib;
    requires org.apache.commons.lang3;
    requires java.logging;
    requires org.knowm.xchart;
    requires java.desktop;
    requires jdk.jshell;

    // Opening packages to javafx.fxml
    opens pt.ipp.isep.dei.esoft.project.ui.gui to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.login to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.menus to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.team to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.entry to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.tasks to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.collaborator to javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.vehicles to javafx.fxml;

    // Exporting packages
    exports pt.ipp.isep.dei.esoft.project.ui.gui;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.login;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.menus;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.team;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.entry;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.tasks;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.collaborator;
    exports pt.ipp.isep.dei.esoft.project.ui.gui.vehicles;

    // Opening domain packages to javafx.base
    opens pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.core.application.domain.team to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.core.application.domain.dto to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.core.application.domain.employee to javafx.base;
    opens pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle to javafx.base;
}
