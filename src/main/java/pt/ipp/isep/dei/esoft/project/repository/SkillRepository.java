package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {
    public List<Skill> skillList;

    public SkillRepository(){
        skillList = new ArrayList<>();
    }

    public Optional<Skill> registerSkill(String skillName){
        Optional<Skill> optionalValue = Optional.empty();
        Skill skill = new Skill(skillName);
        if (verifyIfExistAndSave(skill)) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;

    }
    public boolean verifyIfExistAndSave(Skill skill) {
        Optional<Skill> newSkill;
        boolean operationSuccess = false;
        if (validateSkill(skill)) {
            newSkill = Optional.of(skill.clone());
            operationSuccess = skillList.add(newSkill.get());
        }
        return operationSuccess;
    }
    public List<Skill> getSkillList(){
        return List.copyOf(skillList);
    }

    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }
}
