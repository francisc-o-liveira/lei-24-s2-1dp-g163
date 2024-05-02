package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

    // AC1 : A skill name can’t have special characters or digits.
    @Test
    void verifySkillNameDontCanHaveSpecialCharactersOrNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Skill("2313@SkillTest");
        });
    }

    // AC3 : To register a skill is mandatory input the skill name.

    @Test
    void verifySkillNameNullPointer() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Skill("");
        });
    }

}