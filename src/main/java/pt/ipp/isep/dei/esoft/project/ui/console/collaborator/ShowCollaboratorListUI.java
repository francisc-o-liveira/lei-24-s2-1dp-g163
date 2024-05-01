package pt.ipp.isep.dei.esoft.project.ui.console.collaborator;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;

import java.util.List;

public class ShowCollaboratorListUI implements Runnable {

    /** Variable to represent the controller */
    private RegisterCollaboratorController ctrl;

    /** Initializes the controller */
    public ShowCollaboratorListUI(){
        ctrl= new RegisterCollaboratorController();
    }

    /** Gets the controller */

    public RegisterCollaboratorController getController(){
        return ctrl;
    }

    @Override
    public void run(){
        System.out.print("----- Collaborator List -----\n");
        displayCollabList();
    }
    /** Method to display the List of Collaborators */
    public void displayCollabList(){
        List<Collaborator> collaboratorList= getController().getCollaboratorList();
        if(collaboratorList==null){
            System.out.print("No collaborators are registered on the system.");
        } else {
            for(Collaborator c : collaboratorList){
                System.out.println(c+"\n");
            }
        }
    }
}
