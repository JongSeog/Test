package membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AllChangeInformationFrame extends JFrame {
	ArrayList<HashMap<String, Object>> excelArray;
	HashMap<String, Object> nowHash;
	String nowId;
	int nowIndex;

	JButton okButton;

	JPanel inputSignalapanel;
	JPanel nowIdPanel;
	JPanel passwordPanel;
	JPanel namePanel;
	JPanel birthDayPanel;
	JPanel addressPanel;
	JPanel emailPanel;
	JPanel okButtonPanel;

	JTextField passwordTF;
	JTextField nameTF;
	JTextField birthDayTF;
	JTextField addressTF;
	JTextField emailTF;

	public AllChangeInformationFrame(ArrayList<HashMap<String, Object>> excelArray, String nowId, int nowIndex) {
		this.excelArray = excelArray;
		this.nowId = nowId;
		this.nowIndex = nowIndex;
		nowHash = new HashMap<String, Object>();
		nowHash = excelArray.get(nowIndex);
		

		setTitle("회원정보수정");
		setLayout(new GridLayout(8, 1));
		setSize(800, 600);
		setLocationRelativeTo(null);
		

		okButton = new JButton("OK");
		okButton.addActionListener(new AllChangeEventHandler());

		inputSignalapanel = new JPanel();
		nowIdPanel = new JPanel();
		passwordPanel = new JPanel();
		namePanel = new JPanel();
		birthDayPanel = new JPanel();
		addressPanel = new JPanel();
		emailPanel = new JPanel();
		okButtonPanel = new JPanel();

		passwordTF = new JTextField(10);
		nameTF = new JTextField(10);
		birthDayTF = new JTextField(10);
		addressTF = new JTextField(10);
		emailTF = new JTextField(10);

		inputSignalapanel.add(new JLabel("변경하실 회원정보를 입력하세요!"));
		nowIdPanel.add(new JLabel("ID :" + nowId));
		passwordPanel.add(new JLabel("Password :"));
		passwordPanel.add(passwordTF);
		namePanel.add(new JLabel("Name :"));
		namePanel.add(nameTF);
		birthDayPanel.add(new JLabel("Birthday :"));
		birthDayPanel.add(birthDayTF);
		addressPanel.add(new JLabel("Address :"));
		addressPanel.add(addressTF);
		emailPanel.add(new JLabel("E-mail :"));
		emailPanel.add(emailTF);
		okButtonPanel.add(okButton);

		add(inputSignalapanel);
		add(nowIdPanel);
		add(passwordPanel);
		add(namePanel);
		add(birthDayPanel);
		add(addressPanel);
		add(emailPanel);
		add(okButtonPanel);

		setVisible(true);
	}
	
	class AllChangeEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if((passwordTF.getText().equals("")) || (nameTF.getText().equals("")) || (birthDayTF.getText().equals("")) || (addressTF.getText().equals("")) || (emailTF.getText().equals(""))){
				JOptionPane.showMessageDialog(null, "빈칸을 채워주세요!!");
			} else {
				MemberInformation changeMemberInformation = new MemberInformation();
				changeMemberInformation = (MemberInformation) nowHash.get(nowId);
				changeMemberInformation.setPassword(passwordTF.getText());
				changeMemberInformation.setName(nameTF.getText());
				changeMemberInformation.setBirthDay(birthDayTF.getText());
				changeMemberInformation.setAddress(addressTF.getText());
				changeMemberInformation.setEmail(emailTF.getText());
				
				nowHash.put(nowId, changeMemberInformation);
				excelArray.set(nowIndex, nowHash);
				
				UpdateExcel updateExcel = new UpdateExcel(excelArray);
				updateExcel.update();
				
				JOptionPane.showMessageDialog(null, "회원정보가 변경되었습니다.프로그램을 다시 시작해주세요");
				System.exit(0);
			}
		}
	}
}