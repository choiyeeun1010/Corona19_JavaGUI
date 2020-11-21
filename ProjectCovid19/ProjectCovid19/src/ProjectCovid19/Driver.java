package ProjectCovid19;

import java.sql.Connection;

public class Driver {
	static Main main;
	static Login login;

	public static void main(String[] args) {
		
		main = new Main();
		main.setVisible(true);
		DB.makeConnection();

		//Driver.login = new Login();
		//DB.setMain(driver,login); // 로그인 창에게 메인 클래스 보내기
		// 프로그램 종료 시 데이터베이스를 삭제한다.
		/*Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				DB.deleteDatabase();
			}
		});
		//main = new Main();
		//main.setVisible(true);
	}
	public void StartMain() {
		//login.dispose();
		this.main = new Main();
		main.setVisible(true);
	}*/
	}
}
