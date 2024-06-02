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
        setTitle(title);
        setDescription(description);
        setExpectedDuration(expectedDuration);
        setGreenSpace(greenSpace);
        setDegreeUrgency(degreeUrgency);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        if (verifyIsOnlyCharacterOrNumeric(title)){
            this.title = title;
        }else{
            throw new IllegalArgumentException("Title should be only alphanumeric characters");
        }
    }

    public void setGreenSpace(GreenSpace greenSpace) {
        if (greenSpace!=null){
            this.greenSpace = greenSpace;
        }else{
            throw new NullPointerException("GreenSpace should not be null");
        }
    }

    public void setDegreeUrgency(DegreeUrgency degreeUrgency) {
        if (degreeUrgency!=null){
            this.degreeUrgency = degreeUrgency;
        }else{
            throw new NullPointerException("DegreeUrgency should not be null");
        }
    }

    public void setExpectedDuration(Tempo expectedDuration) {
        if (expectedDuration!=null && getExpectedDuration().isMaior(0,0)){
            this.expectedDuration = expectedDuration;
        }else {
            throw new IllegalArgumentException("ExpectedDuration should be greater than or equal to 0");
        }
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

    /** Method to verify if the name only contains characters
     *
     * @param name of Collaborator
     * @return true if the name only contains characters
     */
    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

    /** Method to verify if the name only contains characters
     *
     * @param name of Collaborator
     * @return true if the name only contains characters
     */
    private boolean verifyIsOnlyCharacterOrNumeric(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c) && !Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}