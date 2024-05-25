package pt.ipp.isep.dei.esoft.project.ui.gui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.application.DetailsEntryAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;


public class ViewDetailsTaskUI {
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

        public void setLabels(TaskDto task){
            titleTask.setText(task.getTitle());
            descriptionTask.setText(task.getDescription());
            Task.DegreeUrgency typeUrgency=task.getDegreeUrgency();
            urgency.setText(typeUrgency.name());
            /*informalTask.setText(ctrl.getInformal(task));
            technicalTask.setText(ctrl.getTechnical(task));
            duration.setText(ctrl.getDuration(task));
            greenSpace.setText(ctrl.getGreenSpace(task));*/
        }



    }

