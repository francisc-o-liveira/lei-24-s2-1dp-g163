package pt.ipp.isep.dei.esoft.project.domain.adapters;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;

import java.io.IOException;

/**
 * The type Email service.
 */
public class EmailService {

    /**
     * Send email.
     *
     * @param to  the email
     * @param subject the title email
     * @param body the body email
     */
    public static void sendToEmailFile(String to, String subject,String body) throws IOException {
        if (body.isEmpty()) {
            throw new IOException("MESSAGE CANNOT BE EMPTY");
        } else {
            SendEmailExternalAPI e = null;
            try {
                e = ApplicationSession.getEmailService();
            } catch (Exception ex) {
                throw new RuntimeException(ex);

            }
            e.sendEmail(to, subject,body);
        }
    }

}