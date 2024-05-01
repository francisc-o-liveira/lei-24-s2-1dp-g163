package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RegisterSkillController {

    private SkillRepository skillRepository;

    public boolean RegisterSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> newSkill = Repositories.getInstance().getSkillRepository().registerSkill(skillName);
        if (newSkill.isPresent()){
            return true;
        }
        return false;
    }

    public List<Skill> getSkillList(){return Repositories.getInstance().getSkillRepository().getSkillList();}

    public void removeFromList(Skill skillName){
        Repositories.getInstance().getSkillRepository().getSkillList().remove(skillName);
    }

    public ArrayList<DocType.Type> getDocTypeList(){return new ArrayList<>(Arrays.asList(DocType.Type.values()));}

    public boolean loadSkillsByFile(String fileName) throws FileNotFoundException, CloneNotSupportedException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        boolean operationSucess=false;
        boolean operations = true;
        if (!scan.hasNextLine()){
            return false;
        }
        while(scan.hasNextLine()){
            String skillName = scan.nextLine();
            operationSucess=RegisterSkill(skillName);
            if(!operationSucess){
                operations=false;
            }
        }
        return operations;

    }
}
