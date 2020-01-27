package com.proyecto.cii.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class HomeController{
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title","sistema ferretería");
		model.addAttribute("description","Sistema proformas de ferretería");
		return"/home";
	}
}

