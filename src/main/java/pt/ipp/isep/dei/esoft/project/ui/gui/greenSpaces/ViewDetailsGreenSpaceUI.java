package pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI Controller class for viewing details of a green space in the GUI.
 */
public class ViewDetailsGreenSpaceUI{

        /** Label for displaying the name of the green space. */
        @FXML
        private Label nameGreenSpace;

        /** Label for displaying the address of the green space. */
        @FXML
        private Label addressGreenSpace;

        /** Label for displaying the area of the green space. */
        @FXML
        private Label areaGreenSpace;

        /** Label for displaying the type of the green space. */
        @FXML
        private Label greenSpaceType;

        /**
         * Sets the labels with information about the given green space.
         *
         * @param greenSpace The green space DTO containing the information to be displayed.
         */
        public void setLabels(GreenSpaceDto greenSpace){
                // Set the name of the green space
                nameGreenSpace.setText(greenSpace.getName());

                // Set the address of the green space
                Address address=greenSpace.getAddress();
                addressGreenSpace.setText(address.getStreet() + ","+ address.getCity() + "\n"+ address.getZipCode());

                // Set the type of the green space
                GreenSpace.Type type = greenSpace.getType();
                greenSpaceType.setText(type.name());

                // Set the area of the green space
                areaGreenSpace.setText(String.valueOf(greenSpace.getAreaInHectares()));
        }
}
