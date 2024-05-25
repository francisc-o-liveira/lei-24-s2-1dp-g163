package pt.ipp.isep.dei.esoft.project.ui.gui.details;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewDetailsGreenSpaceUI implements Initializable{

        public RegisterGreenSpaceController ctrl;
        @FXML
        private Label nameGreenSpace;
        @FXML
        private Label addressGreenSpace;
        @FXML
        private Label areaGreenSpace;
        @FXML
        private Label greenSpaceType;


        public void setLabels(GreenSpace greenSpace){
                nameGreenSpace.setText(ctrl.getNameGreenSpace(greenSpace));
                addressGreenSpace.setText(ctrl.getAddressGreenSpace(greenSpace));
                greenSpaceType.setText(ctrl.getTypeGreenSpace(greenSpace));
                areaGreenSpace.setText(ctrl.getAreaGreenSpace(greenSpace));
        }

        @Override
        public void initialize(URL url, ResourceBundle rbl){
            ctrl=new RegisterGreenSpaceController();
        }

    }
