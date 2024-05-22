package pt.ipp.isep.dei.esoft.project.application.session;

import pt.ipp.isep.dei.esoft.project.domain.adapters.EmailService;
import pt.ipp.isep.dei.esoft.project.domain.adapters.SendEmailExternalAPI;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationSession {
    private final AuthenticationRepository authenticationRepository;
    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    private static final String COMPANY_DESIGNATION = "Company.Designation";
    private static final String EMAIL_DESIGNATION = "SendEmailExternalAPI.Class";
    private static SendEmailExternalAPI sendEmailExternalAPI;

    private ApplicationSession() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        Properties props = getProperties();
    }

    public UserSession getCurrentSession() {
        pt.isep.lei.esoft.auth.UserSession userSession = this.authenticationRepository.getCurrentUserSession();
        return new UserSession(userSession);
    }

    private Properties getProperties() {
        Properties props = new Properties();
        Properties emailService = new Properties();

        // Add default properties and values

        // Read configured values
        try {
            InputStream in = new FileInputStream(CONFIGURATION_FILENAME);
            props.load(in);
            in.close();
            String companyDesignation = props.getProperty(COMPANY_DESIGNATION);
            String className = props.getProperty(EMAIL_DESIGNATION);
            Class<?> oClass = Class.forName(className);
            sendEmailExternalAPI = (SendEmailExternalAPI) oClass.newInstance();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return props;
    }

    private static ApplicationSession singleton = null;

    public static ApplicationSession getInstance() {
        if (singleton == null) {
            synchronized (ApplicationSession.class) {
                singleton = new ApplicationSession();
            }
        }
        return singleton;
    }


    private static String getEmail() throws IOException {
        String fileName="src/main/resources/config.properties";
        InputStream input = new FileInputStream(fileName);
        String email = "";
        try {
            Properties prop = new Properties();
            prop.load(input);
            email= prop.getProperty(EMAIL_DESIGNATION);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            input.close();
        }
        return email;
    }

    public static SendEmailExternalAPI getEmailService() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String email= "pt.ipp.isep.dei.esoft.project.domain.adapters.";
        email += getEmail();
        Class<?> className = Class.forName(email);
        return (SendEmailExternalAPI) className.newInstance();
    }


    private static String getAlgorithm() throws IOException {
        String fileName="src/main/resources/config.properties";
        InputStream input = new FileInputStream(fileName);
        String algorithm = "";
        try {
            Properties prop = new Properties();
            prop.load(input);
            algorithm= prop.getProperty("sortAlgorithm");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            input.close();
        }
        return algorithm;
    }
}