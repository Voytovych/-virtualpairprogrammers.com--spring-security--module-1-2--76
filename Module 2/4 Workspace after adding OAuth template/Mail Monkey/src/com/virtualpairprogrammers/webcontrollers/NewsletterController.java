package com.virtualpairprogrammers.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.rest.representations.CustomerCollectionRepresentation;

@Controller
public class NewsletterController 
{	
	@Autowired
	private OAuth2RestTemplate oauthTemplate;
	
	@RequestMapping("/build-newsletter")
	public ModelAndView displayAllCustomersOnWebPage()
	{
		return new ModelAndView("/newsletter.jsp");
	}
	
	@RequestMapping("/importFromCRM.html")
	public ModelAndView muchBetterVersion()
	{
		CustomerCollectionRepresentation customers = oauthTemplate.getForObject("http://localhost:8080/crm/rest/customers",CustomerCollectionRepresentation.class);
		return new ModelAndView("/importedContacts.jsp","customers",customers.getCustomers());	
	}

}
