package controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.DuplicateMemberException;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@RestController
public class RestMemberController {
	private MemberDao memberDao;
	private MemberRegisterService registerService;
	
	@GetMapping("/api/members")
	public List<Member> members() {
		return new ArrayList<>(memberDao.selectAll());
	}
	
	@GetMapping("api/_members/{id}")
	public Member _member(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Member member = memberDao.selectById(id);
		if (member == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return member;
	}
	
	@GetMapping("api/__members/{id}")
	public ResponseEntity<Object> __member(@PathVariable Long id) {
		Member member = memberDao.selectById(id);
		if (member == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(member);
	}
	
	@GetMapping("api/members/{id}")
	public Member member(@PathVariable Long id) {
		Member member = memberDao.selectById(id);
		if (member == null) {
			throw new MemberNotFoundException();
		}
		return member;
	}
	
	@PostMapping("api/_members")
	public void _newMember(
			@RequestBody @Valid RegisterRequest regReq, Errors errors, 
			HttpServletResponse response) throws IOException {
		try {
			new RegisterRequestValidator().validate(regReq, errors);
			Long newMemberId = registerService.regist(regReq);
			response.setHeader("Location", "/api/members/" + newMemberId);
			response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (DuplicateMemberException dupEx) {
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}
	}
	
	@PostMapping("/api/members")
	public ResponseEntity<Object> newMember(@RequestBody @Valid RegisterRequest regReg,
			Errors errors) {
		if (errors.hasErrors()) {
			String errorCodes = errors.getAllErrors()
					.stream()
					.map(error -> error.getCodes()[0])
					.collect(Collectors.joining("."));
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("errorCodes = " + errorCodes));
		}
		try {
			Long newMemberId = registerService.regist(regReg);
			URI uri = URI.create("/api/members/" + newMemberId);
			return ResponseEntity.created(uri).build();
		} catch (DuplicateMemberException dupEx) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void setRegisterService(MemberRegisterService registerService) {
		this.registerService = registerService;
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoData() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
	}
}
