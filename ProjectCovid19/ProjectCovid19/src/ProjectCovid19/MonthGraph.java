package ProjectCovid19;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MonthGraph extends JFrame {

	Container contentpane;
	private JPanel South;
	private JButton close;
	private ArrayList<String> range = new ArrayList<String>();

	private int x, y, sum, date;
	private String region;
	private int[] month_num;
	private String design;

	public MonthGraph(String region, int date, int[] month_num, String design) {
		// 멤버 초기화
		x = 60;
		y = 350;
		sum = 0;
		this.region = region;
		this.date = date;
		this.month_num = month_num;
		this.design = design;

		// 프레임
		this.setTitle(region + " 확진자 월별 현황");
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
	}

	class drawGraph extends JPanel {
		public void paint(Graphics go) {
			Graphics2D g = (Graphics2D) go;
			Graphics2D g2 = (Graphics2D) g;
			super.paint(g);
			g.clearRect(0, 0, 900, 500);
			g.drawLine(100, 400, 800, 400); // x축
			g.drawLine(100, 60, 100, 400); // y축

			// 세로선
			int a = 0;
			int b = 405;
			int temp = 0;
			for (int cnt = 80; cnt < 400; cnt += 20) {
				g.setColor(new Color(189, 189, 189));
				g.drawLine(100, cnt, 800, cnt);
				g.setColor(new Color(0, 0, 0));
				temp += 5;
				range.add(Integer.toString(temp));
				b -= 20;
				g.drawString(range.get(a), 75, b);
				a++;
			}

			for (int i = 0; i < 9; i++) {
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString((i + 1)) + "월", 130 + i * 65, 425);
			}

			// 막대그리기
			int[] r = new int[9];
			int[] s = new int[9];
			for (int i = 0; i < 9; i++) {

				if (design.equals("Bar")) {

					g.setColor(Color.BLACK);
					System.out.println("month_num: " + month_num[i]);

					g.setColor(new Color(39, 204, 188));
					if (i + 1 == date) {
						g.setColor(Color.BLACK);
					}
					g.fillRect(130 + i * 65, 400 - month_num[i] * 4, 30, month_num[i] * 4);
					sum += month_num[i];
					
					// 평균
					g.setColor(Color.BLACK);
					g.drawString("평균", 715, 425);
					sum = Math.round(sum / 10);
					g.setColor(Color.BLACK);
					g.fillRect(715, 400 - sum * 4, 30, sum * 4);
					
				}else {
					// 꺾은선 그래프 좌표
					r[i] = 130 + i * 65;
					s[i] = 400 - month_num[i] * 4;
				}
			}
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.black);
			g2.drawPolyline(r, s, 9);
			g2.setStroke(new BasicStroke(1));
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
