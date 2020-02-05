package t5750.spring.aop.cglib.service;

public interface PersistenceService {
	void save(long id, String data);

	String load(long id);
}