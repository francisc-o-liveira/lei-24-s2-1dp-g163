package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

public class TaskDto {

    private String title;
    private String description;
    private Task.DegreeUrgency degreeUrgency;
    private Tempo expectedDuration;
    private GreenSpaceDto greenSpace;



    public TaskDto(String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto) {
        this.title = title;
        this.description = description;
        this.degreeUrgency = degreeUrgency;
        this.expectedDuration = expectedDuration;
        this.greenSpace = greenSpaceDto;
    }

    public GreenSpaceDto getGreenSpace() {
        return greenSpace;
    }

    public Task.DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }

    public String getTitle() {
        return title;
    }

    public Tempo getExpectedDuration() {
        return expectedDuration;
    }

    public String getDescription(){
        return description;
    }
}
