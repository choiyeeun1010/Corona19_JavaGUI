package ProjectCovid19;

import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Total extends JPanel {

	private Connection connection;
	private JPanel north, south;

	// 위쪽 패널
	private JPanel panel1, panel2, panel3;
	private JLabel string, region_Select, date_Select;
	private JComboBox<String> regionCombo, monthCombo;
	private String Region[] = { "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "기타", "노원구", "도봉구", "동대문구",
			"동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구" };
	private String Month[] = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };
	private JButton search;

	// 아래쪽
	private JPanel centerPanel;
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = { "연번", "확진일", "환자번호", "지역", "접촉력", "상태" };
	private JScrollPane scrollpane;

	// 리스너
	Listener Listener = new Listener();

	// 그래픽
	private int[] month_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 수정
	private JFrame frame;

	public Total() {
		// 레이아웃 설정
		this.setLayout(new GridLayout(2, 1));
		North();
		South();
	}

	private void North() {
		// 레이아웃 나누기
		north = new JPanel(new GridLayout(3, 1));
		north.setSize(750, 100);
		north.setBackground(Color.white);

		panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		panel1.setBackground(Color.white);

		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		panel2.setBackground(Color.white);

		panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
		panel3.setBackground(Color.white);

		// 안내문구
		string = new JLabel("지역별, 월별로 확진자의 전체적인 정보를 보여줍니다.");
		string.setFont(string.getFont().deriveFont(15.0f));
		// 레이블
		region_Select = new JLabel("지역 : ");
		date_Select = new JLabel("날짜 : 2020년 ");

		// 콤보박스
		regionCombo = new JComboBox<String>(Region);
		monthCombo = new JComboBox<String>(Month);

		// 배경색
		regionCombo.setBackground(Color.white);
		monthCombo.setBackground(Color.white);

		// 버튼
		search = new JButton("조회");
		search.setBackground(Color.white);

		// 리스너 추가
		search.addActionListener(Listener);

		// 패널에 추가
		panel1.add(string);
		panel2.add(region_Select);
		panel2.add(regionCombo);
		panel2.add(date_Select);
		panel2.add(monthCombo);
		panel3.add(search);

		north.add(panel1);
		north.add(panel2);
		north.add(panel3);

		add(north);
	}

	private void South() {
		// 아래쪽 패널 레이아웃 설정
		south = new JPanel(new GridLayout(1, 1));
		south.setBackground(Color.white);

		// 패널 설정
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);

		// 모델에 테이블 원본데이터를 복사해두고 원본데이터를 건들지 않고
		// 모델데이터를 조작하여 테이블을 변경
		// 모델 설정
		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		for (int i = 0; i < 15; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[] { "", "", "", "", "" });
		}

		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 135));

		centerPanel.add(scrollpane);
		south.add(centerPanel);
		add(south);
	}

	private void setTable(String region, String date) {

		try {
			System.out.println("시작 월: " + date);

			connection = DB.makeConnection();
			String pureDate = date.substring(0, 1);
			pureDate = pureDate + "%";

			String sql;

			sql = "SELECT 연번, 확진일, 환자번호, 지역, 접촉력, 상태 FROM person WHERE 지역 = ? AND 확진일 LIKE ? ORDER BY 연번 DESC";

			ResultSet rs;
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, region);
			pstmt.setString(2, pureDate);

			// 실행결과 저장
			rs = pstmt.executeQuery();

			// 테이블 값 설정
			model = (DefaultTableModel) table.getModel(); // 테이블 설정 전에 초기화시키기
			model.setNumRows(0);

			if (rs.next()) {
				do {
					model = (DefaultTableModel) table.getModel();
					model.insertRow(0, new Object[] { rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6) });
				} while (rs.next());
			} else {
				// 만약 결과가 없다면 결과 없음으로 출력
				model = (DefaultTableModel) table.getModel();
				model.insertRow(0, new Object[] { "결과없음", "결과없음", "결과없음", "결과없음", "결과없음", "결과없음" });
			}

			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	// 조회버튼 클릭시 발생하는 이벤트 리스너
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == search) { // 조회버튼 클릭시
				System.out.println("조회버튼 눌림");
				String region = "";
				String date = "";

				region = (String) regionCombo.getSelectedItem();
				date = (String) monthCombo.getSelectedItem();
				// 함수 호출
				setTable(region, date);
			}
		}
	}

}
