package t5750.spring.aop.cglib.service.impl;

import t5750.spring.aop.cglib.service.PersistenceService;

/**
 * A simple implementation of PersistenceService interface
 */
public class PersistenceServiceImpl implements PersistenceService {
	public void save(long id, String data) {
		System.out.println(data + " has been saved successfully.");
	}

	public String load(long id) {
		return "T5750";
	}
}