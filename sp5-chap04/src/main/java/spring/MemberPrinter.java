package spring;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class MemberPrinter {
	private DateTimeFormatter dateTimeFormatter;
	private DateTimeFormatter dateTimeFormatter1;
	private DateTimeFormatter dateTimeFormatter2;
	
	@Autowired(required = false)
	private DateTimeFormatter dateTimeFormatter3;
	@Autowired
	private Optional<DateTimeFormatter> formatterOpt;
	@Autowired
	@Nullable
	private DateTimeFormatter dateTimeFormatter5;
	
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}
	
	public void print(Member member) {
		if (dateTimeFormatter == null) {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime());
		} else {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}
	
	public void print1(Member member) {
		DateTimeFormatter dateTimeFormatter = formatterOpt.orElse(null);
		if (dateTimeFormatter == null) {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime());
		} else {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}
	
	@Autowired(required = false)
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
	
	@Autowired
	public void setDateFormatter1(Optional<DateTimeFormatter> formatterOpt) {
		if (formatterOpt.isPresent()) {
			this.dateTimeFormatter1 = formatterOpt.get();
		} else {
			this.dateTimeFormatter1 = null;
		}
	}
	
	@Autowired
	public void setDateFormatter2(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter2 = dateTimeFormatter;
	}
}
