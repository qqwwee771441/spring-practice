package example;

import org.springframework.transaction.annotation.Transactional;

public class SomeService {
	private AnyService anyService;
	
	@Transactional
	public void some() {
		this.anyService.any();
	}
	
	public void setAnyService(AnyService as) {
		this.anyService = as;
	}
}
