package spring;

import org.springframework.beans.factory.InitializingBean;

public class Client3 implements InitializingBean {
	private String host;
	
	@Override
	public void afterPropertiesSet() {
		connect();
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void connect() {
		System.out.println("Client3.connect() 실행");
	}
	
	public void send() {
		System.out.println("Client3.send() to " + host);
	}
	
	public void close() {
		System.out.println("Client3.close() 실행");
	}
}
