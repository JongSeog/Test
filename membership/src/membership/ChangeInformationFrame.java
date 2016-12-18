package membership;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeInformationFrame extends JFrame {
	ArrayList<HashMap<String, Object>> excelArray;
	HashMap<String, Object> nowHash;
	String nowId;
	int nowIndex;

	JPanel jPanel;
	JPanel nowIdPanel;
	JPanel firstInformationPanel;
	JPanel inputInformationPanel;
	JPanel secondInformationPanel;
	JPanel allChangePanel;

	JTextField inputTF;

	JButton okButton;
	JButton allChangeButton;

	JComboBox<String> jComboBox;

	public ChangeInformationFrame(ArrayList<HashMap<String, Object>> excelArray, String nowId, int nowIndex) {
		this.excelArray = excelArray;
		this.nowId = nowId;
		this.nowIndex = nowIndex;
		nowHash = new HashMap<String, Object>();
		nowHash = excelArray.get(nowIndex);

		setTitle("ȸ����������");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		jPanel = new JPanel();
		nowIdPanel = new JPanel();
		firstInformationPanel = new JPanel();
		inputInformationPanel = new JPanel();
		secondInformationPanel = new JPanel();
		allChangePanel = new JPanel();

		inputTF = new JTextField(10);
		inputTF.addActionListener(new OneChangeEventHandler());

		okButton = new JButton("OK");
		okButton.addActionListener(new OneChangeEventHandler());
		allChangeButton = new JButton("��ü����");
		allChangeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AllChangeInformationFrame(excelArray, nowId, nowIndex);
			}
		});

		jComboBox = new JComboBox<>(new String[] { "Password", "Name", "Birthday", "Address", "E-mail" });

		jPanel.setLayout(new GridLayout(5, 1));

		nowIdPanel.add(new JLabel("���� �α��� ID�� " + nowId + "�Դϴ�."));
		firstInformationPanel.add(new JLabel("1) �����Ͻð� ���� ������ �����Ͽ� �Է��� �ּ���!"));
		inputInformationPanel.add(jComboBox);
		inputInformationPanel.add(inputTF);
		inputInformationPanel.add(okButton);
		secondInformationPanel.add(new JLabel("2) ��ü ������ �����Ͻ÷��� ��ü���� ��ư�� �����ּ���!"));
		allChangePanel.add(allChangeButton);

		jPanel.add(nowIdPanel);
		jPanel.add(firstInformationPanel);
		jPanel.add(inputInformationPanel);
		jPanel.add(secondInformationPanel);
		jPanel.add(allChangePanel);

		add(jPanel, BorderLayout.NORTH);
		setVisible(true);
	}

	class OneChangeEventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(nowId);
			MemberInformation changeMemberInformation = new MemberInformation();
			changeMemberInformation = (MemberInformation) nowHash.get(nowId);
			System.out.println(changeMemberInformation);

			if (!inputTF.getText().equals("")) {
				if (jComboBox.getSelectedIndex() == 0) {
					changeMemberInformation.setPassword(inputTF.getText());
				} else if (jComboBox.getSelectedIndex() == 1) {
					changeMemberInformation.setName(inputTF.getText());
				} else if (jComboBox.getSelectedIndex() == 2) {
					changeMemberInformation.setBirthDay(inputTF.getText());
				} else if (jComboBox.getSelectedIndex() == 3) {
					changeMemberInformation.setAddress(inputTF.getText());
				} else {
					changeMemberInformation.setEmail(inputTF.getText());
				}
				nowHash.put(nowId, changeMemberInformation);
				excelArray.set(nowIndex, nowHash);
				UpdateExcel updateExcel = new UpdateExcel(excelArray);
				updateExcel.update();
				JOptionPane.showMessageDialog(null, "ȸ�������� ����Ǿ����ϴ�.���α׷��� �ٽ� �������ּ���");
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "�����Ͻ� ȸ�������� �ٽ� �Է����ּ���");
			}
		}
	}
}