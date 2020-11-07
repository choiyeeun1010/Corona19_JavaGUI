package ProjectCovid19;

import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class Words extends JPanel {       
	private JPanel main;
    private JLabel word;
    private Random random;
    
    private String[] contents = {"역설적이지만<br>코로나19 시대에 연대하는 방법은<br>모두가 흩어져, 거리를 두는 것입니다.<br><br>우리 국민의 단합된 힘이라면<br>지금의 감염 위험, 피해의 크기를 충분히 줄일 수 있습니다.<br>30일부터 8일간, 수도권의 강화된 방역조치에 동참해<br>반전의 계기를 만들어주세요.", 
    	    "코로나 대응에 모두가 지치고 힘든 지금,<br>서로가 서로를 격려하고 위로하는<br>마음의 방역이 필요한 시점입니다.<br><br>“고생 많으십니다”<br>따뜻한 말 한마디, 눈인사를 나눠주세요.<br><br>모두의 안전을 위한 노고와 인내에<br>방역당국자로서 진심으로 감사의 말씀을 드립니다.",
    	    "최근 밀폐된 공간에서 진행된 한 사업설명회에서<br>집단감염이 일어났습니다.<br><br>참석자 27명 중 26명이 확진된 가운데,<br>행사 내내 마스크를 한번도 벗지 않았던<br>단 한분만이 피해를 면했습니다.<br><br>마스크 한장의 위력,<br>나의 작은 노력으로 사랑하는 가족을 지킬 수 있습니다.", 
    	    "최근 어르신과 만성질환자분들이 모여 계신<br>요양병원, 재활병원 등 시설에 집단감염이 늘어<br>철저한 관리가 필요합니다.<br><br>시설 종사자와 이용자께서는 철저한 손위생과,<br>의심증상 있을 시 빠른검사를 통해<br>어르신들에게 향하는 감염 고리를 끊어 주세요.<br><br>생명을 지키는 일에 실천과 행동이 필요합니다.",
    	    "코로나 일상에서<br>바이러스와 인류는 함께 진화하고 있습니다.<br><br>코로나19는 치료제와 백신에 맞서고자<br>계속 변이를 일으키며 생존 노력을 다하고 있지만,<br><br>인류는 그보다 더 빠르게 진화하며<br>거리 두기로 행동양식을 변화시키고,<br>바이오과학기술 발전에 속도를 내고 있습니다.",
    	    "외신에서 국내 대응을<br>“한국은 바이러스와 함께 살아가는 방식을 택했다”고<br>분석, 평가하였습니다.<br><br>이처럼 일상과 방역의 강도를 조절해<br>생활 방역이 지금까지 정착 될 수 있었던 것은<br>개인 방역을 생활하고 거리 두기를 실천한 국민들과<br>솔선하고 환자를 위하는 의료인들 덕분입니다.<br><br>방역당국은 앞으로도 지속 되어야 할 우리의 대응 원칙을 지키며<br>안전하고 지속 가능하며 마음의 안정도 찾을 수 있는<br>방법을 계속 모색하겠습니다.",
    	    "국내 환자발생이 감소세를 유지하고,<br>위중·중증 환자 발생 추이가 둔화되어<br>감사한 마음 뿐입니다.<br><br>코로나 시대를 이기는 변하지 않는 원칙,<br>조금만 더 안정적으로 관리될 때까지<br>거리 두기와 마스크 착용을 요청드립니다.",
    	    "확산과 억제가 반복될 장기전이 된 코로나19<br>2단계 거리 두기로 지금의 위험상황을 통제하게 된다면<br>우리사회가 대단히 중요한 경험을 갖게 될 것입니다.<br><br>고통을 감수하고 인내와 단합을 통해 이겨 낸 경험<br>누구도 함부로 위태롭게 하거나 폄훼할 수 없는<br>국민 모두의 자산입니다.",
    	    
    	    "행복해서 웃는게 아니라 웃어서 행복한 것이다."};
    	    
    	    
    public Words(){       
    	setLayout(new BorderLayout());
    	setSize(800,400);
    	
    	main = new JPanel();     
		main.setLayout(new BorderLayout());                     //Layout : BorderLayout
        main.setBackground(Color.white);                        //배경 : 하양
    	
        word = new JLabel();                                    //word JLabel
		word.setFont(word.getFont().deriveFont(15.0f));         //폰트크기 : 15
		word.setBackground(Color.white);                        //글자배경 : 하양
		word.setHorizontalAlignment(JLabel.CENTER);             //위치 : 정중앙

		random = new Random();
		String string = "<html>";
		string += contents[random.nextInt(9)];
		string += "<br></html>";
		word.setText(string);
		
		main.add(word, BorderLayout.CENTER);               //word Label을 main Panel 정중앙에 붙인다.

        add(main);                                         //main Panel을 Show Panel에 붙인다.
    } 
} 
