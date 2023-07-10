package config;

import org.springframework.beans.factory.annotation.Value;

public class Info {
	@Value("${info.version}")
	private String version;
	
	public void printInfo() {
		System.out.println("version = " + version);
	}
	
	//@Value("${info.version}")
	public void setVersion(String version) {
		this.version = version;
	}
}
