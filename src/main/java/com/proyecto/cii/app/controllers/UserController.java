package com.proyecto.cii.app.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
  
import com.proyecto.cii.app.models.entity.User;
import com.proyecto.cii.app.models.entity.Role;
import com.proyecto.cii.app.models.service.UserService;

@Controller
@RequestMapping(value="/usuario")
public class UserController {
	
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
			
			String pass = usuario.getPassword();
			usuario.setPassword(passwordEncoder.encode(pass));			
			usuario.getRoles().add(new Role("ROLE_USER"));
			
			service.save(usuario);
			flash.addFlashAttribute("success", "El registro fue guardado con éxito.");
		}
		catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser guardado.");
		}
		return "redirect:/login";		
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
