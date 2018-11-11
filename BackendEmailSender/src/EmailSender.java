

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmailSender
 */
@WebServlet("/EmailSender")
public class EmailSender extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String USER_NAME = "grosceryUSC@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "milkAndApples"; // GMail password
    private static String RECIPIENT = "maxwellnewman100@gmail.com";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailSender() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here");
		//these are the strings that dictate the form and content of the email
		String from = USER_NAME;
        String pass = PASSWORD;
        //RECIPIENT is currently a static variable, so will need to be changed to grab the email of the user
        //String intended = RECIPIENT;
        String intended = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        
        // list of recipient email addresses
        String[] to = { intended }; 
        String subject = "Welcome to GroSCery!";
        
        //in the body, we'll need to substitute the information given to use by the user
        String body = "Thank you for creating an account with GroSCery!  Here is your account information for future reference:";
        body += "\nname: " + name + "\nemail: " + intended + "\npassword: " + password;
        sendFromGMail(from, pass, to, subject, body);
	}
	
	//sends the email with the given contact information
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        
        System.out.println("From: " + from);
        System.out.println("pass: " + pass);
        System.out.println("to: " + to[0]);
        System.out.println("Body: " + body);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        //tries to send the message
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            //prepare the message for sending
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
        	System.out.println("in ae exception");
            ae.printStackTrace();
        }
        catch (MessagingException me) {
        	System.out.println("In messaging exception");
            me.printStackTrace();
        }
    }
}
