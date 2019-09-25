package com.Hansab.WebAppEmail.Email;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.Hansab.WebAppEmail.Controllers.*;

public class Email {
	
    public static int Email(){

        int port = 995;									 //<< POP3 protocol port
        int count = 0;
    	//my test email is internetsvajag123@gmail.com
    	String username = "internetsvajag123@gmail.com"; //<< replace with email address
    	String password = "Hansab123"; 					 //<< replace with emails password
        String protocol = "pop3s";						 //<< gmail uses pop3s as the protocol name. If not working using other host, probably change it to pop3
        String host = "pop.gmail.com";					 //<< replace with hosts server address
        String toFiletxt = "";
        String time;
        String from;
        String subject;
        String content;
        String color = "";
        String colorSet1 = "id=\"c1\"";
        String colorSet2 = "id=\"c2\"";
        String colorSet3 = "id=\"c3\"";
        String fullHTML = "";
        String htmlStart = "" +
        		"<!DOCTYPE html>\r\n" + 
        		"<html>\r\n" + 
        		"<head>\r\n" + 
        		"<style type=\"text/css\">\r\n" + 
        		"#c1 {background-color:#69ff61;}\r\n" + 
        		"#c2 {background-color:#fff94f;}\r\n" + 
        		"#c3 {background-color:#ff4f4f;}\r\n" + 
        		"</style>\r\n" + 
        		"<meta charset=\"UTF-8\">\r\n" + 
        		"<title>All mail</title>\r\n" + 
        		"</head>\r\n" + 
        		"<body>\r\n" + 
        		"<script type = \"text/javascript\" src=\"sortByCols.js\"></script>\r\n" +
        		"\r\n" +
        		"<table id=\"table\" style=\"width:100%\" border=\"1\">\r\n" + 
        		"  <tr>\r\n" + 
        		"    <th onclick=\"sortTable(0)\">Date</th>\r\n" + 
        		"    <th onclick=\"sortTable(1)\">Sender email</th>\r\n" + 
        		"    <th onclick=\"sortTable(2)\">Subject</th> \r\n" + 
        		"    <th onclick=\"sortTable(3)\">Content</th>\r\n" + 
        		"  </tr>\r\n";
        String htmlEnd = "</table>\r\n" + 
        		"\r\n" + 
        		"</body>\r\n" + 
        		"</html>";
      
        try {
			Properties properties = System.getProperties();
			Session session = Session.getDefaultInstance(properties);
			Store store = session.getStore(protocol);
			store.connect(host, port, username, password);;
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			
			Message[] messages = inbox.getMessages();
			
			fullHTML = htmlStart;
			count = messages.length;
			if(messages.length == 0) {
				//No messages found!
				System.out.println("No messages found.");
			}
			else{
			    for(int i = 0; i < messages.length; i++) {
			    	
			    	time = messages[i].getSentDate().toString();
			    	from = messages[i].getFrom()[0].toString();
			    	subject = messages[i].getSubject();
			    	//content = messages[i].getContent().toString(); //returns some MimeMultipart
			    	content = getEmailBody(messages[i]);
			    	//looks for status code 1001 and makes the row green
			    	if(content.contains("1001")) {
			    		color = colorSet1;
			    	}
			    	//looks for status code 2001 and makes the row yellow
			    	else if(content.contains("2001")) {
			    		color = colorSet2;
			    	}
			    	//looks for status code 3001 and makes the row red
			    	else if(content.contains("3001")) {
			    		color = colorSet3;
			    	}
			    	//creates the table row and adds to the HTML
			    	fullHTML += "<tr " + color + ">\r\n";
			    	fullHTML += "<td>" + time + "</td>\r\n";
			    	fullHTML += "<td>" + from + "</td>\r\n";
			    	fullHTML += "<td>" + subject + "</td>\r\n";
			    	fullHTML += "<td>" + content + "</td>\r\n";			    	
			    	fullHTML += "</tr>\r\n";
			    	//adds the email data for a csv file to a string
			    	toFiletxt += time + "," + from + "," + subject + "," + content + "\r\n";
			    	//TODO somehow delete the message. Send a DEL request.
			    	//Or enable deleting email from the server after it is downloaded
			    }
			}			
			inbox.close(true);
			store.close();
			//adds the html code for the end of file
			fullHTML += htmlEnd;
			//saves the html file
			FileWriter htmlWriter = new FileWriter("src/main/resources/templates/allEmails.html");
			htmlWriter.write(fullHTML);
			htmlWriter.close();			
			//saves the csv file
			FileWriter fileWriter = new FileWriter("src/main/resources/templates/allEmails.csv");
			//fileWriter.append(toFiletxt);
		    fileWriter.write(toFiletxt);
		    fileWriter.close();
		    
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
    }
    
    public static String getEmailBody(Message gotMessage) throws MessagingException, IOException {
    	String body = "";
    	String contentType = gotMessage.getContentType();
    	
    	   if (contentType.contains("multipart")) {
    	        Multipart multiPart = (Multipart) gotMessage.getContent();
    	        int numberOfParts = multiPart.getCount();
    	        //System.out.println(numberOfParts);
    	        for (int partCount = 0; partCount < numberOfParts; partCount++) {
    	            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
    	            body = part.getContent().toString();
    	        }
    	    }
    	    else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
    	        Object content = gotMessage.getContent();
    	        //System.out.println(content);
    	        if (content != null) {
    	        	body = content.toString();
    	        }
    	    }    	
    	return body;
    }
    
}
