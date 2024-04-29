package pt.ipp.isep.dei.esoft.project.repository;

public class Repositories {

    private static Repositories instance;
    private final Organization organizationRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;
    private final VehicleRepository vehicleRepository;

    private Repositories() {
        organizationRepository = new Organization();
        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        skillRepository = new SkillRepository();
        jobCategoryRepository = new JobCategoryRepository();
        teamRepository = new TeamRepository();
        collaboratorRepository = new CollaboratorRepository();
        vehicleRepository = new VehicleRepository();

    }

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    public Organization getOrganizationRepository() {
        return organizationRepository;
    }

    public TaskCategoryRepository getTaskCategoryRepository() {
        return taskCategoryRepository;
    }

    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return collaboratorRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public JobCategoryRepository getJobCategoryRepository() {
        return jobCategoryRepository;
    }

    public VehicleRepository getVehicleRepository() {return vehicleRepository;}
}
