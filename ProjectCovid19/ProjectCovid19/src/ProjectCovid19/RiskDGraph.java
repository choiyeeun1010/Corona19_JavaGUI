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
	private ArrayList<String> range = new ArrayList<String>();
	private String yIndex;// y축 입력값

	// 가져온 값
	private String design;
	private ArrayList<String> x = new ArrayList<String>();
	private ArrayList<String> data = new ArrayList<String>();
	private String myDate;

	public RiskDGraph(ArrayList<String> x, ArrayList<String> data, String myDate, String design) {

		this.x = x;
		this.data = data;
		this.myDate = myDate;
		this.design = design;

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
			int a = 0;
			int b = 405;
			int temp1 = 0;
			int temp2 = 80;
			for (int cnt = 80; cnt < 400; cnt += 20) {
				g.setColor(new Color(189, 189, 189));
				g.drawLine(100, cnt, 800, cnt);
				g.setColor(new Color(0, 0, 0));	
				
				
				if (min <= 70) {
					temp1 += 5;
					System.out.println("temp: " + temp1);

					range.add(Integer.toString(temp1));
					yIndex = Integer.toString(temp1);
				}else {
					temp2 += 5;
					range.add(Integer.toString(temp2));
					yIndex = Integer.toString(temp2);
				}
				b -= 20;
				g.drawString(range.get(a), 75, b);
				a++;
			}
			
			// x축
			int i = 0;
			for (int cnt = 125; cnt < 900 && i < data.size(); cnt = cnt + 50) {
				g.drawString(x.get(i), cnt, 425);
				i++;
			}

			// 막대그리기
			int[] r = new int[data.size()];
			int[] s = new int[data.size()];
			for (i = 0; i < data.size(); i++) {
				int mg = numberData[i];
				System.out.println("mg: " + mg);
				if (mg == 0) {
					if (!design.equals("Bar")) {
						r[i] = 145 + i * 100 + 14;
						s[i] = 400;
					}
					continue;
				}
				for (int j = 0; j < data.size(); j++) {

					if (design.equals("Bar")) {
						if(mg >= 85) {
							g.setColor(new Color(213, 247, 248));
							g.fillRect(130 + i * 50, 50, 30, 350);
						}else {
							g.setColor(new Color(213, 247, 248));
							g.fillRect(130 + i * 50, 400-mg*4, 30, mg*4);
						}
					} 
//					else { // 꺾은선 그래프 좌표
//						r[i] = 130 + i * 50;
//						s[i] = 400-mg*4;
//						
//					}
				}
			}
			// 꺾은선 그래프 그리기
//			g2.setStroke(new BasicStroke(3));
//			g2.setColor(Color.black);
//			g2.drawPolyline(r, s, 13);
//			g2.setStroke(new BasicStroke(1));

		}
	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == close) { 
				System.out.println("닫기 버튼 클릭");
				setVisible(false);
			}
		}
	}

}
