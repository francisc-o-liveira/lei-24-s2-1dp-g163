package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class Entry extends Task{

    private Date startDate;
    private enum Status {Planned, Postponed, Canceled,
        Done};
    private Status status;


    public Entry(String reference, String description, String informalDescription, String technicalDescription, int duration, double cost, TaskCategory taskCategory) {
        super(reference, description, informalDescription, technicalDescription, duration, cost, taskCategory);
        this.status = Status.Planned;
        this.startDate = null;
    }


}
