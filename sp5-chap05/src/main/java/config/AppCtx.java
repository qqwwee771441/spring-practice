package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import spring.MemberDao;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;
import spring.ChangePasswordService;
import spring.MemberPrinter;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"spring"})
public class AppCtx {
	@Bean
	@Qualifier("printer")
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
