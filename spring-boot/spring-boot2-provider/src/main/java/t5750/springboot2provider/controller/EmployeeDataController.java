package t5750.springboot2provider.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2api.model.*;

@RestController
public class EmployeeDataController {
	private static Logger logger = LoggerFactory
			.getLogger(EmployeeDataController.class);

	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public EmployeeAddresses getAddresses() {
		logger.info("get addresses Start");
		EmployeeAddresses employeeAddressesList = new EmployeeAddresses();
		EmployeeAddress employeeAddress1 = new EmployeeAddress();
		EmployeeAddress employeeAddress2 = new EmployeeAddress();
		List<EmployeeAddress> addressList = new ArrayList<EmployeeAddress>();
		{
			employeeAddress1.setHouseNo("1111");
			employeeAddress1.setStreetNo("111");
			employeeAddress1.setZipCode("111111");
			employeeAddress2.setHouseNo("222");
			employeeAddress2.setStreetNo("222");
			employeeAddress2.setZipCode("222222");
			addressList.add(employeeAddress1);
			addressList.add(employeeAddress2);
			employeeAddressesList.setEmployeeAddressList(addressList);
		}
		return employeeAddressesList;
	}

	@RequestMapping(value = "/phones", method = RequestMethod.GET)
	public EmployeePhone getPhoneNumbers() {
		logger.info("get phones Start");
		EmployeePhone employeePhone = new EmployeePhone();
		{
			ArrayList<String> phoneNumberList = new ArrayList<String>();
			phoneNumberList.add("100000");
			phoneNumberList.add("200000");
			employeePhone.setPhoneNumbers(phoneNumberList);
		}
		return employeePhone;
	}

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public EmployeeNames getEmployeeName() {
		logger.info("get names Start");
		EmployeeNames employeeNamesList = new EmployeeNames();
		EmployeeName employeeName1 = new EmployeeName();
		EmployeeName employeeName2 = new EmployeeName();
		List<EmployeeName> employeeList = new ArrayList<EmployeeName>();
		{
			employeeName1.setFirstName("Santa");
			employeeName1.setLastName("Singh");
		}
		{
			employeeName2.setFirstName("Banta");
			employeeName2.setLastName("Singh");
		}
		employeeList.add(employeeName1);
		employeeList.add(employeeName2);
		employeeNamesList.setEmployeeNameList(employeeList);
		return employeeNamesList;
	}
}
