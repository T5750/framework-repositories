package t5750.springboot2.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import t5750.springboot2.model.Student;

@Service
public class StudentService {
	@Cacheable("student")
	public Student getStudentByID(String id) {
		try {
			System.out.println(
					"Going to sleep for 5 Secs.. to simulate backend call.");
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Student(id, "Sajal", "V");
	}
}
