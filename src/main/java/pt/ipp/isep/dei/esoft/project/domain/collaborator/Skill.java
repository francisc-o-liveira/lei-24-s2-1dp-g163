package pt.ipp.isep.dei.esoft.project.domain.collaborator;

public class Skill {
    private String skillName;

    public Skill(String skillName){
        this.skillName=skillName;
    }

    public String getSkillName(){
        return this.skillName;
    }

    @Override
    public boolean equals(Object other){
        if(this==other){
            return true;
        }
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Skill otherSkill= (Skill) other;
        return this.getSkillName()==otherSkill.getSkillName();
    }

    public Skill clone() {
        return new Skill(this.skillName);
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
