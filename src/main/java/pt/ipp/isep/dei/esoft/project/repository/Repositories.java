package pt.ipp.isep.dei.esoft.project.repository;

import java.io.*;

public class Repositories implements Serializable{

    private static Repositories instance;
    private Organization organizationRepository;
    private EntryRepository entryRepository;
    private AuthenticationRepository authenticationRepository;
    private JobCategoryRepository jobCategoryRepository;
    private CollaboratorRepository collaboratorRepository;
    private TeamRepository teamRepository;
    private SkillRepository skillRepository;
    private VehicleRepository vehicleRepository;

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



    public void loadSystemStateFromBinary(File file){
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

    public void saveSystemStateToBinary(File file){
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
