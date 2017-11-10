package com.virtualpairprogrammers.webcontrollers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsletterController 
{	
	@RequestMapping("/build-newsletter")
	public ModelAndView displayAllCustomersOnWebPage()
	{
		return new ModelAndView("/newsletter.jsp");
	}
	
	@RequestMapping("/import.html")
	public ModelAndView firstUglyVersion(@RequestParam("code") String code)
	{
		RestTemplate template = new RestTemplate();
		
		String credentials = "mailmonkey:somesecretkey";
		String encodedString = new String(Base64.encodeBase64(credentials.getBytes()));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + encodedString);
		
		HttpEntity request = new HttpEntity(headers);
		
		// Don't do it like this in real life!!!!!!
		// TODO make this https
		String url = "http://localhost:8080/crm/oauth/token";
		url += "?code=" + code;
		url += "&grant_type=authorization_code";
		url += "&redirect_uri=http://localhost:8080/mailmonkey/import.html";
		url += "&client_id=mailmonkey";
		
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST,request, String.class);
		
		System.out.println("\n\n\n\n\n" + response.getBody() + "\n\n\n\n\n\n");
		return null;
	}
}
