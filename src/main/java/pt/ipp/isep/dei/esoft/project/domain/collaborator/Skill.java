package pt.ipp.isep.dei.esoft.project.domain.collaborator;

/** Domain class for the Skill Object */
public class Skill {

    /** Name of the Skill */
    private String skillName;

    /** Constructor method for Skill
     *
     * @param skillName - name for the skill
     */
    public Skill(String skillName){
        setSkillName(skillName);
    }

    /** Gets the name of the Skill
     *
     * @return name of Skill
     */

    public String getSkillName(){
        return this.skillName;
    }
    /** Method to compare if two Skills are the same
     *
     * Two Skills are equal if both have the same name
     *
     * @param other - Job Category to be compared along with other
     * @return true if they are the same
     */
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
        for (char c : skillName.toCharArray()) {
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
