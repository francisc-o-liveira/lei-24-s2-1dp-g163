package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.Serializable;

/**
 * Represents a Task with various properties such as title, description, expected duration, green space, and degree of urgency.
 */
public class Task implements Serializable {
    private String title;
    private String description;
    private Tempo expectedDuration;
    private GreenSpace greenSpace;
    private DegreeUrgency degreeUrgency;

    /**
     * Enum representing the degree of urgency for the task.
     */
    public enum DegreeUrgency {High, Medium, Low};

    /**
     * Constructs a Task with the specified details.
     *
     * @param title the title of the task
     * @param description the description of the task
     * @param expectedDuration the expected duration of the task
     * @param greenSpace the green space associated with the task
     * @param degreeUrgency the degree of urgency of the task
     */
    public Task(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency) {
        setTitle(title);
        setDescription(description);
        setExpectedDuration(expectedDuration);
        setGreenSpace(greenSpace);
        setDegreeUrgency(degreeUrgency);
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the title to set
     * @throws IllegalArgumentException if the title contains non-alphanumeric characters
     */
    public void setTitle(String title) {
        if (verifyIsOnlyCharacterOrNumeric(title)) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("Title should be only alphanumeric characters");
        }
    }

    /**
     * Sets the green space associated with the task.
     *
     * @param greenSpace the green space to set
     * @throws NullPointerException if the green space is null
     */
    public void setGreenSpace(GreenSpace greenSpace) {
        if (greenSpace != null) {
            this.greenSpace = greenSpace;
        } else {
            throw new NullPointerException("GreenSpace should not be null");
        }
    }

    /**
     * Sets the degree of urgency for the task.
     *
     * @param degreeUrgency the degree of urgency to set
     * @throws NullPointerException if the degree of urgency is null
     */
    public void setDegreeUrgency(DegreeUrgency degreeUrgency) {
        if (degreeUrgency != null) {
            this.degreeUrgency = degreeUrgency;
        } else {
            throw new NullPointerException("DegreeUrgency should not be null");
        }
    }

    /**
     * Sets the expected duration for the task.
     *
     * @param expectedDuration the expected duration to set
     * @throws IllegalArgumentException if the expected duration is less than or equal to zero
     */
    public void setExpectedDuration(Tempo expectedDuration) {
        if (expectedDuration != null && expectedDuration.isMaior(0, 0)) {
            this.expectedDuration = expectedDuration;
        } else {
            throw new IllegalArgumentException("ExpectedDuration should be greater than or equal to 0");
        }
    }

    /**
     * Gets the green space associated with the task.
     *
     * @return the green space
     */
    public GreenSpace getGreenSpace() {
        return greenSpace;
    }

    /**
     * Gets the title of the task.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the expected duration of the task.
     *
     * @return the expected duration
     */
    public Tempo getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Gets all possible values of degree of urgency.
     *
     * @return an array of degree of urgency values
     */
    public static DegreeUrgency[] getDegreeUrgencyValues() {
        return DegreeUrgency.values();
    }

    /**
     * Gets the degree of urgency of the task.
     *
     * @return the degree of urgency
     */
    public DegreeUrgency getDegreeUrgency() {
        return degreeUrgency;
    }

    /**
     * Verifies if the given name contains only alphabetic characters.
     *
     * @param name the name to verify
     * @return true if the name contains only alphabetic characters, false otherwise
     */
    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if the given name contains only alphanumeric characters.
     *
     * @param name the name to verify
     * @return true if the name contains only alphanumeric characters, false otherwise
     */
    private boolean verifyIsOnlyCharacterOrNumeric(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
