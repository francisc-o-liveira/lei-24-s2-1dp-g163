package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.List;

public class SkillRepository {
    public List<Skill> skillList;

    public SkillRepository(Skill skill){
        skillList.add(skill);
    }
    public List<Skill> getSkillList(){
        return skillList;
    }
}
