package pt.ipp.isep.dei.esoft.project.core.application.controller.collaboratorSystem;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.core.application.repository.SkillRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Controller class for registering a Skill.
 */
public class RegisterSkillController {


    private SkillRepository skillRepository;

    /**
     * Constructs a RegisterSkillController object.
     */
    private RegisterSkillController(){
        skillRepository=Repositories.getInstance().getSkillRepository();
    }

    /**
     * Registers a new skill with the provided name.
     *
     * @param skillName The name of the skill to register.
     * @return True if the skill is successfully registered, otherwise false.
     * @throws CloneNotSupportedException if there is an issue cloning the skill.
     */
    public boolean RegisterSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> newSkill = Repositories.getInstance().getSkillRepository().registerSkill(skillName);
        if (newSkill.isPresent()){
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of all registered skills.
     *
     * @return A list of all registered skills.
     */
    public List<Skill> getSkillList(){return Repositories.getInstance().getSkillRepository().getSkillList();}

    /**
     * Removes a skill from the list of registered skills.
     *
     * @param skillName The skill to remove.
     */
    public void removeFromList(Skill skillName){
        skillRepository.removeSkill(skillName);
    }

    /**
     * Retrieves a list of document types.
     *
     * @return An ArrayList of document types.
     */
    public ArrayList<DocType.Type> getDocTypeList(){return new ArrayList<>(Arrays.asList(DocType.Type.values()));}

    /**
     * Loads skills from a file.
     *
     * @param fileName The name of the file containing skills.
     * @return True if the skills are successfully loaded, otherwise false.
     * @throws FileNotFoundException if the file is not found.
     * @throws CloneNotSupportedException if there is an issue cloning the skill.
     */
    public boolean loadSkillsByFile(String fileName) throws FileNotFoundException, CloneNotSupportedException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        boolean operationSuccess = false;
        boolean operations = true;
        if (!scan.hasNextLine()){
            return false;
        }
        while(scan.hasNextLine()){
            String skillName = scan.nextLine();
            operationSuccess = RegisterSkill(skillName);
            if(!operationSuccess){
                operations = false;
            }
        }
        return operations;
    }

    private static RegisterSkillController instance;

    public static RegisterSkillController getInstance(){
        if(instance == null){
            synchronized (RegisterSkillController.class) {
                instance = new RegisterSkillController();
            }
        }
        return instance;
    }
}
