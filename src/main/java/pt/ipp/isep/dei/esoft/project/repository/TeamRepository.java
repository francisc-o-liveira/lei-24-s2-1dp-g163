package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {

    private List<Team> teams;

    public TeamRepository(){
        teams = new ArrayList<>();
    }



    public Optional<Team> addTeam(Team team){
        Optional<Team> newTeam = Optional.empty();
        boolean operationSucess = false;
        if(teamIsValid(team)){
            newTeam = Optional.of(team.clone());
            operationSucess = teams.add(newTeam.get());
        }
        if (!operationSucess){
            newTeam = Optional.empty();
        }
        return newTeam;
    }

    private boolean teamIsValid(Team team) {
        boolean isValid = !teams.contains(team);
        return isValid;
    }

    public List<Team> removeTeam(Team team){
        if (teams.contains(team)){
            teams.remove(team);
        }
        return teams;
    }

    public List<Team> getTeamBySkill(List<Skill> skills){
        List<Team> teamWithSkills=new ArrayList<>();
        for(int i=0; i<skills.size(); i++){
            for(Team team : teams){
                if(team.getSkills()==skills.get(i)){
                    teamWithSkills.add(team);
                }
            }
        }
        return teamWithSkills;
    }

    public List<Team> getTeams(){
        return teams;
    }

    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, List<Collaborator> collaboratorsForTeam) {
        Optional<Team> teamGenerated = Optional.empty();
        getSkillByOrder(skillsSelected,numberCollabForSkill);
        int[] generatedTrue=getNumberSkill(numberCollabForSkill);
        Team create = new Team(maxSizeTeam,minSizeTeam,skillsSelected);
            boolean[][] YesOrNoSkill = getCollabOrderWithMoreSkill(skillsSelected,collaboratorsForTeam);
            for (int i = 0; i < YesOrNoSkill.length; i++){
                if(!create.addCollaborator(collaboratorsForTeam.get(i))){
                    throw new RuntimeException("Impossible to create Team with this size");
                }
                for(int h = 0; h < generatedTrue.length; h++){
                    if (YesOrNoSkill[i][h]){
                        generatedTrue[h]--;
                    }
                }
                if(teamIsCompleted(generatedTrue)){
                    break;
                }
            }
            if(teamIsPossible(create)){
                teamGenerated = Optional.of(create);
            }
    return teamGenerated;
    }

    private boolean teamIsPossible(Team create) {
        return create.isPossible();
    }

    private boolean teamIsCompleted(int[] generatedTrue) {
        for (int i = 0; i < generatedTrue.length; i++) {
            if(generatedTrue[i]>0){
                return false;
            }
        }
        return true;
    }

    private int[] getNumberSkill(List<Integer> numberCollabForSkill) {
        int[] numb = new int[numberCollabForSkill.size()];
        for (int i = 0; i < numberCollabForSkill.size(); i++) {
            numb[i]=numberCollabForSkill.get(i);
        }
        return numb;
    }

    private void getSkillByOrder(List<Skill> skillsSelected, List<Integer> numberCollabForSkill) {
        int save;
        Skill saveSkill;
       for(int i = 0; i < skillsSelected.size(); i++){
           for (int j = i +1; j < skillsSelected.size(); j++){
               if(numberCollabForSkill.get(i) < numberCollabForSkill.get(j)){
                   saveSkill=skillsSelected.get(i);
                   save = numberCollabForSkill.get(i);
                   numberCollabForSkill.remove(i);
                   skillsSelected.remove(i);
                   skillsSelected.set(i,skillsSelected.get(j+1));
                   numberCollabForSkill.set(i,numberCollabForSkill.get(j+1));
                   numberCollabForSkill.remove(j);
                   skillsSelected.remove(j);
                   skillsSelected.set(j,saveSkill);
                   numberCollabForSkill.set(j,save);
               }
           }
       }
    }

    private boolean[][] getCollabOrderWithMoreSkill(List<Skill> skillsSelected, List<Collaborator> collaboratorsForTeam) {
        boolean[][] yesOrNoSkill = new boolean[collaboratorsForTeam.size()][skillsSelected.size()];
        int column;
        int line = 0;
        for(Collaborator c : collaboratorsForTeam){
            line++;
            column=0;
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

    private void sortCollabsWithMoreSkill(boolean[][] yesOrNoSkill, List<Collaborator> collaboratorsForTeam) {
        Collaborator save;
        for (int i = 0; i < collaboratorsForTeam.size(); i++){
            for (int j = i + 1; j < collaboratorsForTeam.size(); i++){
                if(numberSkillCollab(yesOrNoSkill, j) > numberSkillCollab(yesOrNoSkill, i)){
                    save=collaboratorsForTeam.get(i);
                    collaboratorsForTeam.remove(i);
                    collaboratorsForTeam.set(i,collaboratorsForTeam.get(j+1));
                    collaboratorsForTeam.remove(j);
                    collaboratorsForTeam.set(j,save);
                }
            }
        }
    }

    private int numberSkillCollab(boolean[][] yesOrNoSkill, int i) {
        int count=0;
        for (int j = 0 ; j < yesOrNoSkill[0].length; j++){
            if(yesOrNoSkill[i][j]==true){
                count++;
            }
        }
        return count;
    }
}
