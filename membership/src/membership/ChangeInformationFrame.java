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

		setTitle("회원정보수정");
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
		allChangeButton = new JButton("전체변경");
		allChangeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AllChangeInformationFrame(excelArray, nowId, nowIndex);
			}
		});

		jComboBox = new JComboBox<>(new String[] { "Password", "Name", "Birthday", "Address", "E-mail" });

		jPanel.setLayout(new GridLayout(5, 1));

		nowIdPanel.add(new JLabel("현재 로그인 ID는 " + nowId + "입니다."));
		firstInformationPanel.add(new JLabel("1) 변경하시고 싶은 정보를 선택하여 입력해 주세요!"));
		inputInformationPanel.add(jComboBox);
		inputInformationPanel.add(inputTF);
		inputInformationPanel.add(okButton);
		secondInformationPanel.add(new JLabel("2) 전체 정보를 변경하시려면 전체변경 버튼을 눌러주세요!"));
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
				JOptionPane.showMessageDialog(null, "회원정보가 변경되었습니다.프로그램을 다시 시작해주세요");
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "변경하실 회원정보를 다시 입력해주세요");
			}
		}
	}
}