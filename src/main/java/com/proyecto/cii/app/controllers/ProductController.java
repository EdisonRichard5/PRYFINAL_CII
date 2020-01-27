package com.proyecto.cii.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.cii.app.models.entity.Client;
import com.proyecto.cii.app.models.entity.Product;
import com.proyecto.cii.app.models.service.IClientService;
import com.proyecto.cii.app.models.service.IProductService;
import com.proyecto.cii.app.models.service.IUploadFileService;
import com.proyecto.cii.app.util.paginator.PageRender;

@Controller
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private IProductService service;
	
	@GetMapping(value="/create")
	public String create(Model model) {
		Product product = new Product();
		model.addAttribute("title", "Registro de Producto");
		model.addAttribute("product", product);
		return "product/form";		
	}
	
	@GetMapping(value="/retrieve/{id}")
	public String retrieve(@PathVariable(value="id") Long id, Model model) {
		Product product = service.findById(id);
		model.addAttribute("product", product);
		return "product/card";		
	} 
	
	@GetMapping(value="/update/{id}")
	public String update(@PathVariable(value="id") Long id, Model model) {
		Product product = service.findById(id);
		model.addAttribute("title", "Actualizando el registro de " 
		+ product.getName());
		model.addAttribute("product", product);
		return "product/form";		
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
		return "redirect:/product/list";		
	} 
	
	@PostMapping(value="/save")
	public String save(@Valid Product product,BindingResult result, Model model,
			RedirectAttributes flash) {
		try {
			if(result.hasErrors())
			{
				model.addAttribute("tittle","Error al Guardar");
				return"product/form";
			}
			service.save(product);
			flash.addFlashAttribute("success", "El registro fue guardado con éxito.");
		}
		catch(Exception ex) {
			flash.addFlashAttribute("error", "El registro no pudo ser guardado.");
		}
		return "redirect:/product/list";		
	} 
	
	@GetMapping(value="/list")
	public String list(Model model) {
		List<Product> lista = service.findAll();
		model.addAttribute("title", "Listado de Productos");
		model.addAttribute("lista", lista);
		return "product/list";		
	} 
	
}