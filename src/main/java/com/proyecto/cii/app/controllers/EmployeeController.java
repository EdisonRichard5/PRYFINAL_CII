package com.proyecto.cii.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.cii.app.models.entity.Client;
import com.proyecto.cii.app.models.entity.Employee;
import com.proyecto.cii.app.models.service.IEmployeeService;
import com.proyecto.cii.app.models.service.IUploadFileService;
import com.proyecto.cii.app.util.paginator.PageRender;


@Controller
@SessionAttributes("employee")
public class EmployeeController {

	protected final Logger log = LoggerFactory.getLogger(getClass());
 
	@Autowired
	private IEmployeeService clientService;

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private MessageSource messageSource;
 
	@Secured("ROLE_USER")
	@GetMapping(value="/upload/{filename:.+}")
	public ResponseEntity<Resource> foto(@PathVariable String filename){
		Resource resource = null;
		try {
			resource = uploadFileService.load(filename);
		} catch (MalformedURLException e) { 
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}


	@Secured("ROLE_USER")
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value="/ver2/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		 
		Employee employee = clientService.fetchByIdWithInvoice(id);
		if(employee == null) {
			flash.addFlashAttribute("error", "El empleado"
					+ " no existe en la base de datos");
			return "redirect:/empleados";
		}else {
			model.put("employee", employee);
			model.put("title2", "Detalles empleado - " + employee.getName());
		}
		return "/ver2";
	}
	
	

	@RequestMapping(value= {"/empleados", "/list2"}, method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, 
			Model model, 
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {
		if(authentication != null) {
			log.info("El usuario actual es " + authentication.getName());
		}
		 
		if(hasRole("ROLE_ADMIN")) {
			log.info("El usuario tiene el role necesario para acceder a éste recurso");
		}else {
			log.error("El usuario NO tiene el role necesario para acceder a éste recurso");
		}
		 
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			log.info("Usando HttpServletRequest: El usuario tiene el role necesario para acceder a éste recurso");
		}else {
			log.info("Usando HttpServletRequest: El usuario NO tiene el role necesario para acceder a éste recurso");
		}
		

		Pageable pageRequest = PageRequest.of(page, 3);	//Spring Boot 2
		Page<Employee> employees = clientService.findAll(pageRequest);
		PageRender<Employee> render = new PageRender<>("/employees", employees);
		model.addAttribute("title2", "Listado de Empleado");
		model.addAttribute("employees", employees);
		model.addAttribute("page", render);
		return "/list2";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form2")
	public String crear(Map<String, Object> model) {
		Employee employee = new Employee();
		model.put("title2", "Formulario de empleado");
		model.put("employee", employee);
		return "/form2";
	}
	
	
	//@Secured("ROLE_ADMIN")
		@PreAuthorize("hasRole('ROLE_ADMIN')")	//La anotación @PreAuthorize es igual que @Secured, solo que permite más control
		@RequestMapping(value="/form2/{id}")
		public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
			if(id > 0) {
				Employee employee = clientService.findOne(id);
				if(employee != null) {
					model.put("title2", "Editar empleado");
					model.put("employee", employee);
					return "/form2";
				}else {
					flash.addFlashAttribute("error", "El ID no es válido");
					return "redirect:/empleados";
				}
			}else {
				flash.addFlashAttribute("error", "El ID tiene que ser positivo");
				return "redirect:/empleados";
			}
		}
		
		
		@Secured("ROLE_ADMIN")
		@RequestMapping(value="/form2", method=RequestMethod.POST)
		public String guardar(@Valid Employee employee, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus) {
			if(result.hasErrors()) {
				model.addAttribute("title2", "Formulario de empleado");
				return "/form2";
			}
			if(!photo.isEmpty()) {
			 
				if(employee.getId() != null && employee.getId() > 0
						&& employee.getPhoto() != null
						&& employee.getPhoto().length() > 0) {
					uploadFileService.delete(employee.getPhoto());
				}
				String uniqueFileName = null;
				try {
					uniqueFileName = uploadFileService.copy(photo);
				}catch(IOException e) {
					e.printStackTrace();
				}
				flash.addFlashAttribute(
						"info", 
						"Imagen subida correctamente (" + uniqueFileName + ")");
				employee.setPhoto(uniqueFileName);
			}
			String message = employee.getId() != null ? "Empleado editado con éxito" :
				"Empleado creado con éxito";
			clientService.save(employee);
			sessionStatus.setComplete();
			flash.addFlashAttribute("success", message);
			return "redirect:empleados";
		}

		@Secured("ROLE_ADMIN")
		@RequestMapping(value="/eliminar2/{id}")
		public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash, Map<String, Object> model) {
			if(id > 0) {
				Employee employee = clientService.findOne(id);
				clientService.delete(id);
				flash.addFlashAttribute("success", "Empleado eliminado con éxito");
				if(uploadFileService.delete(employee.getPhoto())) {
					flash.addFlashAttribute("info", "Foto " + employee.getPhoto() + " eliminada con éxito");
				}
			}
			return "redirect:/empleados";
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
				 
				return authorities.contains(new SimpleGrantedAuthority(role));	//Esta forma es más escueta que utilizando el for
			}
		}
		return false;
	}
}
