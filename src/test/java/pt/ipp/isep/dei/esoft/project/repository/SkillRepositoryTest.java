package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillRepositoryTest {


    // AC4 : When creating a skill with an existing name, the system must reject such operation.
    @Test
    void registerSkill() {
        SkillRepository skillRepository = new SkillRepository();
        try {
            skillRepository.registerSkill("Java");
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        Exception exception = assertThrows(CloneNotSupportedException.class, () -> {
            skillRepository.registerSkill("Java");
        });
    }

    void validateGetSkillListMethod(){
        SkillRepository skillRepository = new SkillRepository();
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Python");
        Skill skill3 = new Skill("PHP");
        Skill skill4 = new Skill("JavaScript");
        Skill skill5 = new Skill("C");


        try {
            skillRepository.registerSkill("Java");
            skillRepository.registerSkill("Python");
            skillRepository.registerSkill("PHP");
            skillRepository.registerSkill("JavaScript");
            skillRepository.registerSkill("C");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        List<Skill> expectResult = new ArrayList<>();
        expectResult.add(skill1);
        expectResult.add(skill2);
        expectResult.add(skill3);
        expectResult.add(skill4);
        expectResult.add(skill5);


        List<Skill> result = skillRepository.getSkillList();


        assertEquals(expectResult,result);
    }


}