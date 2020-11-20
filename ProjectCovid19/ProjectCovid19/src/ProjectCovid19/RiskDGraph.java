package ProjectCovid19;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;

public class RiskDGraph extends JFrame {
	// 패널
	Container contentpane;
	private JPanel South;
	private JButton close;

	// 변수
	private int max; // 최댓값
	private int min; // 최소값
	private Double[] range = new Double[16];
	private String yIndex;// y축 입력값

	// 가져온 값
	private String myGas;
	private String design;
	private ArrayList<String> x = new ArrayList<String>();
	private ArrayList<String> data = new ArrayList<String>();
	private String myDate;

	public RiskDGraph(ArrayList<String> x, ArrayList<String> data, String myDate) {

		this.x = x;
		this.data = data;
		this.myDate = myDate;

		this.setTitle(this.myDate + "의 확진자 수 그래프");
		this.setSize(900, 600);
		this.setLocation(450, 250);
		this.setBackground(Color.white);
		// 패널 및 클래스
		contentpane = getContentPane();
		contentpane.setLayout(new BorderLayout());
		South = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));

		// 버튼
		close = new JButton("close");

		// 색상 지정
		contentpane.setBackground(Color.white);
		close.setBackground(Color.white);
		South.setBackground(Color.white);

		// 리스너 호출
		Listener listener = new Listener();
		close.addActionListener(listener);

		// 추가
		South.add(close);
		contentpane.add(new drawGraph(), BorderLayout.CENTER);
		contentpane.add(South, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	class drawGraph extends JPanel {
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g.clearRect(0, 0, 900, 500);
			g.drawLine(100, 400, 800, 400); // x축
			g.drawLine(100, 60, 100, 400); // y축

			// 데이터 값들을 저장
			int[] numberData = new int[data.size()];
			System.out.println("size" + data.size());
			for (int i = 0; i < data.size(); i++) {
				try {
					numberData[i] = Integer.parseInt(data.get(i));
				} catch (NumberFormatException e) {
					numberData[i] = 0;
				}
			}

			// 최대값
			max = numberData[0];
			for (int i = 0; i < data.size(); i++) {
				max = max > numberData[i] ? max : numberData[i];
			}

			// 최소값
			for (int i = 0; i < data.size(); i++) {
				min = numberData[i];
				if (min != 0)
					break;
			}

			for (int i = 0; i < data.size(); i++) {
				if (numberData[i] == 0)
					continue;
				min = min < numberData[i] ? min : numberData[i];
			}
			System.out.println("max: " + max);

			System.out.println("min: " + min);
			double val = Math.round(((max - min) / 15) * 10000) / 10000.0;

			// y축
			for (int cnt = 80; cnt < 400; cnt = cnt + 20) {
				g.setColor(new Color(189, 189, 189));
				g.drawLine(100, cnt, 800, cnt);
				g.setColor(new Color(0, 0, 0));

			}
			yIndex = Double.toString(max);
			g.drawString("80", 75, 85);
			g.drawString("75", 75, 105);
			g.drawString("70", 75, 125);
			g.drawString("65", 75, 145);
			g.drawString("60", 75, 165);
			g.drawString("55", 75, 185);
			g.drawString("50", 75, 205);
			g.drawString("45", 75, 225);
			g.drawString("40", 75, 245);
			g.drawString("35", 75, 265);
			g.drawString("30", 75, 285);
			g.drawString("25", 75, 305);
			g.drawString("20", 75, 325);
			g.drawString("15", 75, 345);
			g.drawString("10", 75, 365);
			g.drawString(" 5", 75, 385);
			g.drawString(" 1", 75, 400);

			// x축
			int i = 0;
			for (int cnt = 125; cnt < 900 && i < data.size(); cnt = cnt + 50) {
				g.drawString(x.get(i), cnt, 425);
				i++;
			}

			

		}
	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == close) { // 결과보기 버튼을 눌렀을 경우
				System.out.println("닫기 버튼 클릭");
				setVisible(false);
			}
		}
	}

}
