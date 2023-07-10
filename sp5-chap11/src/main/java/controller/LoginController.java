package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@GetMapping("/member/login")
	public String form() {
		return "member/loginForm";
	}
	
	@PostMapping("/member/login")
	public String login() {
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.PUT)
	public String example() {
		return "member/login";
	}
}
