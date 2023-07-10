package dbquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import spring.Member;

public class DbQuery {
	private DataSource dataSource;
	
	public DbQuery(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			try (Statement stmt = conn.createStatement(); 
					ResultSet rs = stmt.executeQuery("selecet count(*) from MEMBER")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
		}
	}
	
	public Member selectByEmail(String email) throws SQLException {
		Member member;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/spring5fs", "spring5", "spring5");
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("select * from MEMBER where EMAIL = ?");
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			conn.commit();
			if (rs.next()) {
				member = new Member(rs.getString("EMAIL"), 
						rs.getString("PASSWORD"), 
						rs.getString("NAME"), 
						rs.getTimestamp("REGDATE").toLocalDateTime());
				member.setId(rs.getLong("ID"));
				return member;
			} else {
				return null;
			}
		} catch (SQLException ex) {
			if (conn != null)
				try { conn.rollback(); } catch (SQLException e) {}
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null)
				try { rs.close(); } catch (SQLException e2) {}
			if (pstmt != null)
				try { pstmt.close(); } catch (SQLException e1) {}
			if (conn != null)
				try { conn.close(); } catch (SQLException e) {}
		}
	}
}
