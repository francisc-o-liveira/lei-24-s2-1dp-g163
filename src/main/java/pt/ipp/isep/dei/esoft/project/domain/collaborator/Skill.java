package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

/**
 * Domain class representing a Skill object.
 */
public class Skill implements Serializable{

    /** Name of the skill */
    private String skillName;

    private final transient BooleanProperty selecting;

    private boolean selectedForTeam;

    private boolean selected;

    private final transient BooleanProperty selectingForTeam;

    private transient IntegerProperty numberCollabsPerSkill;
    /**
     * Constructs a Skill object with the specified name.
     *
     * @param skillName The name of the skill.
     */
    public Skill(String skillName){
        setSkillName(skillName);
        this.selecting = new SimpleBooleanProperty(false);

        this.selecting.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selected=true;
            }
        });

        this.selectingForTeam = new SimpleBooleanProperty(false);

        this.selectingForTeam.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedForTeam=true;
            }
        });
    }

    /**
     * Retrieves the name of the skill.
     *
     * @return The name of the skill.
     */
    public String getSkillName(){
        return this.skillName;
    }

    /**
     * Compares if two skills are equal.
     *
     * Two skills are considered equal if they have the same name.
     *
     * @param other The object to compare with.
     * @return True if the skills are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Skill otherSkill= (Skill) other;
        return this.getSkillName().equals(otherSkill.getSkillName());
    }

    /**
     * Clones the current Skill object.
     *
     * @return A new Skill object with the same name as the current skill.
     */
    public Skill clone() {
        return new Skill(this.skillName);
    }

    /**
     * Sets the name of the skill.
     *
     * @param skillName The name of the skill to set.
     * @throws IllegalArgumentException if the provided skill name is not valid.
     */
    public void setSkillName(String skillName) {
        if(verifySkillName(skillName)){
            this.skillName = skillName;
        }else{
            throw new IllegalArgumentException("Skill name is not valid");
        }
    }

    private boolean verifySkillName(String skillName) {
        boolean value = true;
        if (skillName == null || skillName.length() == 0){
            value = false;
        }
        for (char c : skillName.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isSpace(c)) {
                value = false;
            }
        }
        return value;
    }

    /**
     * Returns a string representation of the skill.
     *
     * @return A string containing the name of the skill.
     */
    @Override
    public String toString(){
        return String.format("Skill: %s\n ", skillName);
    }
    /**
     * Gets the BooleanProperty for assigning skills to collaborators.
     *
     * @return BooleanProperty representing whether a skill has been selected to assign
     */
    public BooleanProperty selectedSkill(){
        return selecting;
    }

    /**
     * Gets the BooleanProperty to select skills for a team.
     *
     * @return BooleanProperty representing whether a skill has been selected to generate a team
     */
    public BooleanProperty selectedSkillForTeam(){
        return selectingForTeam;
    }

    /**
     * Retrieves the IntegerProperty representing the number of collaborators per skill.
     *
     * @return The IntegerProperty representing the number of collaborators per skill.
     */
    public IntegerProperty getNumberCollabsPerSkill() {
        return numberCollabsPerSkill;
    }

    /**
     * Sets the number of collaborators per skill.
     *
     * @param number The number of collaborators per skill to set.
     */
    public void setNumberCollabsPerSkill(Integer number) {
        if (numberCollabsPerSkill == null) {
            numberCollabsPerSkill = new SimpleIntegerProperty();
        }
        this.numberCollabsPerSkill.set(number);
    }

    /**
     * Retrieves the IntegerProperty representing the number of collaborators per skill.
     *
     * @return The IntegerProperty representing the number of collaborators per skill.
     */
    public IntegerProperty numberCollabsPerSkillProperty() {
        return numberCollabsPerSkill;
    }

    public void setSelecting(boolean value){
        this.selecting.set(value);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
