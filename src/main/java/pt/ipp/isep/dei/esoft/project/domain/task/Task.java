package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private Tempo expectedDuration;
    private GreenSpace greenSpace;
    private TaskDto.DegreeUrgency degreeUrgency;




    public Task(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, TaskDto.DegreeUrgency degreeUrgency) {
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.expectedDuration = expectedDuration;
        this.degreeUrgency = degreeUrgency;
    }


    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Tempo getDuration() {
        return expectedDuration;
    }


    public TaskDto.DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }

}