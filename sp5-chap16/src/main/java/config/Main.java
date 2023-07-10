package config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev"); //.setActiveProfiles("dev", "mysql");
		context.register(MemberConfig.class, DsDevConfig.class, DsRealConfig.class);
		context.refresh();
		//java -Dspring.profiles.active=dev main.Main
		/*AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(
						MemberConfig.class, DsDevConfig.class, DsRealConfig.class);*/
	}
}
