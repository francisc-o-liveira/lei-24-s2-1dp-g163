package pt.ipp.isep.dei.esoft.project.domain.adapters;

public interface SendEmailExternalAPI {
    public void sendEmail(String to, String subject, String body);
}
