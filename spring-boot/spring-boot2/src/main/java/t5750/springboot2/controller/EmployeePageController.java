package t5750.springboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import t5750.springboot2.model.EmployeeEntity;
import t5750.springboot2.service.EmployeeService;

@Controller
@RequestMapping("/employeePage")
public class EmployeePageController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "list-employees";
	}

	@GetMapping(value = "/edit")
	public String newEmployee(Model model) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		model.addAttribute("employee", employeeEntity);
		return "add-edit-employee";
	}

	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		EmployeeEntity employeeEntity = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employeeEntity);
		return "add-edit-employee";
	}

	@PostMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(EmployeeEntity employee) {
		EmployeeEntity updated = employeeService
				.createOrUpdateEmployee(employee);
		return "redirect:/employeePage/list";
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employeePage/list";
	}
}