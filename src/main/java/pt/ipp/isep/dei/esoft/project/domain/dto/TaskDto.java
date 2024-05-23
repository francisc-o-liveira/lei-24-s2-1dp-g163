package pt.ipp.isep.dei.esoft.project.domain.dto;

public class TaskDto {

    private String title;
    private String description;
    public static enum DegreeUrgency{High,Medium,Low}
    private DegreeUrgency degreeUrgency;



    public TaskDto(String title, String description) {
        this.title = title;
        this.description = description;
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
