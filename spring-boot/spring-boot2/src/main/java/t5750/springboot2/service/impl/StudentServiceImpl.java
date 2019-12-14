package t5750.springboot2.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import t5750.springboot2.model.Student;
import t5750.springboot2.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	// @Cacheable("student")
	@Override
	@Cacheable(cacheNames = "studentCache", key = "#id")
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
