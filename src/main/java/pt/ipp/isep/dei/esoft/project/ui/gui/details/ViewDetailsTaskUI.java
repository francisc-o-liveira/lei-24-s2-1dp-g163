package pt.ipp.isep.dei.esoft.project.ui.gui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.application.DetailsEntryAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;


public class ViewDetailsTaskUI {
        public DetailsEntryAgendaController ctrl;
        @FXML
        private Label titleTask;
        @FXML
        private Label descriptionTask;
        @FXML
        private Label informalTask;
        @FXML
        private Label technicalTask;
        @FXML
        private Label duration;
        @FXML
        private Label greenSpace;
        @FXML
        private Label urgency;

        public ViewDetailsTaskUI(){
            ctrl=new DetailsEntryAgendaController();
        }

        public void setLabels(Task task){
            /*titleTask.setText(ctrl.getTitle(task));
            descriptionTask.setText(ctrl.getDescription(task));
            informalTask.setText(ctrl.getInformal(task));
            technicalTask.setText(ctrl.getTechnical(task));
            duration.setText(ctrl.getDuration(task));
            greenSpace.setText(ctrl.getGreenSpace(task));
            urgency.setText(ctrl.getUrgency(task));*/
        }



    }

