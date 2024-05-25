package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

public class Task {
    private String title;
    private String description;
    private Tempo expectedDuration;
    private GreenSpace greenSpace;
    private DegreeUrgency degreeUrgency;
    public enum DegreeUrgency {High,Medium,Low};

    public Task(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency) {
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.expectedDuration = expectedDuration;
        this.degreeUrgency = degreeUrgency;
    }
    public GreenSpace getGreenSpace() {
        return greenSpace;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Tempo getExpectedDuration() {
        return expectedDuration;
    }
    public static DegreeUrgency[] getDegreeUrgencyValues() {
        return DegreeUrgency.values();
    }
    public DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }
}