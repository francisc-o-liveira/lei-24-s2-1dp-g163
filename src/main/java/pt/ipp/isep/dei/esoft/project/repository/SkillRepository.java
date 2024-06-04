package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

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

    public boolean isSkillSelected(Skill skill) {
        return skill.selectedSkill().get();
    }

    public boolean isSkillSelectedForTeam(Skill s) {
        return s.selectedSkillForTeam().get();
    }





    public void removeFromSkillDataBase(Skill skill) {
        List<Skill> skillList = new ArrayList<>();
        Skill skillLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getSkillDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    skillLoaded = (Skill) in.readObject();
                    if (!skillLoaded.equals(skill)) {
                        skillList.add(skillLoaded);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();

            FileOutputStream fileOut = new FileOutputStream(MainApp.getSkillDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Skill skillSave : skillList) {
                out.writeObject(skillSave);
            }
            out.close();
            fileOut.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromSkillInDataBase(Skill skill){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getSkillDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(skill);
            out.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromSkillDataBase(){
        Skill skillLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getSkillDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    skillLoaded = (Skill) in.readObject();
                    loadInSystem(skillLoaded);
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(Skill skill) throws CloneNotSupportedException {
        verifyIfExistAndSave(skill);
    }

}
