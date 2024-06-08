package pt.ipp.isep.dei.esoft.project.core.application.mapper;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * The TeamMapper class is responsible for mapping between Team and TeamDto objects.
 */
public class TeamMapper {

    /**
     * Converts a list of Team objects to a list of TeamDto objects.
     *
     * @param teamList The list of Team objects to be converted.
     * @return The list of converted TeamDto objects.
     */
    public List<TeamDto> teamListToTeamDtoList(List<Team> teamList) {
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team team : teamList) {
            teamDtoList.add(teamToTeamDto(team));
        }
        return teamDtoList;
    }

    /**
     * Converts a Team object to a TeamDto object.
     *
     * @param team The Team object to be converted.
     * @return The converted TeamDto object.
     */
    public TeamDto teamToTeamDto(Team team) {
        return new TeamDto(team.getTeamList(), team.getSkills(), team.getTeamName());
    }

    public Team toDomain(TeamDto teamDto){
        return new Team(teamDto.getTeamList(),teamDto.getSkillsSelected(),100,0,teamDto.getTeamName());
    }

    /**
     * Converts a TeamDto object to a Team object.
     *
     * @param teamDto The TeamDto object to be converted.
     * @return The converted Team object.
     * @throws RuntimeException if the corresponding Team object is not found.
     */
    public Team teamDtoToTeam(TeamDto teamDto) {
        for (Team t : Repositories.getInstance().getTeamRepository().getTeams()) {
            if (teamDto.getTeamList().equals(t.getTeamList()) && teamDto.getTeamName().equals(t.getTeamName())) {
                return t;
            }
        }
        throw new RuntimeException("Team not found - Fatal error");
    }
}
