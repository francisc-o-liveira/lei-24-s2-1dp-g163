package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Repository for Skills */
public class SkillRepository {
    /** List of Skills */
    private List<Skill> skillList;
    /** Initialize the list of Skills */
    public SkillRepository(){
        skillList = new ArrayList<>();
    }

    /** Method to register a Skill
     *
     * @param skillName - skill to be registered
     * @return Optional of Skill if Skill has been registered
     * @throws CloneNotSupportedException if Skill already exists
     */
    public Optional<Skill> registerSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> optionalValue = Optional.empty();
        Skill skill = new Skill(skillName);
        if (verifyIfExistAndSave(skill)) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;

    }

    /** Verifies if Skill already exists and saves it
     *
     * @param skill to be verified
     * @return true if Skill did not exist
     * @throws CloneNotSupportedException if Skill already existed
     */
    private boolean verifyIfExistAndSave(Skill skill) throws CloneNotSupportedException {
        boolean operationSuccess = false;
        if (validateSkill(skill)) {
            operationSuccess = skillList.add(skill);
        }else {
            throw new CloneNotSupportedException("This Skill already exists");
        }
        return operationSuccess;
    }

    /** The method gets the List of Skills
     *
     * @return List of Skills
     */
    public List<Skill> getSkillList(){
        return List.copyOf(skillList);
    }

    /** Checks if Skill already exists in the list
     *
     * @param skill to be checked
     * @return true if Skill does not exist on the list
     */
    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }
}
