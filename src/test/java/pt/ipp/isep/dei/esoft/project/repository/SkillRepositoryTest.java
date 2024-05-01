package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

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


}