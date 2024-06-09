package pt.ipp.isep.dei.esoft.project.core.application.repository.serv;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;

import java.util.List;
import java.util.Optional;

public class GenerateTeamServClass implements GenerateTeamServ{
    /**
     * Generates a team based on the provided criteria.
     *
     * @param minSizeTeam           The minimum size of the team.
     * @param maxSizeTeam           The maximum size of the team.
     * @param skillsSelected        The list of skills selected for the team.
     * @param numberCollabForSkill The number of collaborators required for each skill.
     * @param collaboratorsForTeam  The list of collaborators available for forming the team.
     * @param teamName              The name of the team.
     * @return An Optional containing the generated team if successful, empty otherwise.
     * @throws RuntimeException if there are no collaborators to generate a team with the provided skills.
     * @throws RuntimeException if it's impossible to create a team with the given size.
     * @throws RuntimeException if it's impossible to create a team with the given data.
     */
    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, List<Collaborator> collaboratorsForTeam, String teamName) {
        if(collaboratorsForTeam==null || collaboratorsForTeam.isEmpty()){
            throw new RuntimeException("Dont exist collaborators to generate any Team with this Skills.");
        }
        Optional<Team> teamGenerated = Optional.empty();
        getSkillByOrder(skillsSelected,numberCollabForSkill);
        int[] generatedTrue=getNumberSkill(numberCollabForSkill);
        Team create = new Team(minSizeTeam,maxSizeTeam,skillsSelected,teamName);
        boolean[][] YesOrNoSkill = getCollabOrderWithMoreSkill(skillsSelected,collaboratorsForTeam);
        for (int i = 0; i < YesOrNoSkill.length; i++){
            if(collabororatorUpgradeTeam(YesOrNoSkill[i], generatedTrue)){
                if(!create.addCollaborator(collaboratorsForTeam.get(i))){
                    throw new RuntimeException("Impossible to create Team with this size");
                }
                for(int h = 0; h < generatedTrue.length; h++){
                    if (YesOrNoSkill[i][h]){
                        generatedTrue[h]--;
                    }
                }
            }
            if(teamIsCompleted(generatedTrue) && create.getSize()>minSizeTeam){
                break;
            }
        }
        if(teamIsPossible(create)){
            teamGenerated = Optional.of(create);
        }else{
            throw new RuntimeException("Team impossible to create with this data");
        }
        return teamGenerated;
    }

    /**
     * Creates a matrix indicating whether each collaborator has the required skills for the team.
     *
     * @param skillsSelected       The list of skills selected for the team.
     * @param collaboratorsForTeam The list of collaborators available for forming the team.
     * @return A boolean matrix indicating whether each collaborator has the required skills.
     */
    public boolean[][] getCollabOrderWithMoreSkill(List<Skill> skillsSelected, List<Collaborator> collaboratorsForTeam) {
        boolean[][] yesOrNoSkill = new boolean[collaboratorsForTeam.size()][skillsSelected.size()];
        int column;
        int line = -1;
        for(Collaborator c : collaboratorsForTeam){
            line++;
            column=-1;
            for (Skill s : skillsSelected){
                column++;
                if(c.verifyIfHaveSkill(s)){
                    yesOrNoSkill[line][column]=true;
                }
            }
        }
        sortCollabsWithMoreSkill(yesOrNoSkill,collaboratorsForTeam);
        return yesOrNoSkill;
    }
    /**
     * Sorts the list of collaborators based on the number of skills they possess.
     *
     * @param yesOrNoSkill          The boolean matrix indicating whether each collaborator has the required skills.
     * @param collaboratorsForTeam  The list of collaborators available for forming the team.
     */
    public void sortCollabsWithMoreSkill(boolean[][] yesOrNoSkill, List<Collaborator> collaboratorsForTeam) {
        Collaborator save;
        for (int i = 0; i < collaboratorsForTeam.size(); i++){
            for (int j = i + 1; j < collaboratorsForTeam.size(); j++){
                if(numberSkillCollab(yesOrNoSkill, j) > numberSkillCollab(yesOrNoSkill, i)){
                    save=collaboratorsForTeam.get(i);
                    collaboratorsForTeam.remove(i);
                    collaboratorsForTeam.set(i,collaboratorsForTeam.get(j-1));
                    collaboratorsForTeam.remove(j);
                    collaboratorsForTeam.set(j,save);
                }
            }
        }
    }
    /**
     * Calculates the number of skills a collaborator possesses.
     *
     * @param yesOrNoSkill The boolean array indicating whether each collaborator has the required skills.
     * @param i            The index of the collaborator in the array.
     * @return The number of skills the collaborator possesses.
     */
    public int numberSkillCollab(boolean[][] yesOrNoSkill, int i) {
        int count=0;
        for (int j = 0 ; j < yesOrNoSkill[0].length; j++){
            if(yesOrNoSkill[i][j]==true){
                count++;
            }
        }
        return count;
    }
    /**
     * Checks if a collaborator can upgrade the team.
     *
     * @param yesOrNoSkill The boolean array indicating whether each collaborator has the required skills.
     * @param generatedTrue An array indicating the number of collaborators needed for each skill.
     * @return True if the collaborator can upgrade the team, false otherwise.
     */
    public boolean collabororatorUpgradeTeam(boolean[] yesOrNoSkill, int[] generatedTrue) {
        if (generatedTrue.length==0){
            return true;
        }
        for (int i = 0; i < generatedTrue.length; i++){
            if(generatedTrue[i]>0 && yesOrNoSkill[i]){
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a team is completed.
     *
     * @return True if the team is completed, false otherwise.
     */
    public boolean teamIsPossible(Team create) {
        return create.isPossible();
    }
    /**
     * Checks if a team is completed.
     *
     * @param generatedTrue An array indicating the number of collaborators needed for each skill.
     * @return True if the team is completed, false otherwise.
     */
    public boolean teamIsCompleted(int[] generatedTrue) {
        for (int i = 0; i < generatedTrue.length; i++) {
            if(generatedTrue[i]>0){
                return false;
            }
        }
        return true;
    }
    /**
     * Retrieves the number of collaborators needed for each skill.
     *
     * @param numberCollabForSkill The list of numbers of collaborators for each skill.
     * @return An array containing the number of collaborators needed for each skill.
     */
    public int[] getNumberSkill(List<Integer> numberCollabForSkill) {
        int[] numb = new int[0];
        if (numberCollabForSkill!=null){
            numb = new int[numberCollabForSkill.size()];
            for (int i = 0; i < numberCollabForSkill.size(); i++) {
                numb[i]=numberCollabForSkill.get(i);
            }
        }
        return numb;
    }
    /**
     * Orders the skills and their corresponding number of collaborators by descending order of the number of collaborators.
     *
     * @param skillsSelected        The list of skills selected for the team.
     * @param numberCollabForSkill The list of numbers of collaborators for each skill.
     */
    public void getSkillByOrder(List<Skill> skillsSelected, List<Integer> numberCollabForSkill) {
        int save;
        Skill saveSkill;
        for(int i = 0; i < skillsSelected.size(); i++){
            for (int j = i +1; j < skillsSelected.size(); j++){
                if(numberCollabForSkill.get(i) < numberCollabForSkill.get(j)){
                    saveSkill=skillsSelected.get(i);
                    save = numberCollabForSkill.get(i);
                    numberCollabForSkill.remove(i);
                    skillsSelected.remove(i);
                    skillsSelected.set(i,skillsSelected.get(j-1));
                    numberCollabForSkill.set(i,numberCollabForSkill.get(j-1));
                    numberCollabForSkill.remove(j);
                    skillsSelected.remove(j);
                    if(j==numberCollabForSkill.size()){
                        skillsSelected.add(saveSkill);
                        numberCollabForSkill.add(save);
                    }
                    skillsSelected.set(j,saveSkill);
                    numberCollabForSkill.set(j,save);
                }
            }
        }
    }

}
