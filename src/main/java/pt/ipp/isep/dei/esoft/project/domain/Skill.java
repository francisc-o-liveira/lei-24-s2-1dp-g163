package pt.ipp.isep.dei.esoft.project.domain;

public class Skill {
    private String skillName;


    public Skill(String skillName){
        this.skillName=skillName;
    }


    public Skill clone() {
        return new Skill(this.skillName);
    }
}
