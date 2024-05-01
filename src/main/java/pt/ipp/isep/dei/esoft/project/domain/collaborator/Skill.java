package pt.ipp.isep.dei.esoft.project.domain.collaborator;

public class Skill {
    private String skillName;

    public Skill(String skillName){
        setSkillName(skillName);
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
        return this.getSkillName().equals(otherSkill.getSkillName());
    }

    public Skill clone() {
        return new Skill(this.skillName);
    }

    public void setSkillName(String skillName) {
        if(verifySkillName(skillName)){
            this.skillName = skillName;
        }else{
            throw new IllegalArgumentException("Skill name is not valid");
        }
    }

    private boolean verifySkillName(String skillName) {
        boolean value = true;
        if (skillName==null || skillName.length()==0){
            value = false;
        }
        for (char c : this.skillName.toCharArray()) {
            if (!Character.isLetter(c)) {
                value=false;
            }
        }
        return value;
    }

    @Override
    public String toString(){
        return String.format("Skill: %s\n ", skillName);
    }
}
