package ProjectCovid19;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306?serverTimezone=Asia/Seoul";

	private static final String USER = "root";

	private static String PASS = "chldpdms4056@";
	private static String NAME;

	private static Driver driver;
	private static Login login;
	
	// 생성자
	public static Connection makeConnection() {

		Connection conn = null;
		Statement state = null;
		
		String url = "jdbc:mysql://localhost:3306/" + NAME;
		try {
			Class.forName(JDBC_DRIVER);
			conn = java.sql.DriverManager.getConnection(url, USER, PASS);
			System.out.println("Connecting...");
			
			// sqㅣ문을 저장할 String
			String sql;
			sql = "SELECT * FROM person";
			ResultSet rs = state.executeQuery(sql); 
			
			
			while(rs.next()) {
				String number = rs.getString("확진일");
				String name = rs.getString("국적");
				System.out.println("확진일: " + number + "국적" + name + "\n");
				
				
			}
			// 닫아주기
			rs.close();
			state.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// 클래스가 제대로 설치되어 있는지 검사
			System.out.println("드라이버가 존재하지 않습니다" + e);
			return null;
		} catch (SQLException e) {
			System.out.println("오류:" + e);
			return null;
		} finally { // 무조건 실행
			try {
				if(state != null)
					state.close();
			}catch(SQLException ex1) {
				
			}
			try {
				if(conn != null)
					conn.close();
			}catch(SQLException ex1) {
				
			}
		}
		return conn;

	}

	// mainProcess와 연동
	public static void setMain(Driver driver,Login log) {
		DB.driver = driver;
		DB.login = log;
	}
}
