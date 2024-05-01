package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobCategoryTest {

    // AC1 - A job name can’t have special characters or digits.

    @Test
    void verifyIfNameCanHaveSpecialCharacters(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new JobCategory("2313@SkillTest");
        });

    }

    // AC3 - To register a job is mandatory input the job name.
    @Test
    void verifyIfNameCanNullPointer(){
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new JobCategory("");
        });
    }
}