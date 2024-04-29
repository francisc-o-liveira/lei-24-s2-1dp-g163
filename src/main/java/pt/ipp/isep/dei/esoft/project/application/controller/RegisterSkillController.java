package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RegisterSkillController {

    private SkillRepository skillRepository;

    public boolean RegisterSkill(String skillName){
        verifySkillName(skillName);
        Optional<Skill> newSkill = Repositories.getInstance().getSkillRepository().registerSkill(skillName);
        if (newSkill.isPresent()){
            return true;
        }
        return false;
    }

    private void verifySkillName(String skillName) {
        if(skillName.trim().isEmpty()){
            throw new NullPointerException("The Skill Name is empty please introduce name");
        }
        char[] testSkill = skillName.trim().toCharArray();
        for(char x : testSkill){
            if(!Character.isLetter(x)){
                throw new IllegalArgumentException("The Skill Name dont accept Special Characters or Numbers");
            }
        }
    }

    public List<Skill> getSkillList(){return Repositories.getInstance().getSkillRepository().getSkillList();}

    public void removeFromList(Skill skillName){
        Repositories.getInstance().getSkillRepository().getSkillList().remove(skillName);
    }

    public ArrayList<DocType.Type> getDocTypeList(){return new ArrayList<>(Arrays.asList(DocType.Type.values()));}
}
