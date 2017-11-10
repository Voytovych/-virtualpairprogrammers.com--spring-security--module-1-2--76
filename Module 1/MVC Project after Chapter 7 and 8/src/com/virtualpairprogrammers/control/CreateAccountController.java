package com.virtualpairprogrammers.control;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.domain.UserFormObject;

@Controller
@RequestMapping("/createAccount")
public class CreateAccountController 
{
	@Autowired
	private JdbcUserDetailsManager userManager;
	
	@Autowired
	@Qualifier("vppAuthenticator")
	private AuthenticationManager authenticationManager;	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show() {
		return new ModelAndView("create-account", "userFormObject", new UserFormObject());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@Valid UserFormObject newUser, Errors results)
	{
		if (results.hasErrors())
		{
			return new ModelAndView("create-account", "userFormObject", newUser);
		}
		
		// send them to the database
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(newUser.getPassword());
		
		User user = new User(newUser.getUsername(), encodedPassword,roles);
		try
		{
			userManager.createUser(user);
		}
		catch (DuplicateKeyException e)
		{
			results.rejectValue("username", "username.unique");
			newUser.setPassword(null);
			return new ModelAndView("create-account", "userFormObject", newUser);
		}
		
		System.out.println("Password is " + user.getPassword());
		
		Authentication credentials = new UsernamePasswordAuthenticationToken(user.getUsername(), newUser.getPassword());
		credentials = authenticationManager.authenticate(credentials);
		if (credentials.isAuthenticated())
		{
			SecurityContextHolder.getContext().setAuthentication(credentials);
			return new ModelAndView("redirect:/viewAllBooks.do");
		}
		else
		{
			throw new RuntimeException("For some reason, the user didn't login even though it should have been automatic.");
		}		
	}
}
