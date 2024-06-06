package pt.ipp.isep.dei.esoft.project.ui.console.skill;

import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.AssignSkillsController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** This class represents the UI to assign a skill (or skills) to a collaborator*/
public class AssignSkillsUI implements Runnable {

    /**The parameters needed to assign skills to a collaborator
     */
    private Collaborator collaborator;
    private Skill skillName;

    /**The controller */
    private AssignSkillsController ctrl;

    /** Initializes the controller
     *
     */
    public AssignSkillsUI(){
        ctrl= AssignSkillsController.getInstance();
    }

    /** Gets the controller
     *
     * @return controller
     */
    public AssignSkillsController getController(){
        return ctrl;
    }

    @Override
    public void run(){
        try {
            System.out.print("--------- Assignment of Skills ---------\n");
            ctrl.getDataNeededToAssign();
            displayCollaboratorList();
            collaborator=setCollaboratorToAssign();
            skillName = displayAndSelectSkillToAssign();
            submitsData();
        }catch (CloneNotSupportedException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    /** Submits the data if the skill was successfully added
     *
     */

    private void submitsData() throws CloneNotSupportedException {
        Optional<Collaborator> CollaboratorSkillAdded =getController().assignSkills(collaborator,skillName);
        if(CollaboratorSkillAdded.isPresent()){
            System.out.print("\nSkill added!\n");
        } else {
            System.out.print("\nSkill was not added\n");
        }
    }

    /**
     * This method displays and lets the user select the skills to assign to Collaborator
     *
     * @return the skill selected by the user to assign to the collaborator
     */
    public Skill displayAndSelectSkillToAssign(){
        List<Skill> skillsToAssign=ctrl.filterSkillsToAssign(collaborator);
        Skill skillSelected = null;
        while(skillSelected==null) {
            skillSelected = skillsToAssign.get(Utils.showAndSelectIndex(skillsToAssign, "Choose the skills to assign:"));
        }
        return skillSelected;

    }


    /** Displays the list of Collaborators
     *
     */
    public void displayCollaboratorList(){
        System.out.printf("------Select a collaborator by its index----- %n");
        System.out.printf("Collaborators: %n");
        for(int i=0; i<ctrl.getCollaboratorList().size(); i++){
            System.out.printf("%d -- %s%n", i+1, ctrl.getCollaboratorList().get(i));
        }
    }

    /** Gets the collaborator according to the index given by the user
     *
     * @return collaborator
     */
    public Collaborator setCollaboratorToAssign(){
        Scanner scan= new Scanner(System.in);
        boolean validIndex=false;
        int indexCollaborator=0;
        while(!validIndex){
            indexCollaborator=scan.nextInt()-1;
            if(indexCollaborator<0 || indexCollaborator >ctrl.getCollaboratorList().size()-1){
                throw new IllegalArgumentException("Invalid indexCollaborator. Try again");
            } else {
                validIndex=true;
            }
        }
        return ctrl.getCollaborator(indexCollaborator);
    }
}