package pt.ipp.isep.dei.esoft.project.core.application.session;

import pt.ipp.isep.dei.esoft.project.core.application.domain.adapters.SendEmailExternalAPI;
import pt.ipp.isep.dei.esoft.project.core.application.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.core.application.repository.sortingAlgorithmsServ.SortingList;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages the application's session, providing access to the current user session, email services, and sorting algorithms.
 */
public class ApplicationSession {
    private final AuthenticationRepository authenticationRepository;
    private static String configfile;
    //private static final String CONFIGURATION_FILENAME= configfile;
    private static final String COMPANY_DESIGNATION = "Company.Designation";
    private static final String EMAIL_DESIGNATION = "SendEmailExternalAPI.Class";
    private static final String SORTING_ALGORITHM = "SortingList.Class";
    private static final String TIME_WORK = "TimeWork";
    private static SendEmailExternalAPI sendEmailExternalAPI;
    private static SortingList sortingList;
    private static Tempo timeOfWork;

    private ApplicationSession() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        Properties props = getProperties();
    }

    /**
     * Gets an instance of the email service.
     *
     * @return the email service instance
     */
    public SendEmailExternalAPI getEmailServiceInstance() {
        return sendEmailExternalAPI;
    }

    /**
     * Gets the current user session.
     *
     * @return the current user session
     */
    public UserSession getCurrentSession() {
        pt.isep.lei.esoft.auth.UserSession userSession = this.authenticationRepository.getCurrentUserSession();
        return new UserSession(userSession);
    }

    /**
     * Loads properties from the configuration file.
     *
     * @return the loaded properties
     */
    private Properties getProperties() {
        Properties props = new Properties();
        String currentDir = System.getProperty("user.dir");
        try {
            InputStream in = new FileInputStream(currentDir+configfile);
            props.load(in);
            in.close();
            String className = props.getProperty(EMAIL_DESIGNATION);
            sendEmailExternalAPI = getEmailService();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return props;
    }

    private static ApplicationSession singleton = null;

    /**
     * Gets the singleton instance of ApplicationSession.
     *
     * @return the singleton instance
     */
    public static ApplicationSession getInstance() {
        if (singleton == null) {
            synchronized (ApplicationSession.class) {
                if (singleton == null) {
                    singleton = new ApplicationSession();
                }
            }
        }
        return singleton;
    }

    /**
     * Reads the email configuration from the properties file.
     *
     * @return the email class name
     * @throws IOException if an I/O error occurs
     */
    private static String getEmail() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String fileName = currentDir + configfile;
        try (InputStream input = new FileInputStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(EMAIL_DESIGNATION);
        }
    }

    /**
     * Gets the email service instance based on the configuration.
     *
     * @return the email service instance
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     * @throws InstantiationException if the class cannot be instantiated
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     */
    public static SendEmailExternalAPI getEmailService() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String emailClass = "pt.ipp.isep.dei.esoft.project.core.application.domain.adapters." + getEmail();
        Class<?> className = Class.forName(emailClass);
        return (SendEmailExternalAPI) className.newInstance();
    }

    /**
     * Gets the sorting algorithm service instance based on the configuration.
     *
     * @return the sorting algorithm service instance
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     * @throws InstantiationException if the class cannot be instantiated
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     */
    public static SortingList getAlgorithmService() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String algorithmClass = "pt.ipp.isep.dei.esoft.project.core.application.repository.sortingAlgorithmsServ." + getAlgorithm();
        Class<?> className = Class.forName(algorithmClass);
        return (SortingList) className.newInstance();
    }

    /**
     * Reads the sorting algorithm configuration from the properties file.
     *
     * @return the sorting algorithm class name
     * @throws IOException if an I/O error occurs
     */
    private static String getAlgorithm() throws IOException {
        String fileName = System.getProperty("user.dir") + configfile;
        try (InputStream input = new FileInputStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(SORTING_ALGORITHM);
        }
    }

    /**
     * Reads the work time configuration from the properties file.
     *
     * @return the work time as a Tempo object
     * @throws IOException if an I/O error occurs
     */
    public static Tempo getTimeOfWork() throws IOException {
        String fileName = configfile;
        try (InputStream input = new FileInputStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            String time = prop.getProperty(TIME_WORK);
            String[] hoursMinutes = time.split(":");
            if (verifyTime(hoursMinutes)) {
                return new Tempo(Integer.parseInt(hoursMinutes[0]), Integer.parseInt(hoursMinutes[1]));
            } else {
                throw new IllegalArgumentException("Invalid time format in the config file");
            }
        }
    }

    /**
     * Verifies the time format from the configuration file.
     *
     * @param hoursMinutes the time in hours and minutes
     * @return true if the format is valid, false otherwise
     */
    private static boolean verifyTime(String[] hoursMinutes) {
        return hoursMinutes.length == 2 &&
                Integer.parseInt(hoursMinutes[0]) <= 24 &&
                Integer.parseInt(hoursMinutes[1]) >= 0 &&
                Integer.parseInt(hoursMinutes[1]) <= 60 &&
                Integer.parseInt(hoursMinutes[0]) >= 0;
    }

    public void setFilePath(File selectedDirectory) {
        if(selectedDirectory != null){
            configfile=selectedDirectory.getAbsolutePath()+"/config.properties";
        }
    }
}
