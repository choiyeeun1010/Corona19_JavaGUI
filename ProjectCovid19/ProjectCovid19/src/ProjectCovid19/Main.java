package ProjectCovid19;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.event.*;

public class Main extends JFrame {
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
	private JMenuItem menuTotal;
	private JMenuItem menuContact;
	private JMenuItem menuRegion;
	private JMenuItem menuMonth;

	// 부가기능 메뉴
	private JMenu Extra;
	private JMenuItem menuSelfCheck;
	private JMenuItem menuWords;

	// 툴바 버튼
	private JButton mainHome;
	private JButton total;
	private JButton contact;
	private JButton region;
	private JButton month;
	private JButton selfCheck;
	private JButton words;

	// 툴바 카드 패널
	private JPanel mainHomeCard;
	private JPanel totalCard;
	private JPanel contactCard;
	private JPanel regionCard;
	private JPanel monthCard;
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
		totalCard = new Total();
		// routeCard = new Route();
		regionCard = new Region();
		monthCard = new Month();
		selfCheckCard = new SelfCheck();
		wordsCard = new Words();

		cards.add(mainHomeCard, "function_MainHome");
		cards.add(totalCard, "function_Total");
		// cards.add(contactCard, "function_Contact");
		cards.add(regionCard, "function_Region");
		cards.add(monthCard, "function_Month");
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
		menuHome.setPreferredSize(new Dimension(80, 30));
		menuHome.setFont(Home.getFont().deriveFont(15.0f));
		menuHome.addActionListener(MenuListner);

		menuTotal = new JMenuItem("Total");
		menuTotal.setPreferredSize(new Dimension(90, 30));
		menuTotal.setFont(Home.getFont().deriveFont(15.0f));
		menuTotal.addActionListener(MenuListner);

		menuContact = new JMenuItem("Contact");
		menuContact.setPreferredSize(new Dimension(90, 30));
		menuContact.setFont(Home.getFont().deriveFont(15.0f));
		menuContact.addActionListener(MenuListner);

		menuRegion = new JMenuItem("Region");
		menuRegion.setPreferredSize(new Dimension(90, 30));
		menuRegion.setFont(Home.getFont().deriveFont(15.0f));
		menuRegion.addActionListener(MenuListner);

		menuMonth = new JMenuItem("Month");
		menuMonth.setPreferredSize(new Dimension(90, 30));
		menuMonth.setFont(Home.getFont().deriveFont(15.0f));
		menuMonth.addActionListener(MenuListner);

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
		Inquiry.add(menuTotal);
		Inquiry.add(menuContact);
		Inquiry.add(menuRegion);
		Inquiry.add(menuMonth);
		Extra.add(menuSelfCheck);
		Extra.add(menuWords);

		// 메뉴바 배경 색상 설정
		menubar.setBackground(Color.white);

		// 메뉴 아이템 배경 색상 설정
		menuHome.setBackground(Color.white);
		menuTotal.setBackground(Color.white);
		menuContact.setBackground(Color.white);
		menuRegion.setBackground(Color.white);
		menuMonth.setBackground(Color.white);
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
		// Color color = new Color(213, 247, 248);
		toolbar.setBackground(Color.WHITE);

		// 툴바 버튼에 마우스를 올려놓았을때 이름을 나오게 하는 리스너
		MyMouseListener mouseListener = new MyMouseListener();

		// 버튼을 리스너에 연결
		mainHome = new JButton(new ImageIcon("./images/home.png"));
		mainHome.addActionListener(ToolbarListener);
		mainHome.setBackground(Color.WHITE);
		mainHome.addMouseListener(mouseListener);

		total = new JButton(new ImageIcon("./images/stuation.png"));
		total.addActionListener(ToolbarListener);
		total.setBackground(Color.WHITE);
		total.addMouseListener(mouseListener);

		contact = new JButton(new ImageIcon("./images/contact.png"));
		contact.addActionListener(ToolbarListener);
		contact.setBackground(Color.WHITE);
		contact.addMouseListener(mouseListener);

		region = new JButton(new ImageIcon("./images/region.png"));
		region.addActionListener(ToolbarListener);
		region.setBackground(Color.WHITE);
		region.addMouseListener(mouseListener);

		month = new JButton(new ImageIcon("./images/risk.png"));
		month.addActionListener(ToolbarListener);
		month.setBackground(Color.WHITE);
		month.addMouseListener(mouseListener);

		selfCheck = new JButton(new ImageIcon("./images/selfcheck.png"));
		selfCheck.addActionListener(ToolbarListener);
		selfCheck.setBackground(Color.WHITE);
		selfCheck.addMouseListener(mouseListener);

		words = new JButton(new ImageIcon("./images/word.png"));
		words.addActionListener(ToolbarListener);
		words.setBackground(Color.WHITE);
		words.addMouseListener(mouseListener);

		// 툴바에 버튼 추가
		toolbar.add(mainHome);
		toolbar.addSeparator();
		toolbar.add(total);
		toolbar.addSeparator();
		toolbar.add(contact);
		toolbar.addSeparator();
		toolbar.add(region);
		toolbar.addSeparator();
		toolbar.add(month);
		toolbar.addSeparator();
		toolbar.add(selfCheck);
		toolbar.addSeparator();
		toolbar.add(words);
		toolbar.addSeparator();

		// 툴바를 컨텐트페인에 추가
		contentPane.add(toolbar, BorderLayout.NORTH);
	}

	// 버튼을 눌렀을때 발생하는 리스너
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CardLayout pick = (CardLayout) cards.getLayout();
			if (e.getSource() == menuHome || e.getSource() == mainHome) {
				System.out.println("홈 눌림");
				pick.show(cards, "function_MainHome");
			} else if (e.getSource() == menuTotal || e.getSource() == total) {
				System.out.println("확진자 조회 눌림");
				pick.show(cards, "function_Total");
			} else if (e.getSource() == menuContact || e.getSource() == contact) {
				System.out.println("접촉별 조회 눌림");
				pick.show(cards, "function_Contact");
			} else if (e.getSource() == menuRegion || e.getSource() == region) {
				System.out.println("지역별 조회 눌림");
				pick.show(cards, "function_Region");
			} else if (e.getSource() == menuMonth || e.getSource() == month) {
				System.out.println("월별 조회 조회 눌림");
				pick.show(cards, "function_Month");
			} else if (e.getSource() == menuSelfCheck || e.getSource() == selfCheck) {
				System.out.println("자가진단표 눌림");
				pick.show(cards, "function_SelfCheck");
			} else if (e.getSource() == menuWords || e.getSource() == words) {
				System.out.println("눌러보세요 눌림");
				pick.show(cards, "function_Words");
			}
		}
	}

	// 버튼이 무슨 패널로 연결되는지 알려주기 위한 마우스 리스너
	class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override//마우스가 버튼 안으로 들어오면 버튼의 이름을 표시해줌 
	    public void mouseEntered(MouseEvent e) {
	    	if(e.getSource() == mainHome) {
	    		mainHome.setText("홈화면");
			}else if(e.getSource() == total) {
				total.setText("확진자조회");
			}else if(e.getSource() == contact) {
				contact.setText("접촉별조회");
			}else if(e.getSource() == region) {
				region.setText("지역별조회");
			}else if(e.getSource() == month) {
				month.setText("월별조회");
			}else if(e.getSource() == selfCheck) {
				selfCheck.setText("자가진단표");
			}else if(e.getSource() == words) {
				words.setText("눌러보세요");
			}
	    }

		@Override // 마우스가 버튼 밖으로 나가면 원래대로 돌아옴
		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setText("");
		}
	}
}
