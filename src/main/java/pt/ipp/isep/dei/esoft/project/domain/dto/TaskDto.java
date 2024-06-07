package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

/**
 * Represents a Data Transfer Object for a Task in the system.
 */
public class TaskDto {

    private String title;
    private String description;
    private Task.DegreeUrgency degreeUrgency;
    private Tempo expectedDuration;
    private GreenSpaceDto greenSpace;

    /**
     * Constructor for initializing a TaskDto with required parameters.
     *
     * @param title            the title of the task
     * @param description      the description of the task
     * @param degreeUrgency    the urgency degree of the task
     * @param expectedDuration the expected duration of the task
     * @param greenSpaceDto    the green space associated with the task
     */
    public TaskDto(String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto) {
        this.title = title;
        this.description = description;
        this.degreeUrgency = degreeUrgency;
        this.expectedDuration = expectedDuration;
        this.greenSpace = greenSpaceDto;
    }

    /**
     * Gets the green space associated with the task.
     *
     * @return the green space associated with the task
     */
    public GreenSpaceDto getGreenSpace() {
        return greenSpace;
    }

    /**
     * Gets the urgency degree of the task.
     *
     * @return the urgency degree of the task
     */
    public Task.DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }

    /**
     * Gets the title of the task.
     *
     * @return the title of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the expected duration of the task.
     *
     * @return the expected duration of the task
     */
    public Tempo getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }
}
