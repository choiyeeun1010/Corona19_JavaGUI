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
		this.setSize(1300, 600);
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
			g.drawLine(100, 400, 1200, 400); // x축
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
				g.drawLine(100, cnt, 1200, cnt);
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
				if (!(x.get(i) == "경북" && x.get(i) == "고양시" && x.get(i) == "김포" && x.get(i) == "대구" && x.get(i) == "아산"
						&& x.get(i) == "인천시" && x.get(i) == " 평택" && x.get(i) == "국외" && x.get(i) == "대전"
						&& x.get(i) == "부천시" && x.get(i) == "성남시" && x.get(i) == "안양시" && x.get(i) == "양주시"
						&& x.get(i) == "용인시" && x.get(i) == "재외국민" && x.get(i) == "천안" && x.get(i) == "파주"
						&& x.get(i) == "남양주시" && x.get(i) == "미국" && x.get(i) == "스페인" && x.get(i) == "시흥"
						&& x.get(i) == "화성" && x.get(i) == "구리" && x.get(i) == "부평" && x.get(i) == "양평"
						&& x.get(i) == "의왕" && x.get(i) == "군포" && x.get(i) == "수원시" && x.get(i) == "안산"
						&& x.get(i) == "주소불명" && x.get(i) == "경기광주" && x.get(i) == "광명" && x.get(i) == "기타"
						&& x.get(i) == "하남" && x.get(i) == "포천" && x.get(i) == "타시도")) {
					g.drawString(x.get(i), cnt, 425);
					i++;
				}

			}

			// 막대그리기
			int[] r = new int[7];
			int[] s = new int[7];
			for (i = 0; i < 7; i++) {
				double mg = numberData[i];
				System.out.println(mg);

				if (mg == 0) {
					continue;
				}

				for (int j = 0; j < 7; j++) {

					g.setColor(new Color(248, 224, 230));
					if (mg == range[j]) {
						g.fillRect(145 + i * 100, 80 + (j * 20), 30, 320 - j * 20);
					} else if (mg < range[j] && mg > range[j + 1]) {
						g.fillRect(145 + i * 100, 80 + (j * 20 + 10), 30, 320 - (j * 20 + 10));
					}
					if (mg < range[7]) {
						g.fillRect(145 + i * 100, 80 + (15 * 20 + 10), 30, 320 - (15 * 20 + 10));
					}

				}
			}
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.black);
			g2.drawPolyline(r, s, 7);
			g2.setStroke(new BasicStroke(1));

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
