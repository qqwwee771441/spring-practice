package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.Member;
import spring.MemberDao;
import spring.MemberRegisterService;

@Controller
public class _RestMemberController {
	private MemberDao memberDao;
	private MemberRegisterService registerService;
	
	@RequestMapping(path="/api/members", method=RequestMethod.GET)
	@ResponseBody
	public List<Member> members() {
		return new ArrayList<>(memberDao.selectAll());
	}
}
