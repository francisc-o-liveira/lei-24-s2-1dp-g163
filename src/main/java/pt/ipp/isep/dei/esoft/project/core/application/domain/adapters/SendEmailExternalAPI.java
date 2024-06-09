package pt.ipp.isep.dei.esoft.project.core.application.domain.adapters;
/**
 * The interface Email.
 */
public interface SendEmailExternalAPI {
    /**
     * Send email.
     *
     * @param to  the email
     * @param subject the title email
     * @param body the body email
     */
    void sendEmail(String to, String subject, String body);

}
