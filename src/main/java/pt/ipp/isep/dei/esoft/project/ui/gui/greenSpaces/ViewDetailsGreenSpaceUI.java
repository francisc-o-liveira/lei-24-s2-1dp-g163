package pt.ipp.isep.dei.esoft.project.ui.gui.greenSpaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewDetailsGreenSpaceUI{

        @FXML
        private Label nameGreenSpace;
        @FXML
        private Label addressGreenSpace;
        @FXML
        private Label areaGreenSpace;
        @FXML
        private Label greenSpaceType;

        public void setLabels(GreenSpaceDto greenSpace){
                nameGreenSpace.setText(greenSpace.getName());
                Address address=greenSpace.getAddress();
                addressGreenSpace.setText(address.getStreet() + ", "+ address.getCity() + "\n"+ address.getZipCode());
                GreenSpace.Type type = greenSpace.getType();
                greenSpaceType.setText(type.name());
                areaGreenSpace.setText(String.valueOf(greenSpace.getAreaInHectares()));
        }
    }
