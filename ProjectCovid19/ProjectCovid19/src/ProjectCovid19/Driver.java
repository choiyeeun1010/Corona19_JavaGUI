package ProjectCovid19;

public class Driver {
	static Main main;
	public static void main(String[] args) {
		main = new Main();
		main.setVisible(true);
		DB.makeConnection();
	}
}
