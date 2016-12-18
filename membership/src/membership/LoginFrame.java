package membership;

import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{
	JLabel labelId;
	JLabel labelPassword;
	JTextField textId;
	JPasswordField textPassword;
	JButton okButton;
	JButton joinButton;
	ArrayList<HashMap<String, Object>> excelArray;
	
	public LoginFrame(ArrayList<HashMap<String, Object>> excelArray) {
		this.excelArray = excelArray;
		
		//array에 무슨값이 들어가있나 확인
		System.out.println(excelArray);
		
		setTitle("Login");
		
		labelId = new JLabel("ID :", JLabel.RIGHT);
		labelPassword = new JLabel("Password :", JLabel.RIGHT);

		textId = new JTextField(10);
		textPassword = new JPasswordField(10);
		textPassword.setEchoChar('*');
		textId.addActionListener(new LoginOkEventHandler());
		textPassword.addActionListener(new LoginOkEventHandler());

		okButton = new JButton("OK");
		okButton.addActionListener(new LoginOkEventHandler());

		joinButton = new JButton("JOIN");
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new JoinFrame(excelArray);
			}
		});

		setLayout(new FlowLayout());
		add(labelId);
		add(textId);
		add(labelPassword);
		add(textPassword);
		add(okButton);
		add(joinButton);
		setSize(350, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	class LoginOkEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String id = textId.getText();
			String password = textPassword.getText();
			boolean idBoolean = false;
			boolean passwordBoolean = false;
			
			Set<String> arrayKey;
			Iterator<String> arrayIterator;
			for(int i = 0; i < excelArray.size(); i++){
				String idKey;
				arrayKey = excelArray.get(i).keySet(); 
				arrayIterator = arrayKey.iterator();
				idKey = arrayIterator.next();
			
				MemberInformation excelMemberInformation = (MemberInformation) excelArray.get(i).get(idKey);
				if((id.equals(idKey)) && (password.equals(excelMemberInformation.getPassword()))) {
					idBoolean = true;
					passwordBoolean = true;
					new MainPageFrame(excelArray, id, password);
					dispose();
					break;
				}
			}
			if((idBoolean == false) || (passwordBoolean == false)) {
				JOptionPane.showMessageDialog(null, "틀림");
			}
		}
	}
}