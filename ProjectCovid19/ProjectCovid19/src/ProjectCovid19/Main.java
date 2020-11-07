package ProjectCovid19;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
	// 전체 틀
	static public Container contentPane;
	
	// 카드 패널
	private JPanel cards;
	
	// 메뉴 바
	private JMenuBar menubar;
	
	// 홈화면 
	private JMenu Home;
	private JMenuItem menuHome;
	
	// 확진자 조회 메뉴
	private JMenu Inquiry;
	private JMenuItem menuSituation;
	private JMenuItem menuRoute;
	private JMenuItem menuRiskD;
	
	// 부가기능 메뉴
	private JMenu Extra;
	private JMenuItem menuSelfCheck;
	private JMenuItem menuWords;
	
	// 툴바 버튼
	private JButton mainHome;
	private JButton situation;
	private JButton route;
	private JButton riskD;
	private JButton selfCheck;
	private JButton words;
	
	// 툴바 카드 패널
	private JPanel mainHomeCard;
	private JPanel situationCard;
	private JPanel routeCard;
	private JPanel riskDCard;
	private JPanel selfCheckCard;
	private JPanel wordsCard;
	
	// 생성자
	public Main() {
		// 컨텐트페인 객체 얻기
		contentPane = getContentPane();
		
		// 컨텐트페인의 배경색 지정
		contentPane.setBackground(Color.white);
		
		// 컨텐트페인의 정렬 방식 지정
		 contentPane.setLayout(new BorderLayout());
		 
		// 이름
		setTitle("1석 3조");
		
		// 사이즈
		setSize(900, 600);
		
		// 위치
		setLocation(450, 250);
		
		// 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 메뉴 생성
		Menu();
		
		// 툴바 생성
		ToolBar();
		
		// 카드 레이아웃 생성
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		
		// 카드 생성
		mainHomeCard = new MainHome();
		situationCard = new Situation();
		// riskDCard = new Route();
		// routeCard = new RiskD();
		selfCheckCard = new SelfCheck();
		wordsCard = new Words();
		
		cards.add(mainHomeCard, "function_MainHome");
		cards.add(situationCard, "function_Situation");
		//cards.add(riskDCard, "function_Route");
		//cards.add(routeCard, "function_RiskD");
		cards.add(selfCheckCard, "function_SelfCheck");
		cards.add(wordsCard, "function_Words");

		
		
		// 컨텐트페인에 카드 추가
		contentPane.add(cards);
	}
	
	// 메뉴
	private void Menu() {
		Listener MenuListner = new Listener();
		
		// 메뉴바 
		menubar = new JMenuBar();
		
		Home = new JMenu("HOME");
		Home.setPreferredSize(new Dimension(90, 30));
		Home.setFont(Home.getFont().deriveFont(17.0f));
		
		Inquiry = new JMenu("CORONA");
		Inquiry.setPreferredSize(new Dimension(110, 30));
		Inquiry.setFont(Inquiry.getFont().deriveFont(17.0f));
		
		Extra = new JMenu("EXTRA");
		Extra.setPreferredSize(new Dimension(100, 30));
		Extra.setFont(Inquiry.getFont().deriveFont(17.0f));
		
		menuHome = new JMenuItem("Main");
		menuHome.setPreferredSize(new Dimension(90, 30));
		menuHome.setFont(Home.getFont().deriveFont(15.0f));
		menuHome.addActionListener(MenuListner);
		
		menuSituation = new JMenuItem("Situation");
		menuSituation.setPreferredSize(new Dimension(90, 30));
		menuSituation.setFont(Home.getFont().deriveFont(15.0f));
		menuSituation.addActionListener(MenuListner);
		
		menuRoute = new JMenuItem("Route");
		menuRoute.setPreferredSize(new Dimension(90, 30));
		menuRoute.setFont(Home.getFont().deriveFont(15.0f));
		menuRoute.addActionListener(MenuListner);
		
		menuRiskD = new JMenuItem("RiskD");
		menuRiskD.setPreferredSize(new Dimension(90, 30));
		menuRiskD.setFont(Home.getFont().deriveFont(15.0f));
		menuRiskD.addActionListener(MenuListner);
		
		menuSelfCheck = new JMenuItem("SelfCheck");
    	menuSelfCheck.setPreferredSize(new Dimension(90, 30));
		menuSelfCheck.setFont(Home.getFont().deriveFont(15.0f));
		menuSelfCheck.addActionListener(MenuListner);
		
		menuWords = new JMenuItem("Words");
		menuWords.setPreferredSize(new Dimension(90, 30));
		menuWords.setFont(Home.getFont().deriveFont(15.0f));
		menuWords.addActionListener(MenuListner);
		
		
		
		// 메뉴바에 메뉴를 추가
		menubar.add(Home);
		menubar.add(Inquiry);
		menubar.add(Extra);
		
		// 메뉴에 메뉴아이템 추가
		Home.add(menuHome);
		Inquiry.add(menuSituation);
		Inquiry.add(menuRoute);
		Inquiry.add(menuRiskD);
		Extra.add(menuSelfCheck);
		Extra.add(menuWords);

		// 메뉴바 배경 색상 설정
		menubar.setBackground(Color.white);
		
		// 메뉴 아이템 배경 색상 설정
		menuHome.setBackground(Color.white);
		menuSituation.setBackground(Color.white);
		menuRoute.setBackground(Color.white);
		menuRiskD.setBackground(Color.white);
		menuSelfCheck.setBackground(Color.white);
		menuWords.setBackground(Color.white);
		
		setJMenuBar(menubar);
	}
	
	// 툴바
	private void ToolBar() {
		// 툴바 리스너
		Listener ToolbarListener = new Listener();	
		
		// 툴바 생성
		JToolBar toolbar = new JToolBar("toobar");
		//Color color = new Color(213, 247, 248);
		toolbar.setBackground(Color.WHITE);
		
		// 버튼을 리스너에 연결
		mainHome = new JButton(new ImageIcon("./images/home.png"));
		mainHome.addActionListener(ToolbarListener);
		mainHome.setBackground(Color.WHITE);
		
		situation = new JButton(new ImageIcon("./images/stuation.png"));
		situation.addActionListener(ToolbarListener);
		situation.setBackground(Color.WHITE);

		
		route = new JButton(new ImageIcon("./images/route.png"));
		route.addActionListener(ToolbarListener);
		route.setBackground(Color.WHITE);

		
		riskD = new JButton(new ImageIcon("./images/risk.png"));
		riskD.addActionListener(ToolbarListener);
		riskD.setBackground(Color.WHITE);

		
		selfCheck = new JButton(new ImageIcon("./images/selfcheck.png"));
		selfCheck.addActionListener(ToolbarListener);
		selfCheck.setBackground(Color.WHITE);

		
		words = new JButton(new ImageIcon("./images/word.png"));
		words.addActionListener(ToolbarListener);
		words.setBackground(Color.WHITE);
		
		// 툴바에 버튼 추가
		toolbar.add(mainHome);
		toolbar.addSeparator();
		toolbar.add(situation);
		toolbar.addSeparator();
		toolbar.add(route);
		toolbar.addSeparator();
		toolbar.add(riskD);
		toolbar.addSeparator();
		toolbar.add(selfCheck);
		toolbar.addSeparator();
		toolbar.add(words);
		toolbar.addSeparator();

		// 툴바를 컨텐트페인에 추가
		contentPane.add(toolbar, BorderLayout.NORTH);
	}
	
	// 리스너
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CardLayout pick = (CardLayout) cards.getLayout();
			if(e.getSource() == menuHome || e.getSource() == mainHome) {
				System.out.println("홈 눌림");
				pick.show(cards, "function_MainHome");

			}else if(e.getSource() == menuSituation || e.getSource() == situation) {
				System.out.println("확진자조회 눌림");
				pick.show(cards, "function_Situation");
				
			}else if(e.getSource() == menuRoute || e.getSource() == route) {
				System.out.println("환진자경로 눌림");
				pick.show(cards, "function_Route");
				
			}else if(e.getSource() == menuRiskD || e.getSource() == riskD) {
				System.out.println("위험도 조회 눌림");
				pick.show(cards, "function_RiskD");
				
			}else if(e.getSource() == menuSelfCheck || e.getSource() == selfCheck) {
				System.out.println("자가진단표 눌림");
				pick.show(cards, "function_SelfCheck");
				
			}else if(e.getSource() == menuWords || e.getSource() == words) {
				System.out.println("눌러보세요 눌림");
				pick.show(cards, "function_Words");
			}
		}
	}
}
