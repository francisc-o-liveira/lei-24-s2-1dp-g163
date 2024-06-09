package pt.ipp.isep.dei.esoft.project.core.application.repository;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Skills
 */
public class SkillRepository {
    /**
     * List of Skills
     */
    private List<Skill> skillList;
    private File fileToSerialize;

    /**
     * Initialize the list of Skills
     */
    public SkillRepository() {
        try {
            skillList = new ArrayList<>();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to register a Skill
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

    /**
     * Verifies if Skill already exists and saves it
     *
     * @param skill to be verified
     * @return true if Skill did not exist
     * @throws CloneNotSupportedException if Skill already existed
     */
    private boolean verifyIfExistAndSave(Skill skill) throws CloneNotSupportedException {
        boolean operationSuccess = false;
        if (validateSkill(skill)) {

            saveFromSkillInDataBase(skill);
        } else {
            throw new CloneNotSupportedException("This Skill already exists");
        }
        return operationSuccess;
    }

    /**
     * The method gets the List of Skills
     *
     * @return List of Skills
     */
    public List<Skill> getSkillList() {
        return List.copyOf(skillList);
    }

    /**
     * Checks if Skill already exists in the list
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
     *
     * @param skill to be removed from the system
     */

    public void removeSkill(Skill skill) {
        if (skillList.contains(skill)) {
            skillList.remove(skill);
            removeFromSkillDataBase(skill);
        } else {
            throw new RuntimeException("This Skill does not exist in the Repository");
        }
    }

    public void removeFromSkillDataBase(Skill skill) {
        skillList.remove(skill);
       // saveSkills();
    }

    public void saveFromSkillInDataBase(Skill skill) {
        if (!skillList.contains(skill)) {
            skillList.add(skill);
          //  saveSkills();
        }
    }

    private void saveSkills() {
        cleanFile(Bootstrap.getSkillDataBaseFile());
        try (FileOutputStream fileOut = new FileOutputStream(Bootstrap.getSkillDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(skillList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanFile(String skillDataBaseFile) {
        File file = new File(Bootstrap.getSkillDataBaseFile());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromSkillDataBase() throws IOException {
        File file = new File(Bootstrap.getSkillDataBaseFile());
        if (!file.exists()) {
            throw new IOException("The files do not exist.");
        }
        try (FileInputStream fileIn = new FileInputStream(file)){
             if(fileIn.getChannel().size()>0){
                 ObjectInputStream in = new ObjectInputStream(fileIn);
                 List<Skill> skillList = (List<Skill>) in.readObject();
                 loadInSystem(skillList);
            }
        } catch (CloneNotSupportedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<Skill> skills) throws CloneNotSupportedException {
        for (Skill skill : skills) {
            verifyIfExistAndSave(skill);
        }
    }

}
