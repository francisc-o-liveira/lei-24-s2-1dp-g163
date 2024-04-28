package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterSkillController {

    /**
     * This method control a register of an skill by her name
     * @param skillName of the Skill
     */

    public void RegisterSkill(String skillName){
        verifySkillName(skillName);
        Skill skill=new Skill(skillName);
        Repositories.getInstance().getSkillRepository().registerSkill(skillName);
    }





    // UI METHOD IS NOT FOR HERE


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

    /**
     * This method return an List of Skill's existent on the skillRepository
     * @return an existent List of Skills
     */
    public List<Skill> getSkillList(){return Repositories.getInstance().getSkillRepository().getSkillList();}
  }
