package ProjectCovid19;


import javax.swing.*;
import java.awt.*;

// 이미지를 그려주기 위한 클래스
class ImagePanel extends JPanel{
	private Image img;
	
	public ImagePanel(Image img) {
		this.img = img;
		setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		setLayout(null);
	}
	
	// 그리는 메소드
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}

public class MainHome extends JPanel{
	private JPanel main;
	private JLabel name;
	
	public MainHome() {
		
		// 이미지 생성
		ImagePanel panel = new ImagePanel(new ImageIcon("./images/background.jpg").getImage());
		
		// 패널에 이미지 추가
		add(panel);
		setLayout(new BorderLayout());
		setSize(800, 400);
		main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setBackground(Color.WHITE);
		name = new JLabel("<html>60162180  조민수<br>60162117  문정환<br>60150464  임재범<br>60192226  이유정<br>60192249  최예은<br><br><br><br></html>");
		name.setHorizontalAlignment(JLabel.CENTER);
		main.add(name, BorderLayout.SOUTH);
		add(main);
		main.setVisible(true);
	}
}
