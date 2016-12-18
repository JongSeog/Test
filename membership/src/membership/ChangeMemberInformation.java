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

public class ChangeMemberInformation extends JPanel {
	ArrayList<HashMap<String, Object>> excelArray;
	String nowId;
	JPanel jPanel;
	JPanel signalPanel;
	JPanel signalIdPanel;
	JPanel inputPasswordPanel;
	JPanel okPanel;
	JPasswordField inputPasswordTF;
	JButton okButton;

	public ChangeMemberInformation(ArrayList<HashMap<String, Object>> excelArray, String nowId) {
		setLayout(new BorderLayout());
		this.excelArray = excelArray;
		this.nowId = nowId;
		
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(4, 1));
		signalPanel = new JPanel();
		signalIdPanel = new JPanel();
		inputPasswordPanel = new JPanel();
		okPanel = new JPanel();
		inputPasswordTF = new JPasswordField(10);
		inputPasswordTF.setEchoChar('*');
		okButton = new JButton("OK");
		
		inputPasswordTF.addActionListener(new ChangeEventHandler());
		okButton.addActionListener(new ChangeEventHandler());
		signalPanel.add(new JLabel("���� �������� ID�� ��й�ȣ�� �Է��ϼ��� !"));
		signalIdPanel.add(new JLabel("�������� ID : " + nowId));
		inputPasswordPanel.add(new JLabel("Password : "));
		inputPasswordPanel.add(inputPasswordTF);
		okPanel.add(okButton);

		jPanel.add(signalPanel);
		jPanel.add(signalIdPanel);
		jPanel.add(inputPasswordPanel);
		jPanel.add(okPanel);

		add(jPanel, BorderLayout.NORTH);
	}

	class ChangeEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Set<String> arrayKey;
			Iterator<String> arrayIterator;
			String keys = null;
			int nowIndex = 0; 
			MemberInformation excelMemberInformation = new MemberInformation();

			for (int i = 0; i < excelArray.size(); i++) {
				arrayKey = excelArray.get(i).keySet();
				arrayIterator = arrayKey.iterator();
				keys = arrayIterator.next();
				excelMemberInformation = (MemberInformation) excelArray.get(i).get(keys);

				if (keys.equals(nowId)) {
					nowIndex = i;
					break;
				}
			}
			
			if(excelMemberInformation.getPassword().equals(inputPasswordTF.getText())) {
				new ChangeInformationFrame(excelArray, nowId, nowIndex); 
			} else {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� Ȯ�����ּ���!");
			}
		}
	}
}