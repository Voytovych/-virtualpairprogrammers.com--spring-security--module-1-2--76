package main;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import resources.CustomerCollectionRepresentation;


public class RestClient 
{
	public static void main(String[] args) throws IOException
	{
		// Longhand way!
		
//		RestTemplate template = new RestTemplate();
//		
//		String credentials = "trusted:trustedsecret";
//		String encodedString = new String(Base64.encodeBase64(credentials.getBytes()));
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Basic " + encodedString);
//			
//		// TODO make this https
//		String url = "http://localhost:8080/crm/oauth/token";
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//		params.add("grant_type", "client_credentials");
//		
//		HttpEntity request = new HttpEntity(params,headers);
//		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST,request, String.class);		
//		
//		// Leg 3: 
//		url = "http://localhost:8080/crm/rest/customers";
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = mapper.readTree(response.getBody());
//		String token = node.path("access_token").asText();
//		
//		System.out.println(token);
//
//		HttpHeaders headersLeg3 = new HttpHeaders();
//		headersLeg3.add("Authorization", "Bearer " + token);
//		HttpEntity requestLeg3 = new HttpEntity(headersLeg3);
//		
//		ResponseEntity<CustomerCollectionRepresentation> customers = template.exchange(url, HttpMethod.GET, requestLeg3, CustomerCollectionRepresentation.class);
//		System.out.println(customers.getBody());
		
		
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setClientId("trusted");
		resource.setClientSecret("trustedsecret");
		resource.setAccessTokenUri("http://localhost:8080/crm/oauth/token");
		
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
		
		CustomerCollectionRepresentation customers = template.getForObject("http://localhost:8080/crm/rest/customers", CustomerCollectionRepresentation.class);
		
		System.out.println(customers.getCustomers());
		
	}
}
