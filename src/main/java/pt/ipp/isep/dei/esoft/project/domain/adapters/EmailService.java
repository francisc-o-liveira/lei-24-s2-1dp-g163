package pt.ipp.isep.dei.esoft.project.domain.adapters;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;

import java.io.IOException;
import java.util.List;

/**
 * The type Email service.
 */
public class EmailService {



    public static void sendEmailToList(List<Collaborator> collaboratorList, EntryDto entry) throws IOException {
        for(Collaborator collaborator : collaboratorList){
            sendToEmailFile(collaborator.getEmail(),entry.getTitle(),entry.getDescription() + "\n\n" + entry.getTeamAssigned());
        }
    }

    /**
     * Send email.
     *
     * @param to  the email
     * @param subject the title email
     * @param body the body email
     */

    private static void sendToEmailFile(String to, String subject,String body) throws IOException {
        if (body.isEmpty()) {
            throw new IOException("MESSAGE CANNOT BE EMPTY");
        } else {
            SendEmailExternalAPI e = null;
            try {
                e = ApplicationSession.getInstance().getEmailServiceInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);

            }
            e.sendEmail(to, subject,body);
        }
    }

}