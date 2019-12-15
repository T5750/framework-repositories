package t5750.springboot2security.model;

public class Customer {
	private long id;
	private String name;
	private int age;

	public Customer() {
	}

	public Customer(long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Customer(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		String info = String.format(
				"Customer Info: id = %d, name = %s, age = %d", id, name, age);
		return info;
	}
}