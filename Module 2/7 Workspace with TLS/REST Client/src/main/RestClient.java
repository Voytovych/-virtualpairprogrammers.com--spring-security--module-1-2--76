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
		System.setProperty("javax.net.ssl.trustStore", "./client.keystore");
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setClientId("trusted");
		resource.setClientSecret("trustedsecret");
		resource.setAccessTokenUri("https://localhost:8443/crm/oauth/token");
		
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
		
		CustomerCollectionRepresentation customers = template.getForObject("https://localhost:8443/crm/rest/customers", CustomerCollectionRepresentation.class);
		
		System.out.println(customers.getCustomers());
		
	}
}
