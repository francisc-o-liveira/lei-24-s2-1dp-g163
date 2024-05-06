package pt.ipp.isep.dei.esoft.project.repository.serv;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.List;
import java.util.Optional;

public class GenerateTeamServClass implements GenerateTeamServ{

    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, List<Collaborator> collaboratorsForTeam, String teamName) {
        if(collaboratorsForTeam==null || collaboratorsForTeam.isEmpty()){
            throw new RuntimeException("Dont exist collaborators to generate any Team with this Skills.");
        }
        Optional<Team> teamGenerated = Optional.empty();
        getSkillByOrder(skillsSelected,numberCollabForSkill);
        int[] generatedTrue=getNumberSkill(numberCollabForSkill);
        Team create = new Team(maxSizeTeam,minSizeTeam,skillsSelected,teamName);
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

    public int numberSkillCollab(boolean[][] yesOrNoSkill, int i) {
        int count=0;
        for (int j = 0 ; j < yesOrNoSkill[0].length; j++){
            if(yesOrNoSkill[i][j]==true){
                count++;
            }
        }
        return count;
    }

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

    public boolean teamIsPossible(Team create) {
        return create.isPossible();
    }

    public boolean teamIsCompleted(int[] generatedTrue) {
        for (int i = 0; i < generatedTrue.length; i++) {
            if(generatedTrue[i]>0){
                return false;
            }
        }
        return true;
    }

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
