package t5750.springboot2.dao;

import java.util.ArrayList;
import java.util.List;

import t5750.springboot2.vo.EmployeeVO;

public class EmployeeVODB {
	public static List<EmployeeVO> getEmployeeList() {
		List<EmployeeVO> list = new ArrayList<>();
		EmployeeVO empOne = new EmployeeVO(1, "Lokesh", "Gupta",
				"howtodoinjava@gmail.com");
		EmployeeVO empTwo = new EmployeeVO(2, "Amit", "Singhal",
				"asinghal@yahoo.com");
		EmployeeVO empThree = new EmployeeVO(3, "Kirti", "Mishra",
				"kmishra@gmail.com");
		list.add(empOne);
		list.add(empTwo);
		list.add(empThree);
		return list;
	}
}
