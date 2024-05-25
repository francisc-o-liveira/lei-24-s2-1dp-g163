package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

public class TaskDto {

    private String title;
    private String description;
    public static enum DegreeUrgency{High,Medium,Low}
    private DegreeUrgency degreeUrgency;
    private Tempo expectedDuration;



    public TaskDto(String title, String description, DegreeUrgency degreeUrgency, Tempo expectedDuration) {
        this.title = title;
        this.description = description;
        this.degreeUrgency = degreeUrgency;
    }

    public DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }
}
