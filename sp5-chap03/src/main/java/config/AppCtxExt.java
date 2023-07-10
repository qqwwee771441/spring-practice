package config;

import java.util.Map;

import spring.MemberDao;

public class AppCtxExt extends AppCtx {
	private Map<String, Object> beans;
	
	@Override
	public MemberDao memberDao() {
		if (!beans.containsKey("memberDao"))
			beans.put("memberDao", super.memberDao());
		return (MemberDao) beans.get("memberDao");
	}
}
