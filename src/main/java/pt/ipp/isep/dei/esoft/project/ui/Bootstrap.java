package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.core.application.repository.*;

import java.io.File;
import java.io.IOException;

public class Bootstrap {

    private static Bootstrap instance;

    private static File saveDirectory;

    public static void setSaveDirectory(File saveDirectory) {
        Bootstrap.saveDirectory = saveDirectory;
    }

    public static Bootstrap getInstance() {
        if (instance == null) {
            synchronized (Bootstrap.class) {
                instance = new Bootstrap();
            }
        }
        return instance;
    }

    public Bootstrap() {
        saveDirectory = null;
    }

    //Add some task categories to the repository as bootstrap
    public void run() {
        try {
            addSkills();
            addJobCategories();
            addCollaborators();
            addVehicles();
            addEntries();
            addOrganization();
            addUsers();
        } catch (Exception e){
            System.out.print("erro inicializando");
        }
    }
    private void addSkills() throws Exception {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.loadFromSkillDataBase();
    }
    private void addJobCategories() throws Exception {
        JobCategoryRepository jobCategoryRepository = Repositories.getInstance().getJobCategoryRepository();
    }
    private void addCollaborators() throws Exception {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
    }

    private void addVehicles() throws Exception {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
    }
    private void addOrganization() throws Exception {
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.loadSystem();
        organizationRepository.addManager("ADMIN","GSM","+351910000000","admin@this.app");
    }
    private void addUsers() throws Exception {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.loadFromAuthDataBase();
    }
    private void addEntries() throws Exception {
        EntryRepository entryRepository=Repositories.getInstance().getEntryRepository();
    }


    private static String getFilePath(String fileName) {
        if (saveDirectory == null) {
            // Get the current working directory
            String currentDir = System.getProperty("user.dir");
            // Construct the path to the team database file
            String relativePath = "\\target\\classes\\DataBase\\";
            // Combine the current directory with the relative path
            String fullPath = currentDir + relativePath + fileName;
            return fullPath;
        }else {
            String fullPath;

            if (saveDirectory.getAbsolutePath().charAt(saveDirectory.getAbsolutePath().length() - 1) == File.separatorChar) {
                fullPath = saveDirectory.getAbsolutePath()  + fileName;
            }else{
                fullPath = saveDirectory.getAbsolutePath() + File.separator + fileName;
            }
            File testFile = new File(fullPath);
            if (testFile.exists()){
                return fullPath;
            }else {
                try {
                    testFile.createNewFile();
                } catch (IOException e) {
                    System.out.println("Error creating file on directory");
                }

            }
            return fullPath;
        }
    }

    private static String authDataBaseFile = new String("authDataBase.csv");

    public static File getAuthDataBaseFile() {
        return new File(getFilePath(authDataBaseFile));
    }

    private static String collaboratorDataBaseFile = new String("collaboratorDataBase.csv");

    public static String getCollaboratorDataBaseFile() {
        return getFilePath(collaboratorDataBaseFile);
    }

    private static String jobCategoryDataBaseFile = new String("jobCategoryDataBase.csv");

    public static String getJobCategoryDataBaseFile() {
        return getFilePath(jobCategoryDataBaseFile);
    }

    private static String skillDataBaseFile = new String("skillDataBase.csv");

    public static String getSkillDataBaseFile() {
        return getFilePath(skillDataBaseFile);
    }

    private static String entryDataBaseFile = new String("entryDataBase.csv");
    private static String taskDataBaseFile=new String("taskDataBase.csv");

    public static String getEntryDataBaseFile() {
        return getFilePath(entryDataBaseFile);
    }

    public static String getTaskDataBaseFile(){
        return getFilePath(taskDataBaseFile);
    }

    private static String vehicleDataBaseFile = new String("vehicleDataBase.csv");


    public static String getVehicleDataBaseFile() {
        return getFilePath(vehicleDataBaseFile);
    }
    private static String teamDataBaseFile = new String("teamDataBase.csv");


    public static String getTeamDataBaseFile() {
        return getFilePath(teamDataBaseFile);
    }

    private static String greenSpaceDataBaseFile = new String("greenSpaceDataBase.csv");

    public static String getGreenSpaceDataBaseFile() {
        return getFilePath(greenSpaceDataBaseFile);
    }
    private static String managerDataBaseFile = new String("managerDataBase.csv");

    public static String getManagerDataBaseFile() {
        return getFilePath(managerDataBaseFile);
    }
}