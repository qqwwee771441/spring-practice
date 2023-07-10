package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Member> memRowMapper = new RowMapper<Member>() {
		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member member = new Member(
					rs.getString("EMAIL"),
					rs.getString("PASSWORD"),
					rs.getString("NAME"),
					rs.getTimestamp("REGDATE").toLocalDateTime());
			member.setId(rs.getLong("ID"));
			return member;
		}
	};
	
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
    public Member selectByEmail(String email) {
    	class MemberRowMapper implements RowMapper<Member> {
    		@Override
    		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
    			Member member = new Member(
    					rs.getString("EMAIL"),
    					rs.getString("PASSWORD"),
    					rs.getString("NAME"),
    					rs.getTimestamp("REGDATE").toLocalDateTime());
    			member.setId(rs.getLong("ID"));
    			return member;
    		}
    	}
    	
    	List<Member> results = jdbcTemplate.query(
    			"select * from MEMBER where EMAIL = ?", 
    			(ResultSet rs, int rowNum) -> {
    				Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
    			},
    			email);
    	
        return results.isEmpty() ? null : results.get(0);
    }
    
    public Member selectById(Long memId) {
    	List<Member> results = jdbcTemplate.query(
    			"select * from MEMBER where ID = ?", memRowMapper, memId);
    	return results.isEmpty() ? null : results.get(0);
    }

    public void insert(Member member) {
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	jdbcTemplate.update(
    			new PreparedStatementCreator() {
    				@Override
    				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    					PreparedStatement pstmt = con.prepareStatement(
    							"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " + "values (?, ?, ?, ?)",
    							new String[] {"ID"});
    					pstmt.setString(1, member.getEmail());
    					pstmt.setString(2, member.getPassword());
						pstmt.setString(3, member.getName());
						pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
						return pstmt;
    				}
    			},
    			keyHolder);
    	Number keyValue = keyHolder.getKey();
    	member.setId(keyValue.longValue());
    }

    public void update(Member member) {
    	jdbcTemplate.update(
    			"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
    			member.getName(), member.getPassword(), member.getEmail());
    }
    
    public void _update(Member member) {
    	jdbcTemplate.update(
    			new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pstmt = con.prepareStatement("insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) values (?, ?, ?, ?)");
						pstmt.setString(1, member.getEmail());
						pstmt.setString(2, member.getPassword());
						pstmt.setString(3, member.getName());
						pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
						return pstmt;
					}
				});
    }
    
    public Collection<Member> selectAll() {
    	List<Member> results = jdbcTemplate.query(
    			"select * from MEMBER", 
    			new RowMapper<Member>() {
    	    		@Override
    	    		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
    	    			Member member = new Member(
    	    					rs.getString("EMAIL"),
    	    					rs.getString("PASSWORD"),
    	    					rs.getString("NAME"),
    	    					rs.getTimestamp("REGDATE").toLocalDateTime());
    	    			member.setId(rs.getLong("ID"));
    	    			return member;
    	    		}
    	    	});
    	
    	return results;
    }
    
    public int count() {
    	List<Integer> results = jdbcTemplate.query(
    			"select count(*) from MEMBER", 
    			new RowMapper<Integer>() {
    				@Override
    				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
    					return rs.getInt(1);
    				}
    			});
    	
    	return results.get(0); 
    }
    
    public int _count() {
    	Integer count = jdbcTemplate.queryForObject("select count(*) from MEMBER", Integer.class);
    	return count;
    }
    
    public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to) {
    	List<Member> results = jdbcTemplate.query(
    			"select * from MEMBER where REGDATE between ? and ? order by REGDATE desc",
    			new RowMapper<Member>() {
    				@Override
    				public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
    					Member member = new Member(
    							rs.getString("EMAIL"),
    							rs.getString("PASSWORD"),
    							rs.getString("NAME"),
    							rs.getTimestamp("REGDATE").toLocalDateTime());
    					member.setId(rs.getLong("ID"));
    					return member;
    				}
    			},
    			from, to);
    	return results;
    }
}
