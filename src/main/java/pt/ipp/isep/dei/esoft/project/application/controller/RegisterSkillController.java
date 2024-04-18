package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;

public class RegisterSkillController {

    public SkillRepository skillRepository;

    public SkillRepository getSkillRepository(){
        return skillRepository;
    }

    public void RegisterSkill(String skillName){
        Skill skill=new Skill(skillName);
        skillRepository.add(skill);
    }

    public List<Skill> getSkillList(){
        return skillRepository.getSkillList();
    }
}
