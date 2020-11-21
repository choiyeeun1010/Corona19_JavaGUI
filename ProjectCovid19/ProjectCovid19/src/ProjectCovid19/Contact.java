package ProjectCovid19;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.Map;
import java.util.HashMap;

public class Contact extends JPanel {

	private JPanel north;
	private JFrame areaGraph;

	// 위쪽 패널
	private JPanel space, panel1, panel2;
	private JLabel string, region_Select;
	private JComboBox<String> regionCombo;
	private String Region[] = { "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "기타", "노원구", "도봉구", "동대문구",
			"동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구" };

	private JButton search;
	
	// 데이터 값
	private Map <String, Integer> data = new HashMap<String, Integer>();

	// 리스너
	Listener Listener = new Listener();

	public Contact() {
		// 레이아웃 설정
		this.setLayout(new GridLayout(1, 1));
		North();

	}

	private void North() {
		// 레이아웃 나누기
		north = new JPanel(new GridLayout(3, 1));
		north.setSize(750, 100);
		north.setBackground(Color.white);

		space = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		space.setBackground(Color.white);

		panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
		panel1.setBackground(Color.white);

		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
		panel2.setBackground(Color.white);

		// 안내문구
		string = new JLabel("지역별 접촉 정보를 보여줍니다");
		string.setFont(string.getFont().deriveFont(15.0f));
		// 레이블
		region_Select = new JLabel("지역 : ");

		// 콤보박스
		regionCombo = new JComboBox<String>(Region);

		// 배경색
		regionCombo.setBackground(Color.white);

		// 버튼
		search = new JButton("조회");
		search.setBackground(Color.white);

		// 리스너 추가
		search.addActionListener(Listener);

		// 패널에 추가
		space.add(string);
		panel1.add(region_Select);
		panel1.add(regionCombo);

		panel2.add(search);

		north.add(space);
		north.add(panel1);
		north.add(panel2);

		add(north);
	}

	// 확진자 조회 결과 가져오기
	private void getData(String region) {
		
		data.clear();
	
		try (Connection conn = DB.makeConnection();) {
			System.out.println("선택한 지역: " + region);
			String sql;

			sql = "SELECT 접촉력, COUNT(*) FROM person WHERE 지역 = ? and 접촉력 in ('해외 접촉', '해외 접촉 추정', '확인 중') group by 접촉력 ";
			try (PreparedStatement pstmt = conn.prepareStatement(sql);){
				pstmt.setString(1, region);
				
				// 실행결과 저장
				try (ResultSet rs = pstmt.executeQuery();){
					while (rs.next()) {
						data.put(rs.getString(1), rs.getInt(2));
					}
					rs.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
				pstmt.close();
			
			}  catch (Exception e) {
				System.out.println(e);
			}
			
			sql = "SELECT COUNT(*) FROM person WHERE 지역 = ? and 접촉력 not in ('해외 접촉', '해외 접촉 추정', '확인 중') ";
			try (PreparedStatement pstmt = conn.prepareStatement(sql);){
				pstmt.setString(1, region);
				
				// 실행결과 저장
				try (ResultSet rs = pstmt.executeQuery();){
					while (rs.next()) {
						data.put("국내 접촉", rs.getInt(1));
					}
					rs.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
				pstmt.close();
			
			}  catch (Exception e) {
				System.out.println(e);
			}
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
		
	}

	// 조회버튼 클릭시 발생하는 이벤트 리스너
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == search) { // 조회버튼 클릭시
				System.out.println("조회버튼 눌림");
				
				String region = (String) regionCombo.getSelectedItem();
				getData(region);

				areaGraph = new ContactGraph(region, data);
				areaGraph.setVisible(true);

			}
		}
	}

}
