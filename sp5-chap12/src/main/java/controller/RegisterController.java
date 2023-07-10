package controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private MemberRegisterService memberRegisterService;
	
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}
	
	@RequestMapping("/step1")
	public String handleStep1() {
		System.out.println("\thandleStep1()");
		return "register/step1";
	}
	
	@GetMapping("/step2")
	public String handleStep2Get() {
		String[] path = {
				"/register/step1",
				"step1",
				"http://localhost:8080/sp5-chap11/register/step1"
		};
		return "redirect:" + path[0];
	}
	
	@PostMapping("/step2")
	public String handleStep2(
			//HttpServletRequest request, 
			@RequestParam(value="agree", defaultValue="false") Boolean agree,
			Model model) {
		//String agreeParam = request.getParameter("agree");
		//if (agreeParam == null || agreeParam.equals("true"))
		//	return "register/step1";
		System.out.println("\thandleStep2()");
		if (!agree)
			return "register/step1";
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}
	
	@PostMapping("/step3")
	public String handleStep3(@Valid @ModelAttribute("registerRequest") RegisterRequest regReq, Errors errors) {
		System.out.println("\thandleStep3()");
		//new RegisterRequestValidator().validate(regReq, errors);
		if (errors.hasErrors())
			return "register/step2";
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}
	
	/*@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
		//binder.addValidator(new RegisterRequestValidator());
	}*/
	
	@RequestMapping("/_step3")
	public String _handleStep3(HttpServletRequest request) {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		RegisterRequest regReq = new RegisterRequest();
		regReq.setEmail(email);
		regReq.setName(name);
		regReq.setPassword(password);
		regReq.setConfirmPassword(confirmPassword);
		
		return "register/step3";
	}
}
