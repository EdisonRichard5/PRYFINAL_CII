package com.proyecto.cii.app.controllers;


import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
  
import com.proyecto.cii.app.models.entity.User;
import com.proyecto.cii.app.models.entity.Client;
import com.proyecto.cii.app.models.entity.Role;
import com.proyecto.cii.app.models.service.UserService;
import com.proyecto.cii.app.util.paginator.PageRender;

@Controller
@RequestMapping(value="/usuario")
public class UserController {
	

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping(value="/create")
	public String registro(Model model) {	
		User usuario = new User();
		model.addAttribute("usuario", usuario);
		model.addAttribute("title", "Nuevo registro");				
		return "/usuario/form";
	} 
		 
	
	
	
	
	/*
	 * Éste método permite tener más control sobre los roles del usuario, pudiendo acceder a cada uno de ellos
	 */
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if(context != null) {
			Authentication auth = context.getAuthentication();
			if(auth != null) {
				Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
				/*for(GrantedAuthority authority : authorities) {
					if(role.equals(authority.getAuthority())) {
						return true;
					}
				}*/
				return authorities.contains(new SimpleGrantedAuthority(role));	//Esta forma es más escueta que utilizando el for
			}
		}
		return false;
	}
	
	
	
	
	@PostMapping(value="/save")
	public String save(@Valid User usuario, BindingResult result, Model model,
			RedirectAttributes flash) {
		try {
			if(result.hasErrors())
			{
				if(usuario.getUsername() == null) {
					model.addAttribute("title","Nuevo registro");					
				}
				else {
					model.addAttribute("title","Actualización de registro");
				}
				
				return "/usuario/form";
			}
			
			
			
			service.save(usuario);
			flash.addFlashAttribute("success", "El registro fue guardado con éxito.");
		}
		catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser guardado.");
		}
		return "redirect:/login";		
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
