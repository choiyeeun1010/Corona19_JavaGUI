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
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/corona19?serverTimezone=Asia/Seoul";

	private static final String USER = "root";

	private static String PASS = "pw1234";
	private static String NAME;

	private static Driver driver;
	private static Login login;

	public static Connection makeConnection() {

		Connection conn = null;

		String url = DB_URL;
		try {
			Class.forName(JDBC_DRIVER);
			conn = java.sql.DriverManager.getConnection(url, USER, PASS);
			System.out.println("Connecting...");
		} catch (ClassNotFoundException e) {
			// 클래스가 제대로 설치되어 있는지 검사
			System.out.println("드라이버가 존재하지 않습니다" + e);
			return null;
		} catch (SQLException e) {
			System.out.println("오류:" + e);
			return null;
		}
		return conn;

	}

}
