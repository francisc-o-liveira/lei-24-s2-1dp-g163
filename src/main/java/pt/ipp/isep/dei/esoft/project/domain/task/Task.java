package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;

import java.util.Objects;

public class Task {
    private final String reference;
    private String title;
    private String description;
    private String informalDescription;
    private String technicalDescription;
    private int expectedDuration;
    private GreenSpace greenSpace;
    private TaskDto.DegreeUrgency degreeUrgency;




    public Task(String title, String reference, String description, String informalDescription, String technicalDescription,
                int expectedDuration, GreenSpace greenSpace, TaskDto.DegreeUrgency degreeUrgency) {
        validateReference(reference);
        this.greenSpace = greenSpace;
        this.title = title;
        this.reference = reference;
        this.description = description;
        this.informalDescription = informalDescription;
        this.technicalDescription = technicalDescription;
        this.expectedDuration = expectedDuration;
        this.degreeUrgency = degreeUrgency;
    }

    public String getReference() {
        return reference;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getInformalDescription() {
        return informalDescription;
    }
    public String getTechnicalDescription() {
        return technicalDescription;
    }
    public int getDuration() {
        return expectedDuration;
    }




    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }


    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */

    public Task clone() {
        return new Task(this.title,this.reference, this.description, this.informalDescription, this.technicalDescription,
                this.expectedDuration, this.greenSpace,this.degreeUrgency);
    }
}