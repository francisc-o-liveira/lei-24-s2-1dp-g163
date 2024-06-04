package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator.StatusType.Active;
import static pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator.StatusType.NotActive;

/** Repository for Collaborator */
public class CollaboratorRepository {

    /** Variable for the List of Collaborators */
    public List<Collaborator> collaboratorList;

    /** Initializes the list of Collaborators */
    public CollaboratorRepository(){
        collaboratorList=new ArrayList<>();
    }

    /** The method gets the List of Collaborators
     *
     * @return List of Collaborators
     */
    public List<Collaborator> getCollaboratorList(){
        return List.copyOf(collaboratorList);
    }

    /**
     * This method creates and instances a new Collaborator
     * @param name of collaborator
     * @param birthday of collaborator
     * @param admissionDate of collaborator
     * @param address of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity of collaborator
     * @param email of collaborator
     * @param phoneNumber of collaborator
     * @param docType of collaborator
     * @param docIDNumber of collaborator
     * @param jobCategory of collaborator
     *
     * @return Optional of Collaborator if the Collaborator has been successfully created
     */

    public Optional<Collaborator> createCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String email, String phoneNumber, DocType.Type docType, int docIDNumber, JobCategory jobCategory) throws CloneNotSupportedException {
        Optional<Collaborator> newCollab = Optional.empty();
        Collaborator collab = new Collaborator(name,birthday,admissionDate,address,addressZipCode,addressCity,phoneNumber,email,docType,docIDNumber,jobCategory);
        newCollab = verifyCollaboratorExistAndSave(collab);
        return newCollab;
    }

    /** Verifies if Collaborator exists and saves it
     *
     * @param collab - Collaborator to be created
     * @return Optional of Collaborator if it has been added to the Collaborator's List
     * @throws CloneNotSupportedException when Collaborator has already been created
     */

    private Optional<Collaborator> verifyCollaboratorExistAndSave(Collaborator collab) throws CloneNotSupportedException {
        Optional<Collaborator> newCollab = Optional.empty();
        if (!collaboratorList.contains(collab)){
            collaboratorList.add(collab);
            saveFromCollaboratorDataBase(collab);
            newCollab=Optional.of(collab);
        }else{
            throw new CloneNotSupportedException();
        }
        return newCollab;
    }


    /**Verifies if collaborator does not exist
     *
     * @param collaborator to be verified
     * @return true if collaborator does not exist
     */
    private boolean isValidCollaborator(Collaborator collaborator) {
        boolean isValid = !collaboratorList.contains(collaborator);
        return isValid;
    }

    /**Method to get the List of Not Active Collaborators
     *
     * @return List of Not Active Collaborators
     */
    public List<Collaborator> getCollaboratorsNotActive(){
        List<Collaborator> collaboratorNotActive=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==NotActive){
                collaboratorNotActive.add(c);
            }
        }
        return collaboratorNotActive;
    }

    /**
     * Method to change the status of Not Active Collaborators to Active
     *
     * @param team where collaborator has been added to
     * @return
     */

    public boolean activateCollaborators(List<Collaborator> team){
        for(Collaborator c : team){
            if(c.getStatus()==NotActive){
                c.setStatus(Active);
            }else {
                throw new IllegalArgumentException("Collaborator is active");
            }
        }
        return true;
    }


    /**This method gets the List of Collaborators that are not active according to the List of Skills given
     *
     * @param skill of collaborator
     * @return List of Not Active Collaborators with the given skills
     */

    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skill){
        boolean valueCollabAdd ;
        List<Collaborator> collaboratorNotActiveBySkills=new ArrayList<>();
        if (skill==null || skill.isEmpty()){
            return getCollaboratorsNotActive();
        }
            for(Collaborator c : collaboratorList){
                valueCollabAdd = false;
                for(int i=0; i<skill.size(); i++) {
                    if (c.getStatus() == NotActive && c.verifyIfHaveSkill(skill.get(i))) {
                        if(!valueCollabAdd){
                            collaboratorNotActiveBySkills.add(c);
                            valueCollabAdd = true;
                        }
                    }
                }
            }

        sortCollaboratorsByNumberOfSkills(collaboratorNotActiveBySkills);
        return collaboratorNotActiveBySkills;
    }


    /** This method sorts, in ascending order, the List of Collaborators by the number of Skills they have
     *
     * @param collaborators - List of Collaborators
     */

    public void sortCollaboratorsByNumberOfSkills(List<Collaborator> collaborators){
        Collaborator temp;
        for(int i=0; i<collaborators.size()-1; i++){
            for(int j=i+1; j< collaborators.size(); j++){
                if(collaborators.get(i).getNumberOfSkills()<collaborators.get(j).getNumberOfSkills()){
                    temp=collaborators.get(i);
                    collaborators.set(i, collaborators.get(j));
                    collaborators.set(j, temp);
                }
            }
        }
    }

    /** The method searches for the Collaborator by their DocIDNumber
     *
     * @param docIDNumber of collaboraator
     * @return collaborator found
     */

    public Collaborator searchForCollaboratorByIDNumber(int docIDNumber){
        Collaborator collaboratorFound = null;
        List<Collaborator> collaborators=getCollaboratorList();
        for(Collaborator c : collaborators){
            if(c.getDocIDNumber()==docIDNumber){
                collaboratorFound=c;
            }
        }
        return collaboratorFound;
    }

    /** The method searches for the Collaborator by their index on the list
     *
     * @param index of collaboraator
     * @return collaborator
     */

    public Collaborator searchForCollaborator(int index){
        List<Collaborator> collaborators=getCollaboratorList();
        Collaborator collaboratorFound=collaborators.get(index);

        return collaboratorFound;
    }

    /** Gets the list of Skills of Collaborator
     *
     * @param collaborator to see the Skills
     * @return list of Skills the Collaborator has
     */

    public List<Skill> getCollaboratorSkillsList(Collaborator collaborator){
        return collaborator.getSkills();
    }

    /** Assigns a Skill to Collaborator
     *
     * @param collaborator to be assigned a skill
     * @param skillName - skill to be assigned
     * @return Optional of Collaborator if skill has been assigned
     * @throws CloneNotSupportedException if the skill has already been assigned to Collaborator
     */

    public Optional<Collaborator> assignSkill(Collaborator collaborator, Skill skillName) throws CloneNotSupportedException {
        Optional<Collaborator> collabWithSkill = collaborator.setAddSkill(skillName);
        return collabWithSkill;
    }

    /** Removes a collaborator from the list of collaborators
     *
     * @param collaborator to be removed
     */

    public void removeFromList(Collaborator collaborator){
        if(collaboratorList.contains(collaborator)){
            collaboratorList.remove(collaborator);
            removeFromCollaboratorDataBase(collaborator);
        } else {
            throw new RuntimeException("This Collaborator does not exist in the Repository");
        }
    }

    public boolean haveCollaboratorWithEmail(String email) {
        for (Collaborator c : collaboratorList) {
            if (c.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Collaborator getCollaboratorByEmail(String email) {
        for (Collaborator collaborator : collaboratorList) {
            if (collaborator.getEmail().equals(email)) {
                return collaborator;
            }
        }
        throw new RuntimeException("You dont exist in the Repository! Please try again or contact your system administrator");
    }

    public void removeFromCollaboratorDataBase(Collaborator collaborator) {
        List<Collaborator> collaborators = new ArrayList<>();
        Collaborator collaboratorLoad;
        try {
            FileInputStream file = new FileInputStream(MainApp.getCollaboratorDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    collaboratorLoad = (Collaborator) in.readObject();
                    if (!collaboratorLoad.getEmail().equals(collaborator.getEmail())) {
                        collaborators.add(collaboratorLoad);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();




            FileOutputStream fileOut = new FileOutputStream(MainApp.getCollaboratorDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Collaborator collab : collaborators) {
                out.writeObject(collab);
            }
            out.close();
            fileOut.close();

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromCollaboratorDataBase(Collaborator collaborator){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getCollaboratorDataBaseFile(), true);
            ObjectOutputStream out;

            // If the file already has content, we need to use the AppendableObjectOutputStream
            if (file.getChannel().size() > 0) {
                out = new AppendableObjectOutputStream(file);
            } else {
                out = new ObjectOutputStream(file);
            }
            out.writeObject(collaborator);
            out.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void loadFromCollaboratorDataBase(){
        Collaborator collaboratorLoad;
        try {
            FileInputStream file = new FileInputStream(MainApp.getCollaboratorDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    collaboratorLoad = (Collaborator) in.readObject();
                    loadInSystem(collaboratorLoad);
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

    private void loadInSystem(Collaborator collaboratorLoad) throws CloneNotSupportedException {
        verifyCollaboratorExistAndSave(collaboratorLoad);
    }

}


