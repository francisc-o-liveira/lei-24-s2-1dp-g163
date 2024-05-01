package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {
        public List<Skill> skillList;

    public SkillRepository(){
        skillList = new ArrayList<>();
    }

    public Optional<Skill> registerSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> optionalValue = Optional.empty();
        Skill skill = new Skill(skillName);
        if (verifyIfExistAndSave(skill)) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;

    }

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

    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }
}
