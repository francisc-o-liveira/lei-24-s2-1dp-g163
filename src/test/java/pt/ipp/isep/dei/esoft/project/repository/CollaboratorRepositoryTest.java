package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorRepositoryTest {


    // Collaborator AC1
    @Test
    void verifyIfCollaboratorExists() throws CloneNotSupportedException { // verify if exist and if not exist
        CollaboratorRepository repo = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 197232131;
        Optional<Collaborator> collab=repo.createCollaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","joaquim.cunha@gmail.com", "+351916835384",DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        Collaborator cTest=new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses", "+351916835384","joaquim.cunha@gmail.com",DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));

        Exception exception = assertThrows(CloneNotSupportedException.class, () -> {
            repo.verifyCollaboratorExistAndSave(cTest);
        });
    }


    @Test
    void activateCollaborators() {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        String name="";

        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835383","jouim.cunha@gmail.com", DocType.Type.CitizenCard,123131231,new JobCategory("Gardener"));
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,838742392,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835382","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,873247823,new JobCategory("Gardener"));
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
        assertEquals(cTest3.getStatus(), Collaborator.StatusType.NotActive);
    }

    @Test
    void getCollaboratorsNotActiveBySkills() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 197232131;
        int docIDNumber2=197321131;
        Skill newSkill = new Skill("podar");
        Skill newSkill2 = new Skill("carta de ligeiros");
        Optional<Collaborator> collab1=rep.createCollaborator("Joaquim",new Date(2002,10,29), new Date(2024,4,29),"Rua Das Rosas","4630-131","Marco de Canaveses","jouim.cunha@gmail.com","+351916835384", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        rep.assignSkill(collab1.get(),newSkill);
        Optional<Collaborator> collab2=rep.createCollaborator("Joaquim M",new Date(2005,10,29), new Date(2024,4,29),"Rua Das Rosas","4630-131","Marco de Canaveses","joaquim.cunha@gmail.com", "+351916323234",DocType.Type.CitizenCard,docIDNumber2,new JobCategory("Garder"));
        rep.assignSkill(collab2.get(),newSkill2);
        rep.assignSkill(collab2.get(),newSkill);
        List<Skill> skillSet = new ArrayList<>();


        skillSet.add(newSkill);
        skillSet.add(newSkill2);


        List<Collaborator> expectedResult = new ArrayList<>();
        expectedResult.add(collab2.get());
        expectedResult.add(collab1.get());


        List<Collaborator> result = rep.getCollaboratorsNotActiveBySkills(skillSet);

        assertEquals(result,expectedResult);
    }
    @Test
    void sortCollaboratorsByNumberOfSkills() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 197232133;
        Skill newSkill = new Skill("podar");
        Skill newSkill2 = new Skill("carta de ligeiros");
        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        docIDNumber = 197432131;
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Garder"));
        docIDNumber = 197233131;
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        docIDNumber = 197232132;

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
    void searchForCollaboratorByIDNumber() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 197232131;
        Optional<Collaborator> collab=rep.createCollaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","joaquim.cunha@gmail.com", "+351916835384",DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));

        assertEquals(collab.get(),rep.searchForCollaboratorByIDNumber(docIDNumber));
    }

    @Test
    void searchForCollaborator() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        int docIDNumber = 197232131;
        Optional<Collaborator> collab=rep.createCollaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","joaquim.cunha@gmail.com", "+351916835384",DocType.Type.CitizenCard,docIDNumber,new JobCategory("Gardener"));
        assertEquals(collab.get(), rep.searchForCollaborator(0));
    }

    @Test
    void getCollaboratorSkillsList() throws CloneNotSupportedException {
        CollaboratorRepository rep = Repositories.getInstance().getCollaboratorRepository();
        List<Skill> expectedSkills = new ArrayList<Skill>();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
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
        Collaborator cTest = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        Skill skillToAdd = new Skill("carta de ligeiros");
        rep.assignSkill(cTest,skillToAdd);
        List<Skill> collabSkills = cTest.getSkills();
        assertTrue(collabSkills.contains(skillToAdd));
    }
}