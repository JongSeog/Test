package membership;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DeleteMember extends JPanel {
	ArrayList<HashMap<String, Object>> excelArray;
	String nowId;
	String nowPassword;
	
	JPanel jPanel;
	JPanel nowIdPanel;
	JPanel inputSignalPanel;
	JPanel inputNowIdPanel;
	JPanel inptNowPasswordPanel;
	JPanel okButtonPanel;
	JButton okButton;
	JTextField idTF;
	JPasswordField passwordTF;

	public DeleteMember(ArrayList<HashMap<String, Object>> excelArray, String nowId, String nowPassword) {
		this.excelArray = excelArray;
		this.nowId = nowId;
		this.nowPassword = nowPassword;
		
		setLayout(new BorderLayout());
		
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(5, 1));

		nowIdPanel = new JPanel();
		inputSignalPanel = new JPanel();
		inputNowIdPanel = new JPanel();
		inptNowPasswordPanel = new JPanel();
		okButtonPanel = new JPanel();
		okButton = new JButton("OK");
		okButton.addActionListener(new DeleteEventHandler());

		idTF = new JTextField(10);
		passwordTF = new JPasswordField(10);
		passwordTF.setEchoChar('*');
		passwordTF.addActionListener(new DeleteEventHandler());

		nowIdPanel.add(new JLabel("접속중인 ID : " + nowId));
		inputSignalPanel.add(new JLabel("접속중인 ID와 Password를 입력하세요"));
		inputNowIdPanel.add(new JLabel("ID : "));
		inputNowIdPanel.add(idTF);
		inptNowPasswordPanel.add(new JLabel("Password : "));
		inptNowPasswordPanel.add(passwordTF);
		okButtonPanel.add(okButton);

		jPanel.add(nowIdPanel);
		jPanel.add(inputSignalPanel);
		jPanel.add(inputNowIdPanel);
		jPanel.add(inptNowPasswordPanel);
		jPanel.add(okButtonPanel);
		
		add(jPanel, BorderLayout.NORTH);
	}

	class DeleteEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if ((nowId.equals(idTF.getText())) && (nowPassword.equals(passwordTF.getText()))) {
				Set<String> arrayKey;
				Iterator<String> arrayIterator;
				boolean checkValue = false;
				System.out.println(excelArray.size());
				for (int i = 0; i < excelArray.size(); i++) {
					String keys;
					arrayKey = excelArray.get(i).keySet();
					arrayIterator = arrayKey.iterator();
					keys = arrayIterator.next();
					System.out.println(keys);
					MemberInformation excelMemberInformation = (MemberInformation) excelArray.get(i).get(keys);
					System.out.println(excelMemberInformation.getPassword());
					if ((nowId.equals(keys)) && (nowPassword.equals(excelMemberInformation.getPassword()))) {
						checkValue = true;
						excelArray.remove(i);
						System.out.println(excelArray.size());
						DeleteUpdateExcel deleteUpdateExcel = new DeleteUpdateExcel(excelArray);
						deleteUpdateExcel.update();
						JOptionPane.showMessageDialog(null, "삭제되었습니다.");
						System.exit(0);
						break;
					}
				}
				if (checkValue == false) {
					JOptionPane.showMessageDialog(null, "입력하신 정보가 맞지 않습니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "입력하신 정보가 맞지 않습니다.");
			}
		}
	}
}