package t5750.springbootadmin.util;

public class Globals {
	public static final String SPRING_APPLICATION_NAME = "spring.application.name";
	public static final String CONTEXT_PATH = "/"
			+ YmlUtil.getProperty(SPRING_APPLICATION_NAME);
}
