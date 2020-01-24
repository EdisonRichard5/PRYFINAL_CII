package com.proyecto.cii.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.proyecto.cii.app.models.entity.Employee;

@Component("list2.csv")
public class EmpleadoCsvView extends AbstractView{

	public EmpleadoCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		final String filename = "employees.csv";
		res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		res.setContentType(getContentType());

		Page<Employee> employees = (Page<Employee>) model.get("employees");
		ICsvBeanWriter beanWriter = new CsvBeanWriter(res.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		// Nombres EXACTOS de las propiedades de los objetos a escribir,
		// en este caso clientes
		String[] headers = {"id", "name", "surname", "email", "createdAt"};
		beanWriter.writeHeader(headers);
		
		for(Employee employee : employees) {
			beanWriter.write(employee, headers);
		}
		beanWriter.close();
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}


}
