package com.proyecto.cii.app.controllers;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import com.proyecto.cii.app.models.entity.Product; 
import com.proyecto.cii.app.models.service.IProductService; 
@Controller
@Secured("ROLE_ADMIN")
@RequestMapping(value="/producto")
@SessionAttributes("product")
public class ProductController {

	@Autowired
	private IProductService service;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Product product = new Product();
		model.addAttribute("title", "Registro de Producto");
		model.addAttribute("product", product);
		return "/product/form";		
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Long id, Model model) {
		Product product = service.findById(id);
		model.addAttribute("product", product);
		return "/product/card";		
	} 
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Long id, Model model) {
		Product product = service.findById(id);
		model.addAttribute("title", "Actualizando el registro de " 
		+ product.getName());
		model.addAttribute("product", product);
		return "/product/form";		
	} 
	
	@GetMapping(value="/delete/{id}")
	public String delete(@PathVariable(value="id") Long id, Model model, 
			RedirectAttributes flash) {
		try {
			service.delete(id);
			flash.addFlashAttribute("success", "El registro fue eliminado con éxito.");
		}	
		catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser eliminado.");
		}
		return "redirect:/producto/list";		
	} 
	
	@PostMapping(value="/save")
	public String save(@Valid Product product,BindingResult result, Model model,
			RedirectAttributes flash) {
		try {
			if(result.hasErrors())
			{
				model.addAttribute("tittle","Error al Guardar");
				return"/product/form";
			}
			service.save(product);
			flash.addFlashAttribute("success", "El registro fue guardado con éxito.");
		}
		catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser guardado.");
		}
		return "redirect:/producto/list";		
	} 
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Product> lista = service.findAll();
		model.addAttribute("title", "Listado de Productos");
		model.addAttribute("lista", lista);
		return "/product/list";		
	} 
	
}