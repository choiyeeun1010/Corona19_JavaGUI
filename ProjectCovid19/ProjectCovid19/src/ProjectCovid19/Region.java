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

public class Region extends JPanel {

	private Connection connection;
	private JPanel north, south;
	private JFrame frame;
	private String myDate;

	// 위쪽 패널
	private JPanel panel1, panel2, panel3;
	private JLabel string, date_Select;
	private JComboBox<String> monthCombo;
	private String Month[] = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월" };
	private JButton search;

	// 아래쪽
	private JPanel centerPanel, choice;
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = { "지역", "확진자 수" };
	private JScrollPane scrollpane;

	// 그래프 버튼
	private JButton showMap, showBarGraph;

	// 리스너
	Listener Listener = new Listener();

	public Region() {
		// 레이아웃 설정
		this.setLayout(new GridLayout(2, 1));
		North();
		South();
	}

	private void North() {
		north = new JPanel(new GridLayout(3, 1));
		north.setSize(750, 100);
		north.setBackground(Color.white);
		
		// 패널
		panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		panel1.setBackground(Color.white);

		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		panel2.setBackground(Color.white);

		panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
		panel3.setBackground(Color.white);

		// 레이블
		string = new JLabel("선택한 월의 확진자 수를 지역별로 보여줍니다.");
		string.setFont(string.getFont().deriveFont(15.0f));
		date_Select = new JLabel("날짜 : 2020년 ");

		// 콤보박스
		monthCombo = new JComboBox<String>(Month);

		// 배경색
		monthCombo.setBackground(Color.white);

		// 버튼
		search = new JButton("조회");
		search.setBackground(Color.white);

		// 리스너 추가
		search.addActionListener(Listener);

		// 패널에 추가
		panel1.add(string);
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
		showMap = new JButton("서울시 지도 보기");
		showMap.setBackground(Color.white);

		showBarGraph = new JButton("막대 그래프보기");
		showBarGraph.setBackground(Color.white);

		// 리스너 달기
		showMap.addActionListener(Listener);
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
		choice.add(showMap);
		choice.add(showBarGraph);

		south.add(centerPanel);
		south.add(choice);
		add(south);
	}

	// 확진자 조회 결과를 보여줌
	private void setTable(String date) {

		try {
			System.out.println("시작 월: " + date);
			connection = DB.makeConnection();
			String pureDate = date.substring(0, 1);
			pureDate = pureDate + "%";

			String sql;

			sql = "SELECT 지역, COUNT(지역) AS 확진자수 FROM person WHERE 확진일 LIKE ? group by 지역  ORDER BY 확진자수";

			ResultSet rs;
			PreparedStatement pstmt = connection.prepareStatement(sql);
			// pstmt.setString(1, region);
			pstmt.setString(1, pureDate);

			// 실행결과 저장
			rs = pstmt.executeQuery();

			// 테이블 값 설정
			model = (DefaultTableModel) table.getModel(); // 테이블 설정 전에 초기화시키기
			model.setNumRows(0);

			if (rs.next()) {
				do {
					model = (DefaultTableModel) table.getModel();
					model.insertRow(0, new Object[] { rs.getString(1), rs.getString(2) });
				} while (rs.next());
			} else {
				// 만약 결과가 없다면 결과 없음으로 출력
				model = (DefaultTableModel) table.getModel();
				model.insertRow(0, new Object[] { "결과없음", "결과없음" });
			}
			System.out.println(table.getRowCount());

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
				String date = "";

				date = (String) monthCombo.getSelectedItem();
				// 함수 호출
				setTable(date);
				myDate = date;

			} else if (e.getSource() == showMap || e.getSource() == showBarGraph) {
				System.out.println("버튼 클릭");

				boolean isEmpty = false;
				for (int i = 0; i < 2; i++) {
					if (table.getValueAt(i, 1) == "") {
						if (i == 2) {
							// 모든 데이터가 없을 경우
							JOptionPane.showMessageDialog(null, "조회 할 데이터가 없습니다!");
							isEmpty = true;
						}
					} else {
						break;
					}
				}

				// 크기가 가변적이라 리스트에 저장
				ArrayList<String> x = new ArrayList<String>();
				ArrayList<String> data = new ArrayList<String>();

				// 그래프로 데이터 전달해주기
				// 1월만 예외처리 why? 1월에 확진자가 있는 구가 5개 밖에 없음
				if ((String) monthCombo.getSelectedItem() == "1월") {
					for (int i = 0; i < 5; i++) {
						x.add((String) table.getValueAt(i, 0));
					}
					for (int i = 0; i < 5; i++) {
						data.add((String) table.getValueAt(i, 1));
					}
					for (int i = 0; i < 5; i++) {
						System.out.println(x.get(i) + "     " + data.get(i));
					}
				}
				// 나머지 월들은 상위 13개만 넘겨주기
				else {
					for (int i = 0; i < 13; i++) {
						x.add((String) table.getValueAt(i, 0));
					}
					for (int i = 0; i < 13; i++) {
						data.add((String) table.getValueAt(i, 1));
					}
					for (int i = 0; i < 13; i++) {
						System.out.println(x.get(i) + "     " + data.get(i));
					}
				}

				if (!isEmpty) {
					if (e.getSource() == showMap) {
						frame = new Map();
						frame.setVisible(true);
					} else if (e.getSource() == showBarGraph) {
						frame = new RegionGraph(x, data, myDate, "Bar");
						frame.setVisible(true);
					}
				}
			}
		}
	}
}