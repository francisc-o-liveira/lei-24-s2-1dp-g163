package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorRepositoryTest {


    // Collaborator AC1
    @Test
    void verifyIfCollaboratorExists() { // verify if exist and if not exist !
        CollaboratorRepository repo = Repositories.getInstance().getCollaboratorRepository();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,863473624,new JobCategory("Gardener"));
        Collaborator cTestEqual = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,863473624,new JobCategory("Gardener"));

        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,743626422,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,376432422,new JobCategory("Gardener"));
        repo.getCollaboratorList().add(cTest);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            repo.getCollaboratorList().add(cTestEqual);
        });
    }


    @Test
    void activateCollaborators() {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        String name="";

        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,1231312312,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,838742392,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,873247823,new JobCategory("Gardener"));
        List<Collaborator> expectedResult = new ArrayList<>();
        List<Skill> skillSet = new ArrayList<>();
        Team team = new Team(4,1,skillSet,name);

        team.addCollaborator(cTest);
        team.addCollaborator(cTest2);
        team.addCollaborator(cTest3);

        expectedResult.add(cTest);
        expectedResult.add(cTest2);

        rep.activateCollaborators(expectedResult);
        assertEquals(cTest.getStatus(), Collaborator.StatusType.Active);
        assertEquals(cTest2.getStatus(), Collaborator.StatusType.Active);
        assertEquals(cTest3.getStatus(), Collaborator.StatusType.Active);
    }

    @Test
    void getCollaboratorsNotActiveBySkills() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 1972321313;
        Skill newSkill = new Skill("podar");
        Skill newSkill2 = new Skill("carta de ligeiros");
        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        List<Skill> skillSet = new ArrayList<>();


        skillSet.add(newSkill);
        skillSet.add(newSkill2);

        cTest.setAddSkill(newSkill);
        cTest.setAddSkill(newSkill2);

        cTest2.setAddSkill(newSkill2);


        List<Collaborator> expectedResult = new ArrayList<>();
        expectedResult.add(cTest);
        expectedResult.add(cTest2);

        List<Collaborator> result = rep.getCollaboratorsNotActiveBySkills(skillSet);

        assertEquals(result,expectedResult);
    }

    @Test
    void sortCollaboratorsByNumberOfSkills() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 1972321313;
        Skill newSkill = new Skill("podar");
        Skill newSkill2 = new Skill("carta de ligeiros");
        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));

        cTest.setAddSkill(newSkill);
        cTest.setAddSkill(newSkill2);
        cTest2.setAddSkill(newSkill2);
        ArrayList<Collaborator> sortToMake = new ArrayList<>();
        ArrayList<Collaborator> expectedResult = new ArrayList<>();
        sortToMake.add(cTest);
        sortToMake.add(cTest2);
        sortToMake.add(cTest3);
        expectedResult.add(cTest);
        expectedResult.add(cTest2);
        expectedResult.add(cTest3);
        rep.sortCollaboratorsByNumberOfSkills(sortToMake);
        assertEquals(sortToMake,expectedResult);
    }

    @Test
    void searchForCollaboratorByIDNumber() {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 1972321313;
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        rep.getCollaboratorList().add(cTest);
        assertEquals(cTest,rep.searchForCollaboratorByIDNumber(docIDNumber));
    }

    @Test
    void searchForCollaborator() {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        rep.getCollaboratorsNotActive().add(cTest);
        assertEquals(cTest, rep.searchForCollaborator(0));
    }

    @Test
    void getCollaboratorSkillsList() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        List<Skill> expectedSkills = new ArrayList<Skill>();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        Skill skillToAdd = new Skill("carta de ligeiros");
        expectedSkills.add(skillToAdd);
        rep.assignSkill(cTest,skillToAdd);
        List<Skill> collabSkills = cTest.getSkills();
        assertEquals(collabSkills,expectedSkills);
    }





    // US004 - Assign a Skill to a Collaborator
    @Test
    void assignSkill() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        Skill skillToAdd = new Skill("carta de ligeiros");
        rep.assignSkill(cTest,skillToAdd);
        List<Skill> collabSkills = cTest.getSkills();
        assertTrue(collabSkills.contains(skillToAdd));
    }
}