package pt.ipp.isep.dei.esoft.project.core.application.repository;

import pt.ipp.isep.dei.esoft.project.core.application.repository.*;

import java.io.*;

/**
 * The Repositories class manages repositories for various entities in the system.
 */
public class Repositories implements Serializable {

    private static Repositories instance;
    private Organization organizationRepository;
    private EntryRepository entryRepository;
    private AuthenticationRepository authenticationRepository;
    private JobCategoryRepository jobCategoryRepository;
    private CollaboratorRepository collaboratorRepository;
    private TeamRepository teamRepository;
    private SkillRepository skillRepository;
    private VehicleRepository vehicleRepository;

    private Repositories() throws Exception {
        organizationRepository = new Organization();
        authenticationRepository = new AuthenticationRepository();
        skillRepository = new SkillRepository();
        jobCategoryRepository = new JobCategoryRepository();
        teamRepository = new TeamRepository();
        collaboratorRepository = new CollaboratorRepository();
        vehicleRepository = new VehicleRepository();
        entryRepository = new EntryRepository();
    }

    /**
     * Retrieves the singleton instance of Repositories.
     *
     * @return The singleton instance of Repositories.
     */
    public static Repositories getInstance() throws Exception {
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

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    /**
     * Loads the system state from a binary file.
     *
     * @param file The binary file to load the system state from.
     * @throws RuntimeException if an error occurs while loading the system state.
     */
    public void loadSystemStateFromBinary(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Repositories repObject = (Repositories) in.readObject();

            organizationRepository = repObject.getOrganizationRepository();
            entryRepository = repObject.getEntryRepository();
            authenticationRepository = repObject.getAuthenticationRepository();
            skillRepository = repObject.getSkillRepository();
            vehicleRepository = repObject.getVehicleRepository();
            jobCategoryRepository = repObject.getJobCategoryRepository();
            collaboratorRepository = repObject.getCollaboratorRepository();
            teamRepository = repObject.getTeamRepository();

            in.close();
            fileInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the system state to a binary file.
     *
     * @param file The binary file to save the system state to.
     * @throws RuntimeException if an error occurs while saving the system state.
     */
    public void saveSystemStateToBinary(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(instance);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
