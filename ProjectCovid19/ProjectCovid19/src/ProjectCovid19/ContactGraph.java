package ProjectCovid19;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;

import javax.swing.*;

import java.awt.*;

import java.util.Map;

public class ContactGraph extends JFrame {
	
	// 패널
	Container contentpane;
	private JPanel South;
	private JButton close;

// 변수
	
	// 원 그래프 항목 이름
	private ArrayList<Integer> data = new ArrayList<Integer>();
	
	// 원 그래프 항목 색상 
	private ArrayList<Color> color = new ArrayList<Color>();
	
	// 원 그래프 항목별 각도
	private ArrayList<Integer> arcAngle = new ArrayList<Integer>();
	
	// 원 그래프 항목별 데이터 값
	private ArrayList<String> itemName = new ArrayList<String>();
	
	// 선택한 지역;
	private String region = null;
	
	public ContactGraph(String region, Map data) {
		
		//선택한 지역명
		this.region = region;
		
		//선택한 지역의 접촉력 조회
		System.out.println(data);
		
		itemName.add("해외 접촉");
		itemName.add("해외 접촉 추정");
		itemName.add("국내 접촉");
		itemName.add("확인 중");
		
		for (int i = 0; i < itemName.size() ; i++) {
			if(data.containsKey(itemName.get(i))) {
				this.data.add( (Integer) data.get(itemName.get(i) ));
			} else {
				this.data.add(0);
			}
		}
		
		color.add(new Color(0, 63, 92));
		color.add(new Color(122, 81, 149));
		color.add(new Color(239, 86, 117));
		color.add(new Color(255, 166, 0));

		this.setTitle(region + " 접촉 현황");
		this.setSize(900, 600);
		this.setLocation(450, 250);

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
		
		public void paint(Graphics originG) {
			
			Graphics2D g = (Graphics2D) originG.create();
			
			// 초기 각도
			int startAngle = 0;
			
			// 데이터 값 합
			int sum = 0;
			
			// 총 확진자 수 라벨
			String allPeople = region + " 총 확진자 수";
			
			// 모든 항목 총합
			for (int i = 0; i < data.size(); i++) {
				sum += data.get(i);
			}
			
			// 각 항목별 각도 추가
			for (int i = 0; i < data.size(); i++) {
				arcAngle.add( (int)Math.round((double)data.get(i)/(double) sum*360) );
			}
			
			super.paint(g);
			g.clearRect(0, 0, 900, 500);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// 총 확진자 수 글짜 크기 설정 및 작성
			g.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			g.drawString(allPeople + " " + sum + "명", 900 / 2 - allPeople.length()*10 , 50);
			
			// 항목별 확진자 수 글짜 크기 설정
			g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			
			int length = 0;
			
			for(int i=0;i<data.size();i++){
				g.setColor(color.get(i));
				g.drawString(itemName.get(i)+" "+ data.get(i) + "명", 200 + length*10 + i*80,100);
				length += itemName.get(i).length();
			}
 
			for(int i=0;i<data.size();i++){
				g.setColor(color.get(i));
				if (data.get(i) < 150) {
					g.fillArc(300,150,300,300,startAngle,arcAngle.get(i) + 1);
				} else {
					g.fillArc(300,150,300,300,startAngle,arcAngle.get(i));
				}
				startAngle = startAngle + arcAngle.get(i);
			}
			

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
