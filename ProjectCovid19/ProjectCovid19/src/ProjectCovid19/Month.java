package ProjectCovid19;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class Month extends JPanel {
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
	private JPanel centerPanel, choice;
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = { "연번", "확진일" };
	private JScrollPane scrollpane;

	// 그래프 버튼
	private JButton showBarGraph, showLineGraph;

	// 리스너
	Listener Listener = new Listener();

	// 그래픽
	private int[] month_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 수정
	private JFrame frame;

	public Month() {
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
		string = new JLabel("선택한 지역의 확진자 수를 월별로 보여줍니다.");
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
		south = new JPanel(new GridLayout(2, 1));
		south.setBackground(Color.white);

		// 패널 설정
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);

		choice = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 40));
		choice.setBackground(Color.white);

		// 버튼 설정
		showBarGraph = new JButton("막대 그래프 보기");
		showBarGraph.setBackground(Color.white);

		showLineGraph = new JButton("꺾은선 그래프 보기");
		showLineGraph.setBackground(Color.white);

		// 리스너 달기
		showLineGraph.addActionListener(Listener);
		showBarGraph.addActionListener(Listener);

		// 모델에 테이블 원본데이터를 복사해두고 원본데이터를 건들지 않고
		// 모델데이터를 조작하여 테이블을 변경
		// 모델 설정
		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		for (int i = 0; i < 15; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[] { "", "", "", "", "" });
		}

		// 테이블 설정
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400, 100));

		// 패널에 추가
		centerPanel.add(scrollpane);
		choice.add(showLineGraph);
		choice.add(showBarGraph);

		south.add(centerPanel);
		south.add(choice);
		add(south);
	}

	// 확진자 조회 결과를 보여줌
	private void setTable(String region, String date) {

		try {
			System.out.println("시작 월: " + date);

			connection = DB.makeConnection();
			int pureDate = take_num(date);
			String sql;

			sql = "SELECT 연번, 확진일, 환자번호, 지역, 접촉력, 상태 FROM person WHERE 지역 = ? ORDER BY 연번 DESC";
			ResultSet rs;
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, region);
			// pstmt.setString(2, pureDate);

			// 실행결과 저장
			rs = pstmt.executeQuery();

			// 테이블 값 설정
			model = (DefaultTableModel) table.getModel(); // 테이블 설정 전에 초기화시키기
			model.setNumRows(0);
			// 막대그래프 값 초기화
			for (int i = 0; i < month_num.length; i++) { // 수정
				month_num[i] = 0; // 수정
			}

			if (rs.next()) {
				do {
					// 막대그래프 계산
					int num = take_num(rs.getString(2)); // 수정
					month_num[num - 1]++; // 수정
					// 해당 월 데이터 출력
					if (pureDate == num) { // 수정
						model = (DefaultTableModel) table.getModel();
						model.insertRow(0, new Object[] { rs.getString(1), rs.getString(2) });
					}
				} while (rs.next());
			}
			if (model.getRowCount() == 0) { // 수정
				// 만약 결과가 없다면 결과 없음으로 출력
				model = (DefaultTableModel) table.getModel();
				model.insertRow(0, new Object[] { "결과없음", "결과없음" });
			}
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	// 월 숫자반환함수
	private int take_num(String s) {
		String temp = "";
		if (s.charAt(0) != 0) {
			temp += s.charAt(0);
		}
		if (s.charAt(1) != '.' && s.charAt(1) != '월') {
			temp += s.charAt(1);
		}
		return Integer.parseInt(temp);
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
			} else {
				System.out.println("그래프버튼 눌림");

				boolean isEmpty = false;
				for (int i = 0; i < 7; i++) {
					if (table.getValueAt(i, 1) == "") {
						if (i == 6) {
							// 모든 데이터가 없을 경우
							JOptionPane.showMessageDialog(null, "조회 할 데이터가 없습니다!");
							isEmpty = true;
						}
					} else {
						break;
					}
				}

				String region = "";
				String date = "";

				region = (String) regionCombo.getSelectedItem();
				date = (String) monthCombo.getSelectedItem();
				// 함수 호출
				setTable(region, date);
				int pureDate = take_num(date);

				if (!isEmpty) {
					if (e.getSource() == showBarGraph) {
						frame = new MonthGraph(region, pureDate, month_num, "Bar");
						frame.setVisible(true);
					} else if (e.getSource() == showLineGraph) {
						frame = new MonthGraph(region, pureDate, month_num, "Line");
						frame.setVisible(true);
					}
				}

			}
		}
	}
}
