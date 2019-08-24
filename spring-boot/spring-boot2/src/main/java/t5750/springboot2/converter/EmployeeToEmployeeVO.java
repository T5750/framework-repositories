package t5750.springboot2.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import t5750.springboot2.model.Employee;
import t5750.springboot2.vo.EmployeeVO;

@Component
public class EmployeeToEmployeeVO implements Converter<Employee, EmployeeVO> {
	@Override
	public EmployeeVO convert(Employee employee) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployeeId(employee.getId());
		employeeVO.setFirstName(employee.getFirstName());
		employeeVO.setLastName(employee.getLastName());
		employeeVO.setEmail(employee.getEmail());
		return employeeVO;
	}
}
