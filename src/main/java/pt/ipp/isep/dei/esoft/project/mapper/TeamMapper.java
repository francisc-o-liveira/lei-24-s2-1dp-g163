package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class TeamMapper {


    public List<TeamDto> teamListToTeamDtoList(List<Team> teamList) {
        List<TeamDto> teamDtoList = new ArrayList<TeamDto>();
        for (Team team : teamList) {
            teamDtoList.add(teamToTeamDto(team));
        }
        return teamDtoList;
    }

    // need to be implemented
    private TeamDto teamToTeamDto(Team team) {
       return new TeamDto();
    }

    public Team teamDtoToTeam(TeamDto teamDto) {
        for (Team t : Repositories.getInstance().getTeamRepository().getTeams()){
            if (teamDto.equals(t)){
                return t;
            }
        }
        throw new RuntimeException("Team not found - Fatal error");
    }
}
