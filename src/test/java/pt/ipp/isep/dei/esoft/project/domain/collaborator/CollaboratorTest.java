package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorTest {

    @Test
    void setStatus() {
        Collaborator c1= new Collaborator("Something", new Date(2000,10,10), new Date(2024,5,1), "Rua", "1111-111", "Porto", "+351919888777", "something.s@email.com", DocType.Type.CitizenCard, 888777444, new JobCategory("Job"));
        c1.setStatus(Collaborator.StatusType.NotActive);
        assertNotNull(c1.getStatus());
    }

    @Test
    void setAddSkill() throws CloneNotSupportedException {
        Collaborator c1= new Collaborator("Something", new Date(2000,10,10), new Date(2024,5,1), "Rua", "1111-111", "Porto", "+351919888777", "something.s@email.com", DocType.Type.CitizenCard, 888777444, new JobCategory("Job"));
        Skill skill1= new Skill("skill");
        c1.setAddSkill(skill1);
        assertNotNull(c1.getSkills());
    }

    @Test
    void verifyIfHaveSkill() throws CloneNotSupportedException {
        Collaborator c1= new Collaborator("Something", new Date(2000,10,10), new Date(2024,5,1), "Rua", "1111-111", "Porto", "+351919888777", "something.s@email.com", DocType.Type.CitizenCard, 888777444, new JobCategory("Job"));
        Skill skill1= new Skill("skill");
        c1.setAddSkill(skill1);
        assertTrue(c1.verifyIfHaveSkill(skill1));
    }

    @Test
    void verifyIfDontHaveSkill(){
        Collaborator c1= new Collaborator("Something", new Date(2000,10,10), new Date(2024,5,1), "Rua", "1111-111", "Porto", "+351919888777", "something.s@email.com", DocType.Type.CitizenCard, 888777444, new JobCategory("Job"));
        Skill skill1= new Skill("skill");
        assertFalse(c1.verifyIfHaveSkill(skill1));
    }
    @Test
    void testEqualsSameObject(){
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertEquals(cTest,cTest);
    }
    @Test
    void testEqualsDifferentClass(){
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertNotEquals(cTest,"");
    }
    @Test
    void testEqualsNull(){
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertNotEquals(cTest,null);
    }
    @Test
    void testEqualsDifferentDate(){
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2001,10,29), new Date(2024,01,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertEquals(cTest,cTest2);
    }
    @Test
    void testEqualsDifferentJobCategory(){
        Collaborator cTest = new Collaborator("Joaquim Antonio",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2001,10,29), new Date(2024,01,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Electrician"));
        assertNotEquals(cTest,cTest2);
    }


    @Test
    void verifyNumberWorks(){
        Collaborator cTest = new Collaborator("Joaquim Antonio",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertEquals(cTest.getPhoneNumber(),"+351916835384");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Collaborator cTest2 = new Collaborator("Joaquim Antonio",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","(+351)916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        });

    }




    // COLLABORATOR AC2 verify the docIDNumber
    @Test
    void verifyDocTypeIDNumber(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Manel Mendes Cunha Manuel Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,-1972453213,new JobCategory("Gardener"));
        });
    }

    //COLLABORATOR NAME AC3
    @Test
    void verifyIfCollaboratorNameCanHaveMoreThan6Words(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Manel Mendes Cunha Manuel Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });

    }

    //COLLABORATOR AGE AC4
    @Test
    void verifyIfCollaboratorCanHaveLessThan18years(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2006,6,30), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        });
        Collaborator test = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2006,4,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
    }

    //COLLABORATOR EMAIL AC5
    @Test
    void verifyIfCollaboratorCanHaveBadEmail(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","@gmail", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });
    }
    // COLLABORATOR PHONE NUMBER AC6
    @Test
    void verifyBadPhoneNumber(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","519168384","joaquim@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        });
    }

    // COLLABORATOR VERIFY MINIMUM DATA
    @Test
    void verifyCreateCollaboratorMinimumCriteria(){
       Collaborator c = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351 916835384","joaquim@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        assertNotNull(c);
    }

}