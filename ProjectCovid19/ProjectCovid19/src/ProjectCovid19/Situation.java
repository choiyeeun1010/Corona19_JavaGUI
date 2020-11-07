package ProjectCovid19;

import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.*;

public class Situation extends JPanel {

	private Connection connection;
	private JPanel north, south;

	// 위쪽 패널
	private JPanel panel1, panel2, panel3;
	private JLabel region_Select, date_Select, text;
	private JComboBox<String> regionCombo, monthCombo, dateCombo;
	private String Region[] = { "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "기타", "노원구", 
			"도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
			"용산구", "은평구", "종로구", "중구", "중랑구"};
	private String Month[] = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월" };
	/*private String Day31[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일", "31일" };
	private String Day30[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일" };
	private String Day28[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일 ", "29일" };
*/
	private JButton Result;

	// 아래쪽 
	private JPanel centerPanel;
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = { "확진일", "환자번호", "접촉력", "상태"};
	private JScrollPane scrollpane;

	// 리스너
	Listener Listener = new Listener();
	
	public Situation() {

		this.setLayout(new GridLayout(2, 1));
		North();
		South();
		
	}
	private void North() {
		//  레이아웃 나누기
		north = new JPanel(new GridLayout(3, 1));
		north.setSize(750, 100);
		north.setBackground(Color.white);
		panel1 = new JPanel();
		panel1.setBackground(Color.white);

		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panel2.setBackground(Color.white);

		panel3 = new JPanel();
		panel3.setBackground(Color.white);


		// 레이블 
		region_Select = new JLabel("지역 선택: ");
		date_Select = new JLabel("날짜 선택 : 2020년 ");
		text = new JLabel("부터 한달간");
		
		// 콤보박스 
		regionCombo = new JComboBox<String>(Region);
		monthCombo = new JComboBox<String>(Month);
		//dateCombo = new JComboBox<String>(Day31);

		// 배경색
		regionCombo.setBackground(Color.white);
		monthCombo.setBackground(Color.white);
		//dateCombo.setBackground(Color.white);
		
//		// 월마다 다른 일수 콤보박스 
//		monthCombo.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				// TODO Auto-generated method stub
//				panel2.remove(dateCombo);
//				panel2.remove(text);
//
//				switch ((String) e.getItem()) {
//
//				case "1월":
//				case "3월":
//				case "5월":
//				case "7월":
//				case "8월":
//				case "10월":
//				case "12월": // 31일짜리
//					dateCombo = new JComboBox<String>(Day31);
//					panel2.add(dateCombo);
//					break;
//				case "4월":
//				case "6월":
//				case "9월":
//				case "11월": // 30일짜리
//					dateCombo = new JComboBox<String>(Day30);
//					panel2.add(dateCombo);
//					break;
//				case "2월": // 28일짜리
//					dateCombo = new JComboBox<String>(Day28);
//					panel2.add(dateCombo);
//					break;
//				default:
//					System.out.println("월 선택 이상");
//				}
//				dateCombo.setBackground(Color.white);
//				panel2.add(text);
//				revalidate(); // 변경사항 바로 보기
//			}
//		});
		
		// 버튼
		Result = new JButton("조회");
		
		// 리스너 추가 
		Result.addActionListener(Listener);
		
		// 패널에 추가 
		panel1.add(region_Select);
		panel1.add(regionCombo);
		
		panel2.add(date_Select);
		panel2.add(monthCombo);
		//panel2.add(dateCombo);
		panel2.add(text);
		
		panel3.add(Result);
		
		north.add(panel1);
		north.add(panel2);
		north.add(panel3);
		
		add(north);
	}
	
	private void South() {
		south = new JPanel(new GridLayout(1, 2));
		south.setBackground(Color.white);
		
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);

		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		for (int i = 0; i < 15; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[] {"", "", "", "", ""});
		}
		
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400, 135));
		
		centerPanel.add(scrollpane);
		south.add(centerPanel);
		add(south);
	}
	
//	private String getEndDate(String month, String day) {
//		int startMonth = Integer.parseInt(month);
//		int startDay = Integer.parseInt(day);
//
//		switch (startMonth) {
//		case 1:
//		case 3:
//		case 5:
//		case 7:
//		case 8:
//		case 10:// 31일짜리
//			if (startDay > 25) { // 26일부터
//				return "2020-" + (startMonth + 1) + "-" + (startDay - 25);
//			} else {
//				return "2020-" + startMonth + "-" + (startDay + 6);
//			}
//		case 12: // 12월 31일까지의 데이터만 있으므로
//			if (startDay > 25) { // 26일부터여도 12/31까지만 보여준다.
//				return "2021-1-" + (startDay - 25);
//			} else {
//				return "2020-" + startMonth + "-" + (startDay + 6);
//			}
//		case 4:
//		case 6:
//		case 9:
//		case 11:// 30일짜리
//			if (startDay > 24) { // 25일부터
//				return "2020-" + (startMonth + 1) + "-" + (startDay - 24);
//			} else {
//				return "2020-" + startMonth + "-" + (startDay + 6);
//			}
//		case 2: // 28일짜리
//			if (startDay > 22) { // 23일부터
//				return "2020-" + (startMonth + 1) + "-" + (startDay - 22);
//			} else {
//				return "2018-" + startMonth + "-" + (startDay + 6);
//			}
//		default:
//			System.out.println("이상한 날짜");
//			return null;
//		}
//	}
	

	
	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == Result) { // 조회버튼 클릭시
				System.out.println("조회버튼 눌림");
				
				String selectRegion = "";
				String selectMonth = "";
				String selectDay = "";
				
				String temp = ""; // 오른쪽 라벨 설명
				
				selectRegion = (String) regionCombo.getSelectedItem();
				selectMonth = (String) monthCombo.getSelectedItem();
				selectDay = (String) dateCombo.getSelectedItem();
				
				temp += "<html><br><br><br>다음은 ";
				temp += selectRegion + "에 대한 <br>2020년 " + selectMonth + " " + selectDay
						+ "부터 일주일간의 확진자 입니다.";

			}
		}
	}
}
