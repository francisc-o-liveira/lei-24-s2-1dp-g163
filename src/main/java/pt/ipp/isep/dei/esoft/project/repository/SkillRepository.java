package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.TaskCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {
    public List<Skill> skillList;

    public SkillRepository(){
        skillList = new ArrayList<>();
    }
    public Optional<Skill> add(Skill skill) {

        Optional<Skill> newSkill= Optional.empty();
        boolean operationSuccess = false;

        if (validateSkill(skill)) {
            newSkill = Optional.of(skill.clone());
            operationSuccess = skillList.add(newSkill.get());
        }

        if (!operationSuccess) {
            newSkill = Optional.empty();
        }

        return newSkill;
    }
    public List<Skill> getSkillList(){
        return List.copyOf(skillList);
    }

    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }
}
