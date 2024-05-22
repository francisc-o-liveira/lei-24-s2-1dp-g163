package pt.ipp.isep.dei.esoft.project.domain.adapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GmailService implements SendEmailExternalAPI{

    @Override
    public void sendEmail(String to, String subject, String body) {


        String fileName = "email.txt";

        File f = new File(fileName);
        if (f.exists()) {
            writeToFile(f, to,subject,body);
        } else {
            try {
                f.createNewFile();
                writeToFile(f, to,subject,body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    /**
     * Write to file.
     *
     * @param f      the file where the email will be written
     * @param to  the email of the address
     * @param subject the title email
     * @param body the message content
     */


    private void writeToFile(File f, String to, String subject, String body) {
        try {
            FileWriter send = new FileWriter(f, true);
            send.write("Email Service: Dei Email Service\n"+"To:"+to + "\n" + subject + "\n" + body + "\n");
            send.flush();
            send.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
