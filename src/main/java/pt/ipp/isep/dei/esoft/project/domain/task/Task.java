package pt.ipp.isep.dei.esoft.project.domain.task;

import java.util.Objects;

public class Task {
    private final String reference;
    private String description;
    private String informalDescription;
    private String technicalDescription;
    private int duration;
    private double cost;


    public Task(String reference, String description, String informalDescription, String technicalDescription,
                int duration, double cost) {
        validateReference(reference);
        this.reference = reference;
        this.description = description;
        this.informalDescription = informalDescription;
        this.technicalDescription = technicalDescription;
        this.duration = duration;
        this.cost = cost;

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
        return new Task(this.reference, this.description, this.informalDescription, this.technicalDescription,
                this.duration, this.cost);
    }
}