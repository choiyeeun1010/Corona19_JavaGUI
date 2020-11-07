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
	private JButton Result;

	// 아래쪽 
	private JPanel centerPanel;
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = { "확진일", "환자번호", "접촉력", "상태"};
	private JScrollPane scrollpane;

	// 폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);

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

	private void setTable(String region, String date) {

		try {
			System.out.println("시작 월: " + date);


			// 1월 넘어가는거 나오면 표시는 하되 데이터가 없습니다. 하고싶은뎅,,,, - 기준으로 읽어서 앞이 2019인 경우는 null로하도록하자
			connection = DB.makeConnection();
			String pureDate = date.substring(0, 1);
			pureDate = pureDate + "%";
			
			String sql;

			sql = "SELECT 확진일, 환자번호, 접촉력, 상태 FROM corona WHERE 지역 = ? AND 확진일 LIKE ?";

			
			ResultSet rs;
			PreparedStatement pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, region);
			pstmt.setString(2, date);

			// 실행결과 저장
			rs = pstmt.executeQuery();

			// 테이블 값 설정
			model = (DefaultTableModel) table.getModel(); // 테이블 설정 전에 초기화시키기
			model.setNumRows(0);

			int count = 1;
			Object[] result = new Object[4];
			while (rs.next()) {
				// 날짜가 없는건 아예 읽지를 않는다....
				System.out.println(rs.getString(1));
				model.insertRow(1, new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
				
				count++;
			}

			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == Result) { // 조회버튼 클릭시
				System.out.println("조회버튼 눌림");

			String region = "";
			String date = "";

			String temp = ""; // 오른쪽 라벨 설명

			region = (String) regionCombo.getSelectedItem();
			date = (String) monthCombo.getSelectedItem();
			setTable(region, date);
			
			temp += "<html><br><br><br>다음은 ";
			temp += region + "에 대한 <br>2020년 " + date + "월의 확진자 입니다.";

			}
		}
	}


}
