package com.Hansab.WebAppEmail.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Hansab.WebAppEmail.Email.Email;


@Controller
public class Controllers {

	@RequestMapping("/404")
	//just a 404 page opener
	public String go404() {		
		return "error.html";
	}
	@RequestMapping("/allEmails.html")
	public String MailList() {
		//downloads emails, creates csv & html files
		Email.Email();
		//opens the newly created html file
		return "allEmails.html";
	}
	@RequestMapping("/noEmail.html")
	public static String noEmail() {
		return "noEmail.html";
	}
}