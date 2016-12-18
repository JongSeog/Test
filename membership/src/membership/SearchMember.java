package membership;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchMember extends JPanel {
	ArrayList<HashMap<String, Object>> excelArray;
	JPanel jPanel;
	JComboBox<String> jComboBox;
	JTextField jTextField;
	JButton jButton;

	public SearchMember(ArrayList<HashMap<String, Object>> excelArray) {
		this.excelArray = excelArray;
		setLayout(new BorderLayout());

		jPanel = new JPanel();
		jComboBox = new JComboBox<>(new String[] { "ID", "Name" });
		jTextField = new JTextField(10);
		jTextField.addActionListener(new OKEventHandler());
		jButton = new JButton("OK");
		jButton.addActionListener(new OKEventHandler());

		jPanel.add(jComboBox);
		jPanel.add(jTextField);
		jPanel.add(jButton);

		add(jPanel, BorderLayout.CENTER);
	}

	class OKEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Set<String> arrayKey;
			Iterator<String> arrayIterator;
			String keys = null;
			MemberInformation excelMemberInformation = new MemberInformation();
			boolean checkValue = false;

			if (jComboBox.getSelectedIndex() == 0) {
				for (int i = 0; i < excelArray.size(); i++) {
					arrayKey = excelArray.get(i).keySet();
					arrayIterator = arrayKey.iterator();
					keys = arrayIterator.next();
					excelMemberInformation = (MemberInformation) excelArray.get(i).get(keys);

					if (keys.equals(jTextField.getText())) {
						checkValue = true;
						break;
					}
				}
				if (checkValue == true) {
					JOptionPane.showMessageDialog(null,
							"ID : " + keys + "\n" + "Name : " + excelMemberInformation.getName() + "\n" + "E - mail  : "
									+ excelMemberInformation.getEmail() + "\n" + "Birthday : " +  excelMemberInformation.getBirthDay());
					checkValue = false;
				} else {
					JOptionPane.showMessageDialog(null, "입력하신 ID를 가진 회원이 없습니다.");
				}
			} else {
				for (int i = 0; i < excelArray.size(); i++) {
					arrayKey = excelArray.get(i).keySet();
					arrayIterator = arrayKey.iterator();
					keys = arrayIterator.next();
					excelMemberInformation = (MemberInformation) excelArray.get(i).get(keys);
					if (excelMemberInformation.getName().equals(jTextField.getText())) {
						checkValue = true;
						break;
					}
				}
				if (checkValue == true) {
					JOptionPane.showMessageDialog(null,
							"ID : " + keys + "\n" + "Name : " + excelMemberInformation.getName() + "\n" + "E - mail  : "
									+ excelMemberInformation.getEmail() + "\n" + "Birthday : " + excelMemberInformation.getBirthDay());
					checkValue = false;
				} else {
					JOptionPane.showMessageDialog(null, "입력하신 이름을 가진 회원이 없습니다.");
				}
			}
		}
	}
}
