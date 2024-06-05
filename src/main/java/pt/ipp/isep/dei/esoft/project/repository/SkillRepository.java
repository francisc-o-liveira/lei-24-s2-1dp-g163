package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Repository for Skills */
public class SkillRepository {
    /** List of Skills */
    private List<Skill> skillList;
    /** Initialize the list of Skills */
    public SkillRepository(){
        skillList = new ArrayList<>();
        loadFromSkillDataBase();
    }

    /** Method to register a Skill
     *
     * @param skillName - skill to be registered
     * @return Optional of Skill if Skill has been registered
     * @throws CloneNotSupportedException if Skill already exists
     */
    public Optional<Skill> registerSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> optionalValue = Optional.empty();
        Skill skill = new Skill(skillName);
        if (verifyIfExistAndSave(skill)) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;

    }

    /** Verifies if Skill already exists and saves it
     *
     * @param skill to be verified
     * @return true if Skill did not exist
     * @throws CloneNotSupportedException if Skill already existed
     */
    private boolean verifyIfExistAndSave(Skill skill) throws CloneNotSupportedException {
        boolean operationSuccess = false;
        if (validateSkill(skill)) {
            operationSuccess = skillList.add(skill);
                saveFromSkillInDataBase(skill);
        }else {
            throw new CloneNotSupportedException("This Skill already exists");
        }
        return operationSuccess;
    }

    /** The method gets the List of Skills
     *
     * @return List of Skills
     */
    public List<Skill> getSkillList(){
        return List.copyOf(skillList);
    }

    /** Checks if Skill already exists in the list
     *
     * @param skill to be checked
     * @return true if Skill does not exist on the list
     */
    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }

    /**
     * This method remove the skill selected by user
     * @param skill to be removed from the system
     */

    public void removeSkill(Skill skill){
        if(skillList.contains(skill)){
            skillList.remove(skill);
            removeFromSkillDataBase(skill);
        }else{
            throw new RuntimeException("This Skill does not exist in the Repository");
        }
    }






    public void removeFromSkillDataBase(Skill skill) {
        try {
            FileOutputStream fileOut = new FileOutputStream(MainApp.getSkillDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
           if (!skillList.contains(skill)) {
               out.writeObject(skillList);
           }
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromSkillInDataBase(Skill skill){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getSkillDataBaseFile());
            ObjectOutputStream out;
            // If the file already has content, we need to use the AppendableObjectOutputStream
            out = new AppendableObjectOutputStream(file);
            if (skillList.contains(skill)) {
                out.writeObject(skillList);
            }
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromSkillDataBase(){
        List<Skill> skillLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getSkillDataBaseFile());
            if (file.getChannel().size() > 0){
                ObjectInputStream in = new ObjectInputStream(file);
                while (true) {
                    try {
                        skillLoaded = (List<Skill>) in.readObject();
                        if (skillLoaded != null) {
                            loadInSystem(skillLoaded);
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            }
            file.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<Skill> skills) throws CloneNotSupportedException {
        for (Skill skill1 : skills) {
            verifyIfExistAndSave(skill1);
        }
    }

}
