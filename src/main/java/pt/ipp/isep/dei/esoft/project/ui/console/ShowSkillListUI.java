package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.List;

public class ShowSkillListUI implements Runnable{

    private RegisterSkillController ctrl;

    public ShowSkillListUI(){
        ctrl = new RegisterSkillController();
    }

    public RegisterSkillController getCtrl() {
        return ctrl;
    }

    @Override
    public void run() {
        System.out.println("----- Skill List -----\n");
        showDataAsked();
    }

    private void showDataAsked() {
        List<Skill> skillList = getCtrl().getSkillList();
        System.out.println();
        if (skillList == null) {
            System.out.println("No Skills are registered on the system.");
        }else{
            for (Skill s : skillList){
                System.out.println(s);
            }
        }
    }
}
