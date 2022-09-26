package backend.services;

import backend.DatabaseModels.Admin;
import backend.Locator;
import backend.Tables.AdminsTable;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {
    final AdminsTable adminsTable = (AdminsTable) Locator.instance.get(AdminsTable.class);
    final Logger logger = Logger.getLogger(EmailService.class.getName());
    final String sendGridApiKey;

    public EmailService(String sendGridApiKey) {
        this.sendGridApiKey = sendGridApiKey;
    }

    public void emailSystemDataUpdated() throws SQLException {
        emailAdmins("AiWeb System data successfully updated", "Hi Admin\n\nAiWeb system data has been successfully updated!\n\nAiWeb team\n");
    }
    public void emailSystemDataUpdateError(Object error) throws SQLException {
        emailAdmins("Error: AiWeb System could not update data", "Hi Admin\n\nAiWeb system could not update data!\n\n"+error.toString()+"\n\nAiWeb team\n");
    }

    private void emailAdmins(String subject, String text) throws SQLException {
        final List<Admin> admins = adminsTable.listAll();
        for(final Admin toAdmin: admins) {
            try{
                Email from = new Email("b.mgutsi@gmail.com");
                Email to = new Email(toAdmin.username);
                Content content = new Content("text/plain", text);
                Mail mail = new Mail(from, subject, to, content);

                SendGrid sg = new SendGrid(sendGridApiKey);
                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                sg.api(request);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Could not send mail to "+ toAdmin.username);
            }
        }
    }
}
