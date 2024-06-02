package pt.ipp.isep.dei.esoft.project.application.session;

import pt.ipp.isep.dei.esoft.project.domain.adapters.EmailService;
import pt.ipp.isep.dei.esoft.project.domain.adapters.SendEmailExternalAPI;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationSession {
    private final AuthenticationRepository authenticationRepository;
    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    private static final String COMPANY_DESIGNATION = "Company.Designation";
    private static final String EMAIL_DESIGNATION = "SendEmailExternalAPI.Class";
    private static final String TIME_WORK = "TimeWork";
    private static SendEmailExternalAPI sendEmailExternalAPI;
    private static Tempo timeOfWork;

    private ApplicationSession() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        Properties props = getProperties();
    }

    public SendEmailExternalAPI getEmailServiceInstance(){
        return sendEmailExternalAPI;
    }

    public UserSession getCurrentSession() {
        pt.isep.lei.esoft.auth.UserSession userSession = this.authenticationRepository.getCurrentUserSession();
        return new UserSession(userSession);
    }

    private Properties getProperties() {
        Properties props = new Properties();
        Properties emailService = new Properties();
        try {
            InputStream in = new FileInputStream(CONFIGURATION_FILENAME);
            props.load(in);
            in.close();
            String companyDesignation = props.getProperty(COMPANY_DESIGNATION);
            String className = props.getProperty(EMAIL_DESIGNATION);
            sendEmailExternalAPI = getEmailService();
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

    public static Tempo getTimeOfWork() throws IOException {
        String fileName="src/main/resources/config.properties";
        InputStream input = new FileInputStream(fileName);
        String time = "";
        Tempo tempo = null;
        try {
            Properties prop = new Properties();
            prop.load(input);
            time= prop.getProperty(TIME_WORK);
            String[] hoursMinutes = time.split(":");
            if (verifyTime(hoursMinutes)) {
                tempo = new Tempo(Integer.parseInt(hoursMinutes[0]), Integer.parseInt(hoursMinutes[1]));
            }else {
                throw new IOException("Invalid time format on config file");
            }
            tempo = new Tempo(Integer.parseInt(hoursMinutes[0]), Integer.parseInt(hoursMinutes[1]));
        }finally {
            input.close();
        }
        return tempo;
    }

    private static boolean verifyTime(String[] hoursMinutes) {
        return (hoursMinutes.length == 2 && Integer.parseInt(hoursMinutes[0])>24 && Integer.parseInt(hoursMinutes[1])>-1 && Integer.parseInt(hoursMinutes[1])<61 && Integer.parseInt(hoursMinutes[0])>-1);
    }
}