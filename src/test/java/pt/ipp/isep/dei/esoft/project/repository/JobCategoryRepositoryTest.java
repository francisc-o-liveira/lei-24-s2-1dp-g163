package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobCategoryRepositoryTest {
    // AC4 - When creating a job category with an existing reference, the system must reject such operation.
    @Test
    void registerJobCategory() {
        JobCategoryRepository jobCategoryRepository = new JobCategoryRepository();
        try {
            jobCategoryRepository.registerJobCategory("Java");
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        Exception exception = assertThrows(CloneNotSupportedException.class, () -> {
            jobCategoryRepository.registerJobCategory("Java");
        });
    }
}