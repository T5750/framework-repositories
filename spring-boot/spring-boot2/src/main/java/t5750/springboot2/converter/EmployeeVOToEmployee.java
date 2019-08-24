package t5750.springboot2.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import t5750.springboot2.model.Employee;
import t5750.springboot2.vo.EmployeeVO;

@Component
public class EmployeeVOToEmployee implements Converter<EmployeeVO, Employee> {
	@Override
	public Employee convert(EmployeeVO employeeVO) {
		Employee employee = new Employee();
		employee.setId(employeeVO.getEmployeeId());
		employee.setFirstName(employeeVO.getFirstName());
		employee.setLastName(employeeVO.getLastName());
		employee.setEmail(employeeVO.getEmail());
		return employee;
	}
}
