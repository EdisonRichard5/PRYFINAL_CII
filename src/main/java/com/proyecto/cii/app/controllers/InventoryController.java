package com.proyecto.cii.app.controllers;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.cii.app.models.entity.Employee;
import com.proyecto.cii.app.models.entity.Inventory;
import com.proyecto.cii.app.models.entity.InventoryLine;
import com.proyecto.cii.app.models.entity.Product;
import com.proyecto.cii.app.models.service.IEmployeeService;

@Controller
@RequestMapping("/inventario")
@Secured("ROLE_ADMIN")
@SessionAttributes("inventory")
public class InventoryController {

	@Autowired
	private IEmployeeService clientService;

	@GetMapping("/form2/{clientId}")
	public String crear(@PathVariable(value="clientId") Long clientId, Map<String, Object> model, RedirectAttributes flash) {
	Employee employee = clientService.findOne(clientId);
	if(employee == null) {
		flash.addFlashAttribute("error", "No existe ese cliente");
		return "redirect:/employees";
	}
	Inventory inventory = new Inventory();
	inventory.setEmployee(employee);
	model.put("inventory", inventory);
	model.put("title", "Crear inventario");

	return "/inventories/form2";
	}

	@GetMapping(value="/cargar-producto2/{search}", produces={"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String search){
		return clientService.findByName(search);
	}

	@PostMapping("/form2")
	public String save(
			@Valid Inventory inventory, 
			BindingResult result,
			@RequestParam(name="item_id[]", required=false) Long[] itemId, 
			@RequestParam(name="quantity[]", required=false) Integer[] quantities, 
			RedirectAttributes flash,
			SessionStatus status,
			Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Crear Inventario");
			return "/inventories/form2";
		}
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Crear Inventario");
			model.addAttribute("error", "El Inventario debe tener productos");
			return "/inventories/form";
		}
		for(int i=0; i<itemId.length; i++) {
			Product product = clientService.findProductById(itemId[i]);
			InventoryLine line = new InventoryLine();
			line.setQuantity(quantities[i]);
			line.setProduct(product);
			inventory.addLine(line);
		}
		clientService.saveInventory(inventory);
		status.setComplete();
		flash.addFlashAttribute("success", "Inventario creado con éxito");
		return "redirect:/ver2/" + inventory.getEmployee().getId();
	}
	
	@GetMapping("/ver2/{id}")
	public String ver(@PathVariable(value="id") Long id,
			Model model,
			RedirectAttributes flash) {
		//Invoice invoice = clientService.findInvoiceById(id);
		Inventory inventory = clientService.fetchByIdWithClientWithInvoiceLineWithProduct(id);
		if(inventory == null) {
			flash.addFlashAttribute("error", "El inventario no existe");
			return "redirect:/empleados";
		}
		model.addAttribute("inventory", inventory);
		model.addAttribute("title", "Inventario:".concat(inventory.getDescription()));
		return "/inventories/view";
	}

	@GetMapping("/eliminar2/{id}")
	public String eliminar(
			@PathVariable(value="id") Long id,
			RedirectAttributes flash) {
		Inventory inventory = clientService.findInvoiceById(id);
		if(inventory != null) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "Inventario eliminado con éxito");
			return "redirect:/ver2/" + inventory.getEmployee().getId();
		}else {
			flash.addAttribute("error", "El inventario no existe");
			return "redirect:/empleadoss";
		}
	
	}

}
