package membership;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JoinFrame extends JFrame {
	ArrayList<HashMap<String, Object>> excelArray;
	boolean overlapCheck;

	JButton registrationButton;
	JButton overlapCheckButton;

	JPanel idPanel;
	JPanel passwordPanel;
	JPanel namePanel;
	JPanel birthDayPanel;
	JPanel addressPanel;
	JPanel emailPanel;
	JPanel registrationPanel;

	JTextField idTF;
	JPasswordField passwordTF;
	JTextField nameTF;
	JTextField birthDayTF;
	JTextField addressTF;
	JTextField emailTF;

	public JoinFrame(ArrayList<HashMap<String, Object>> excelArray) {
		this.excelArray = excelArray;
		overlapCheck = true;

		System.out.println(excelArray);
		setTitle("join-us");
		setLayout(new GridLayout(7, 1));
		setSize(800, 600);
		registrationButton = new JButton("회원등록");
		overlapCheckButton = new JButton("중복확인");

		idPanel = new JPanel();
		passwordPanel = new JPanel();
		namePanel = new JPanel();
		birthDayPanel = new JPanel();
		addressPanel = new JPanel();
		emailPanel = new JPanel();
		registrationPanel = new JPanel();

		idTF = new JTextField(10);
		passwordTF = new JPasswordField(10);
		passwordTF.setEchoChar('*');
		nameTF = new JTextField(10);
		birthDayTF = new JTextField(10);
		addressTF = new JTextField(10);
		emailTF = new JTextField(10);

		idPanel.add(new JLabel("ID :"));
		idPanel.add(idTF);
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

		registrationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (overlapCheck == false) {
					JOptionPane.showMessageDialog(null, "ID 중복확인을 해주세요!");
				} else if ((idTF.getText().equals("")) || (passwordTF.getText().equals(""))
						|| (nameTF.getText().equals("")) || (birthDayTF.getText().equals(""))
						|| (addressTF.getText().equals("")) || (emailTF.getText().equals(""))) {
					JOptionPane.showMessageDialog(null, "빈칸을 채워주세요");
				} else {
					HashMap<String, Object> addMemberHashMap = new HashMap<String, Object>();
					MemberInformation addMember = new MemberInformation();
					addMember.setPassword(passwordTF.getText());
					addMember.setName(nameTF.getText());
					addMember.setBirthDay(birthDayTF.getText());
					addMember.setAddress(addressTF.getText());
					addMember.setEmail(emailTF.getText());
					addMemberHashMap.put(idTF.getText(), addMember);
					excelArray.add(addMemberHashMap);
					
					UpdateExcel updateExcel = new UpdateExcel(excelArray);
					updateExcel.update();
					
					dispose();
					new LoginFrame(excelArray);
				}
			}
		});

		overlapCheckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (overlapCheck == false){
					overlapCheck = true;	
				}
				
				Set<String> arrayKey;
				Iterator<String> arrayIterator;
				for (int i = 0; i < excelArray.size(); i++) {
					String keys;
					arrayKey = excelArray.get(i).keySet();
					arrayIterator = arrayKey.iterator();
					keys = arrayIterator.next();

					if (idTF.getText().equals(keys)) {
						overlapCheck = false;
						JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.");
						break;
					} else if (idTF.getText().equals("")) {
						overlapCheck = false;
						JOptionPane.showMessageDialog(null, "ID를 입력해주세요");
						break;
					}
				}
				if (overlapCheck == true) {
					JOptionPane.showMessageDialog(null, "사용가능한 ID입니다.");
				}
			}
		});

		idPanel.add(overlapCheckButton);
		registrationPanel.add(registrationButton);

		add(idPanel);
		add(passwordPanel);
		add(namePanel);
		add(birthDayPanel);
		add(addressPanel);
		add(emailPanel);
		add(registrationPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
