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

class ImagePanel extends JPanel{
	private Image img;
	
	public ImagePanel(Image img) {
		this.img = img;
		setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		setLayout(null);
	}
	
	// 그리는 메소드
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}

public class RiskD extends JPanel{
	private String [] locationList = {"선택하세요","강남구","강남대로","강동구","강변북로","강북구","강서구","공항대로","관악구","관악산","광진구","구로구","궁동","금천구","남산",
			"노원구","도봉구","도산대로","동대문구","동작구","동작대로","마포구","북한산","서대문구","서초구","성동구","성북구","세곡",
			"송파구","시흥대로","신촌로","양천구","영등포구","영등포로","용산구","은평구","정릉로","종로","종로구","중구","중랑구",
			"천호대로" ,"청계천로" ,"한강대로","행주" ,"홍릉로" ,"화랑로"};
	private int [] locationX = {0,500,478,675,603,475,128,79,378,352,578,205,114,257,376,
			535,460,472,507,343,392,276,387,322,446,489,435,593,
			617,241,300,161,250,250,374,277,396,380,370,404,585,
			654,386,346,71,481,502};
	private int [] locationY = {0,397,444,300,306,134,282,343,518,546,342,454,430,509,318,
			89,60,382,249,412,437,294,70,238,470,310,204,498,
			404,531,300,381,368,358,356,167,154,210,220,278,207,
			353,283,342,241,234,185};
	
	private JPanel map;
	
	public RiskD() {
		ImagePanel panel = new ImagePanel(new ImageIcon("./images/지도.png").getImage());
		
		// 패널에 이미지 추가
		add(panel);
		setLayout(new BorderLayout());
		setSize(800, 400);
		map = new JPanel();
		map.setLayout(new BorderLayout());
		map.setBackground(Color.WHITE);
		
		
	}

}
