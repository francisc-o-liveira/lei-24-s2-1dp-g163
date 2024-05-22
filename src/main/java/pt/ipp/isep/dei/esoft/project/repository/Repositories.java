package pt.ipp.isep.dei.esoft.project.repository;

public class Repositories {

    private static Repositories instance;
    private final Organization organizationRepository;
    private final EntryRepository entryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;
    private final VehicleRepository vehicleRepository;

    private Repositories() {
        organizationRepository = new Organization();

        authenticationRepository = new AuthenticationRepository();
        skillRepository = new SkillRepository();
        jobCategoryRepository = new JobCategoryRepository();
        teamRepository = new TeamRepository();
        collaboratorRepository = new CollaboratorRepository();
        vehicleRepository = new VehicleRepository();
        entryRepository = new EntryRepository();
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

    public EntryRepository getEntryRepository() {
        return entryRepository;
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
